package net.myitian.schematicanon_processing_pattern.fabric;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;

public final class PlatformUtilImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static ItemStack getBlueprint(SchematicannonBlockEntity schematicannon) {
        return schematicannon.inventory.getStackInSlot(0); // porting-lib transfer
    }
}