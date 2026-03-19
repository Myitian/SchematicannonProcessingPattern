package net.myitian.schematicanon_processing_pattern;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.core.definitions.AEItems;
import appeng.crafting.pattern.EncodedPatternItem;
import com.google.common.collect.Sets;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.cannon.MaterialChecklist;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static void init() {
        // TODO: config
    }

    public static boolean isPatternLike(Item item) {
        return item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem;
    }

    public static ItemStack getProcessingPattern(SchematicannonBlockEntity schematicannon) {
        ItemStack blueprint = PlatformUtil.getBlueprint(schematicannon);
        String filename = getBlueprintFilename(blueprint);
        List<GenericStack> inputs = getProcessingPatternInputs(schematicannon.checklist, false);
        GenericStack output = getProcessingPatternOutput(filename, schematicannon.checklist.blocksNotLoaded);
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

    public static GenericStack getProcessingPatternOutput(@Nullable String filename, boolean blocksNotLoaded) {
        CompoundTag tag;
        if (filename != null) {
            MutableComponent component = createLiteralComponent(filename);
            if (blocksNotLoaded) {
                component
                    .append(" ")
                    .append(createTranslatableComponent("create.materialChecklist.blocksNotLoaded"));
            }
            tag = createDisplayNameTag(component);
        } else if (blocksNotLoaded) {
            tag = createDisplayNameTag(createTranslatableComponent("create.materialChecklist.blocksNotLoaded"));
        } else {
            tag = null;
        }
        return new GenericStack(AEItemKey.of(AllItems.SCHEMATIC.get(), tag), 1);
    }

    public static CompoundTag createDisplayNameTag(Component nameComponent) {
        CompoundTag root = new CompoundTag();
        CompoundTag display = new CompoundTag();
        display.putString("Name", Component.Serializer.toJson(nameComponent));
        root.put("display", display);
        return root;
    }

    public static MutableComponent createTranslatableComponent(String key) {
        return Component.translatable(key);
    }

    public static MutableComponent createLiteralComponent(String text) {
        return Component.literal(text);
    }
}