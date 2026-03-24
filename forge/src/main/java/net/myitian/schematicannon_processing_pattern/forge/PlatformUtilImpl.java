package net.myitian.schematicannon_processing_pattern.forge;

import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;

import java.nio.file.Path;

public final class PlatformUtilImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static ItemStack getBlueprint(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(0); // minecraftforge
    }

    public static ItemStack getBookInput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicannonProcessingPattern.BOOK_INPUT); // minecraftforge
    }

    public static ItemStack getBookOutput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicannonProcessingPattern.BOOK_OUTPUT); // minecraftforge
    }
}