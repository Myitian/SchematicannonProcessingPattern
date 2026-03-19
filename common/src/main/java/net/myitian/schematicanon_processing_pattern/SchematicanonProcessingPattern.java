package net.myitian.schematicanon_processing_pattern;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.core.definitions.AEItems;
import appeng.crafting.pattern.EncodedPatternItem;
import com.google.common.collect.Sets;
import com.simibubi.create.content.schematics.cannon.MaterialChecklist;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicanon_processing_pattern.config.Config;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SchematicanonProcessingPattern {
    public static final String MOD_ID = "schematicanon_processing_pattern";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Path CONFIG_PATH = PlatformUtil.getConfigDirectory().resolve(MOD_ID + ".json");
    public static final int BOOK_INPUT = 2;
    public static final int BOOK_OUTPUT = 3;
    private static final GenericStack[] EMPTY = new GenericStack[0];

    public static void reloadConfig() {
        File configFile = CONFIG_PATH.toFile();
        if (!Config.load(configFile)) {
            Config.save(configFile);
        }
    }

    public static boolean isPatternLike(Item item) {
        return item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem;
    }

    public static ItemStack getProcessingPattern(SchematicannonBlockEntity schematicannon) {
        List<GenericStack> inputs = getProcessingPatternInputs(schematicannon.checklist, Config.addGathered);
        GenericStack output = getProcessingPatternOutput(
            schematicannon.inventory,
            Config.showNbtFileName ? getBlueprintFilename(PlatformUtil.getBlueprint(schematicannon)) : null,
            Config.showBlocksNotLoadedMessage && schematicannon.checklist.blocksNotLoaded);
        return PatternDetailsHelper.encodeProcessingPattern(inputs.toArray(EMPTY), new GenericStack[]{output});
    }

    public static String getBlueprintFilename(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("File")) {
            return tag.getString("File");
        }
        return null;
    }

    public static List<GenericStack> getProcessingPatternInputs(MaterialChecklist checklist, boolean addGathered) {
        Sets.SetView<Item> keys = Sets.union(checklist.required.keySet(), checklist.damageRequired.keySet());
        List<GenericStack> stacks = new ArrayList<>(keys.size());
        for (Item item : keys) {
            int amount = checklist.getRequiredAmount(item);
            if (!addGathered) {
                if (checklist.gathered.containsKey(item)) {
                    amount -= checklist.gathered.getInt(item);
                }
                if (amount <= 0) {
                    continue;
                }
            }
            stacks.add(new GenericStack(AEItemKey.of(item, null), amount));
        }
        return stacks;
    }

    public static GenericStack getProcessingPatternOutput(SchematicannonInventory inventory, @Nullable String filename, boolean blocksNotLoaded) {
        ItemStack item = Config.useSourceBlueprint ? inventory.getStackInSlot(0) : null;
        if (item == null || item.isEmpty()) {
            item = Config.outputItem;
        }
        CompoundTag tag = item.getTag();
        if (filename != null) {
            MutableComponent component = Component.literal(filename);
            if (blocksNotLoaded) {
                component
                    .append(" ")
                    .append(Component.translatable("create.materialChecklist.blocksNotLoaded"));
            }
            tag = tag == null ? new CompoundTag() : tag.copy();
            updateDisplayNameTag(tag, component);
        } else if (blocksNotLoaded) {
            tag = tag == null ? new CompoundTag() : tag.copy();
            updateDisplayNameTag(tag, Component.translatable("create.materialChecklist.blocksNotLoaded"));
        } else {
            tag = null;
        }
        return new GenericStack(AEItemKey.of(item.getItem(), tag), item.getCount());
    }

    public static void updateDisplayNameTag(CompoundTag root, Component nameComponent) {
        CompoundTag display = new CompoundTag();
        display.putString("Name", Component.Serializer.toJson(nameComponent));
        root.put("display", display);
    }

    public static boolean checkItem(SchematicannonInventory inventory, boolean originalValue) {
        Item itemIn = inventory.getStackInSlot(BOOK_INPUT).getItem();
        ItemStack itemOut = inventory.getStackInSlot(BOOK_OUTPUT);
        boolean isPatternOut = itemOut.getItem() == AEItems.PROCESSING_PATTERN.asItem();
        if (isPatternLike(itemIn)) {
            return !isPatternOut || itemOut.getCount() >= itemOut.getMaxStackSize();
        }
        return originalValue || isPatternOut;
    }
}