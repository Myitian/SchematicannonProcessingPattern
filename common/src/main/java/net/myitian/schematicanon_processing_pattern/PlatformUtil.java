package net.myitian.schematicanon_processing_pattern;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;

public final class PlatformUtil {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getBlueprint(SchematicannonBlockEntity schematicannon) {
        throw new AssertionError();
    }
}