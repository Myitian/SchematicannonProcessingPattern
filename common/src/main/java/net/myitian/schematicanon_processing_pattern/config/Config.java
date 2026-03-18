package net.myitian.schematicanon_processing_pattern.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public final class Config {
    private static final ConfigCodec CODEC = new ConfigCodec();

    static {
        registerCodec(CODEC.getFieldMap());
    }

    public static void registerCodec(Map<String, Pair<ConfigCodec.ConsumerWithIOException<JsonReader>, ConfigCodec.ConsumerWithIOException<JsonWriter>>> map) {
    }

    public static boolean load(File configFile) {
        try (var reader = new JsonReader(new FileReader(configFile))) {
            reader.setLenient(true);
            return CODEC.deserialize(reader);
        } catch (Exception e) {
            SchematicanonProcessingPattern.LOGGER.info("Failed to read config: {}", e.getLocalizedMessage());
        }
        return false;
    }

    public static boolean save(File configFile) {
        try (var writer = new JsonWriter(new FileWriter(configFile))) {
            writer.setHtmlSafe(false);
            writer.setIndent("  ");
            return CODEC.serialize(writer);
        } catch (Exception e) {
            SchematicanonProcessingPattern.LOGGER.warn("Failed to write config: {}", e.getLocalizedMessage());
        }
        return false;
    }
}