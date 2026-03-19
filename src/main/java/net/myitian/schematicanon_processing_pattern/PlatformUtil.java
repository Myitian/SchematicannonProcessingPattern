package net.myitian.schematicanon_processing_pattern;

import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.ItemStack;

public final class PlatformUtil {
    public static ItemStack getBlueprint(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(0);
    }

    public static ItemStack getBookInput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicanonProcessingPattern.BOOK_INPUT);
    }

    public static ItemStack getBookOutput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicanonProcessingPattern.BOOK_OUTPUT);
    }
}