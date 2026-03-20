package net.myitian.schematicanon_processing_pattern;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.core.definitions.AEItems;
import appeng.crafting.pattern.EncodedPatternItem;
import com.google.common.collect.Sets;
import com.simibubi.create.AllDataComponents;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.cannon.MaterialChecklist;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.myitian.schematicanon_processing_pattern.config.Config;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class SchematicanonProcessingPattern {
    public static final String MOD_ID = "schematicanon_processing_pattern";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int BOOK_INPUT = 2;
    public static final int BOOK_OUTPUT = 3;

    public static boolean isPatternLike(Item item) {
        return item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem;
    }

    public static ItemStack getProcessingPattern(SchematicannonBlockEntity schematicannon) {
        List<GenericStack> inputs = getProcessingPatternInputs(schematicannon.checklist, Config.ADD_GATHERED.getAsBoolean());
        GenericStack output = getProcessingPatternOutput(
            schematicannon.inventory,
            Config.SHOW_NBT_FILE_NAME.getAsBoolean() ?
                getBlueprintFilename(schematicannon.inventory.getStackInSlot(0)) : null);
        ItemStack result;
        if (inputs.isEmpty()) {
            result = AEItems.PROCESSING_PATTERN.stack();
        } else {
            result = PatternDetailsHelper.encodeProcessingPattern(inputs, List.of(output));
        }
        if (Config.SHOW_BLOCKS_NOT_LOADED_MESSAGE.getAsBoolean() && schematicannon.checklist.blocksNotLoaded) {
            updateDisplayLoreTag(result, Component.translatable("create.materialChecklist.blocksNotLoaded"));
        }
        return result;
    }

    public static String getBlueprintFilename(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        return stack.get(AllDataComponents.SCHEMATIC_FILE);
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
            stacks.add(new GenericStack(AEItemKey.of(item), amount));
        }
        return stacks;
    }

    public static GenericStack getProcessingPatternOutput(SchematicannonInventory inventory, @Nullable String filename) {
        ItemStack item = Config.getOutputItem();
        if (item == null || item.isEmpty()) {
            item = inventory.getStackInSlot(0);
        }
        if (filename != null) {
            MutableComponent component = Component.literal(filename);
            item = item.isEmpty() ? AllItems.SCHEMATIC.asStack() : item.copy();
            updateDisplayNameTag(item, component);
        }
        return new GenericStack(AEItemKey.of(item), item.getCount());
    }

    public static void updateDisplayNameTag(ItemStack item, Component nameComponent) {
        item.set(DataComponents.CUSTOM_NAME, nameComponent);
    }

    public static void updateDisplayLoreTag(ItemStack item, Component loreComponent) {
        item.set(DataComponents.LORE, new ItemLore(List.of(loreComponent)));
    }

    public static boolean checkItem(SchematicannonInventory inventory, boolean originalValue) {
        Item itemIn = inventory.getStackInSlot(BOOK_INPUT).getItem();
        ItemStack itemOut = inventory.getStackInSlot(BOOK_OUTPUT);
        boolean isPatternOut = itemOut.getItem() == AEItems.PROCESSING_PATTERN.asItem();
        if (isPatternLike(itemIn)) {
            return (!isPatternOut && !itemOut.isEmpty()) || itemOut.getCount() >= itemOut.getMaxStackSize();
        }
        return originalValue || isPatternOut;
    }
}