package net.myitian.schematicannon_processing_pattern.fabric;

import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;

import java.nio.file.Path;

public final class PlatformUtilImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static ItemStack getBlueprint(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(0); // porting-lib transfer
    }

    public static ItemStack getBookInput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicannonProcessingPattern.BOOK_INPUT); // porting-lib transfer
    }

    public static ItemStack getBookOutput(SchematicannonInventory inventory) {
        return inventory.getStackInSlot(SchematicannonProcessingPattern.BOOK_OUTPUT); // porting-lib transfer
    }
}