package net.myitian.schematicanon_processing_pattern.config;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Map;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue ADD_GATHERED = BUILDER
        .comment("Whether should add already collected item to result processing pattern.")
        .define("addGathered", false);
    public static final ModConfigSpec.BooleanValue SHOW_BLOCKS_NOT_LOADED_MESSAGE = BUILDER
        .comment("Whether should add the 'create.materialChecklist.blocksNotLoaded' message to item lore when some chunks not loaded.")
        .define("showBlocksNotLoadedMessage", true);
    public static final ModConfigSpec.BooleanValue SHOW_NBT_FILE_NAME = BUILDER
        .comment("Whether should rename the output item to the nbt file name.")
        .define("showNbtFileName", true);
    public static final ModConfigSpec.ConfigValue<String> OUTPUT_ITEM_RAW = BUILDER
        .comment("The placeholder output item in processing pattern. Leave empty to use source blueprint.")
        .define("outputItem", "");
    public static final ModConfigSpec SPEC = BUILDER.build();
    private static final RegistryAccess.Frozen ITEM_REGISTRY_ACCESS = new RegistryAccess.ImmutableRegistryAccess(
        Map.of(BuiltInRegistries.ITEM.key(), BuiltInRegistries.ITEM)).freeze();
    private static final ItemParser ITEM_PARSER = new ItemParser(ITEM_REGISTRY_ACCESS);
    private static boolean dirty = true;
    private static ItemStack outputItem = ItemStack.EMPTY;

    public static void markDirty() {
        dirty = true;
    }

    public static ItemStack getOutputItem() {
        if (dirty) {
            outputItem = getItemStack(OUTPUT_ITEM_RAW.get(), 1);
            dirty = false;
        }
        return outputItem;
    }

    private static ItemStack getItemStack(String string, int count) {
        StringReader sr = new StringReader(string);
        try {
            ItemParser.ItemResult result = ITEM_PARSER.parse(sr);
            ItemInput item = new ItemInput(result.item(), result.components());
            return item.createItemStack(count, true);
        } catch (CommandSyntaxException e) {
            return ItemStack.EMPTY;
        }
    }
}
