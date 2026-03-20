package net.myitian.schematicanon_processing_pattern;

import net.minecraft.nbt.*;

public final class StringBuilderTagVisitor implements TagVisitor {
    private final StringBuilder builder;

    public StringBuilderTagVisitor(StringBuilder builder) {
        this.builder = builder;
    }

    public static boolean isSimpleString(String text) {
        for (int i = 0, length = text.length(); i < length; i++) {
            switch (text.charAt(i)) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '_', '+', '-',
                     'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                     'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                     'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                     'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' -> {
                }
                default -> {
                    return false;
                }
            }
        }
        return true;
    }

    public void visitString(StringTag tag) {
        builder.append(StringTag.quoteAndEscape(tag.getAsString()));
    }

    public void visitByte(ByteTag tag) {
        builder.append(tag.getAsByte()).append('b');
    }

    public void visitShort(ShortTag tag) {
        builder.append(tag.getAsShort()).append('s');
    }

    public void visitInt(IntTag tag) {
        builder.append(tag.getAsInt());
    }

    public void visitLong(LongTag tag) {
        builder.append(tag.getAsLong()).append('L');
    }

    public void visitFloat(FloatTag tag) {
        builder.append(tag.getAsFloat()).append('f');
    }

    public void visitDouble(DoubleTag tag) {
        builder.append(tag.getAsDouble()).append('d');
    }

    public void visitByteArray(ByteArrayTag tag) {
        byte[] bs = tag.getAsByteArray();
        builder.append("[B;");
        for (int i = 0; i < bs.length; i++) {
            if (i != 0) {
                builder.append(',');
            }
            builder.append(bs[i]).append('B');
        }
        builder.append(']');
    }

    public void visitIntArray(IntArrayTag tag) {
        int[] is = tag.getAsIntArray();
        builder.append("[I;");
        for (int i = 0; i < is.length; i++) {
            if (i != 0) {
                builder.append(',');
            }
            builder.append(is[i]);
        }
        builder.append(']');
    }

    public void visitLongArray(LongArrayTag tag) {
        long[] ls = tag.getAsLongArray();
        builder.append("[L;");
        for (int i = 0; i < ls.length; i++) {
            if (i != 0) {
                builder.append(',');
            }
            builder.append(ls[i]).append('L');
        }
        builder.append(']');
    }

    public void visitList(ListTag tag) {
        builder.append('[');
        for (int i = 0; i < tag.size(); i++) {
            if (i != 0) {
                builder.append(',');
            }
            tag.get(i).accept(this);
        }
        builder.append(']');
    }

    public void visitCompound(CompoundTag tag) {
        builder.append('{');
        boolean first = true;
        for (String key : tag.getAllKeys()) {
            if (first) {
                first = false;
            } else {
                builder.append(',');
            }
            builder.append(isSimpleString(key) ? key : StringTag.quoteAndEscape(key)).append(':');
            Tag value = tag.get(key);
            assert value != null;
            value.accept(this);
        }
        builder.append('}');
    }

    public void visitEnd(EndTag tag) {
    }

    public String toString() {
        return builder.toString();
    }
}