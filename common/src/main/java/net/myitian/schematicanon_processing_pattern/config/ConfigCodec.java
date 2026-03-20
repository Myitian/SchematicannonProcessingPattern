package net.myitian.schematicanon_processing_pattern.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicanon_processing_pattern.StringBuilderTagVisitor;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class ConfigCodec {
    private final LinkedHashMap<String, Pair<ConsumerWithIOException<JsonReader>, ConsumerWithIOException<JsonWriter>>> fieldMap = new LinkedHashMap<>();

    public static ItemStack deserializeItemStack(JsonReader reader) throws IOException {
        switch (reader.peek()) {
            case NULL -> {
                reader.nextNull();
                return ItemStack.EMPTY;
            }
            case BEGIN_ARRAY -> {
                String item = reader.nextString();
                int count = reader.nextInt();
                reader.endArray();
                return getItemStack(item, count);
            }
            default -> {
                return getItemStack(reader.nextString(), 1);
            }
        }
    }

    public static void serializeItemStack(JsonWriter writer, ItemStack item) throws IOException {
        if (item == null || item.isEmpty()) {
            writer.nullValue();
            return;
        }
        ResourceLocation id = Registry.ITEM.getKey(item.getItem());
        StringBuilder sb = new StringBuilder()
            .append(id.getNamespace())
            .append(':')
            .append(id.getPath());
        StringBuilderTagVisitor visitor = new StringBuilderTagVisitor(sb);
        if (item.hasTag()) {
            assert item.getTag() != null;
            item.getTag().accept(visitor);
        }
        String result = visitor.toString();
        if (item.getCount() <= 1) {
            writer.value(result);
        } else {
            writer.beginArray();
            writer.value(result);
            writer.value(item.getCount());
            writer.endArray();
        }
    }

    private static ItemStack getItemStack(String string, int count) {
        StringReader sr = new StringReader(string);
        try {
            ItemParser result = new ItemParser(sr, false).parse();
            ItemInput item = new ItemInput(result.getItem(), result.getNbt());
            return item.createItemStack(count, true);
        } catch (CommandSyntaxException e) {
            return ItemStack.EMPTY;
        }
    }

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

    public void serialize(JsonWriter writer) throws IOException {
        writer.beginObject();
        for (var fieldInfo : fieldMap.entrySet()) {
            writer.name(fieldInfo.getKey());
            fieldInfo.getValue().getRight().accept(writer);
        }
        writer.endObject();
    }

    @FunctionalInterface
    public interface ConsumerWithIOException<T> {
        void accept(T t) throws IOException;
    }
}