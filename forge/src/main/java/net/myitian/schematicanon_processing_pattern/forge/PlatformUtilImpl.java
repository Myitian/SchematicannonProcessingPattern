package net.myitian.schematicanon_processing_pattern.forge;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class PlatformUtilImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static ItemStack getBlueprint(SchematicannonBlockEntity schematicannon) {
        return schematicannon.inventory.getStackInSlot(0);
    }
}