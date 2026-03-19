package net.myitian.schematicanon_processing_pattern;

import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;

public final class PlatformUtil {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getBlueprint(SchematicannonInventory inventory) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getBookInput(SchematicannonInventory inventory) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemStack getBookOutput(SchematicannonInventory inventory) {
        throw new AssertionError();
    }
}