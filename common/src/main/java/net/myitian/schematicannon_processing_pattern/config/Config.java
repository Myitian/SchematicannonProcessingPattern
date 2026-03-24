package net.myitian.schematicannon_processing_pattern.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public final class Config {
    private static final ConfigCodec CODEC = new ConfigCodec();
    public static boolean addGathered = false;
    public static boolean showBlocksNotLoadedMessage = true;
    public static boolean showNbtFileName = true;
    public static ItemStack outputItem = null;

    static {
        registerCodec(CODEC.getFieldMap());
    }

    public static void registerCodec(Map<String, Pair<ConfigCodec.ConsumerWithIOException<JsonReader>, ConfigCodec.ConsumerWithIOException<JsonWriter>>> map) {
        map.put("addGathered", Pair.of(
            reader -> addGathered = reader.nextBoolean(),
            writer -> writer.value(addGathered)
        ));
        map.put("showBlocksNotLoadedMessage", Pair.of(
            reader -> showBlocksNotLoadedMessage = reader.nextBoolean(),
            writer -> writer.value(showBlocksNotLoadedMessage)
        ));
        map.put("showNbtFileName", Pair.of(
            reader -> showNbtFileName = reader.nextBoolean(),
            writer -> writer.value(showNbtFileName)
        ));
        map.put("outputItem", Pair.of(
            reader -> outputItem = ConfigCodec.deserializeItemStack(reader),
            writer -> ConfigCodec.serializeItemStack(writer, outputItem)
        ));
    }

    public static boolean load(File configFile) {
        try (var reader = new JsonReader(new FileReader(configFile))) {
            reader.setLenient(true);
            return CODEC.deserialize(reader);
        } catch (Exception e) {
            SchematicannonProcessingPattern.LOGGER.info("Failed to read config: {}", e.getLocalizedMessage());
        }
        return false;
    }

    public static void save(File configFile) {
        try (var writer = new JsonWriter(new FileWriter(configFile))) {
            writer.setHtmlSafe(false);
            writer.setIndent("  ");
            CODEC.serialize(writer);
        } catch (Exception e) {
            SchematicannonProcessingPattern.LOGGER.warn("Failed to write config: {}", e.getLocalizedMessage());
        }
    }
}