package net.myitian.schematicanon_processing_pattern.forge;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;

import java.nio.file.Path;

public final class PlatformUtilImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static ItemStack getBlueprint(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(0); // minecraftforge
    }

    public static ItemStack getBookInput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicanonProcessingPattern.BOOK_INPUT); // minecraftforge
    }

    public static ItemStack getBookOutput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicanonProcessingPattern.BOOK_OUTPUT); // minecraftforge
    }
}