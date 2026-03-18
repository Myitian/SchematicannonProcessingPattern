package net.myitian.schematicanon_processing_pattern.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ConfigCodec {
    private final LinkedHashMap<String, Pair<ConsumerWithIOException<JsonReader>, ConsumerWithIOException<JsonWriter>>> fieldMap = new LinkedHashMap<>();

    public Map<String, Pair<ConsumerWithIOException<JsonReader>, ConsumerWithIOException<JsonWriter>>> getFieldMap() {
        return fieldMap;
    }

    public boolean deserialize(JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.BEGIN_OBJECT) {
            return false;
        }
        reader.beginObject();
        Set<String> nameSet = new HashSet<>(fieldMap.size());
        while (reader.peek() == JsonToken.NAME) {
            String name = reader.nextName();
            var pair = fieldMap.get(name);
            if (pair != null) {
                nameSet.add(name);
                pair.getLeft().accept(reader);
            } else {
                reader.skipValue();
            }
        }
        return nameSet.size() == fieldMap.size();
    }

    public boolean serialize(JsonWriter writer) throws IOException {
        writer.beginObject();
        for (var fieldInfo : fieldMap.entrySet()) {
            writer.name(fieldInfo.getKey());
            fieldInfo.getValue().getRight().accept(writer);
        }
        writer.endObject();
        return true;
    }

    @FunctionalInterface
    public interface ConsumerWithIOException<T> {
        void accept(T t) throws IOException;
    }
}