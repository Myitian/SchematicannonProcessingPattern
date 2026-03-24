package net.myitian.schematicannon_processing_pattern.neoforge;

import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;
import net.myitian.schematicannon_processing_pattern.config.Config;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(SchematicannonProcessingPattern.MOD_ID)
@EventBusSubscriber(modid = SchematicannonProcessingPattern.MOD_ID)
public final class SchematicannonProcessingPatternNeoForge {
    public SchematicannonProcessingPatternNeoForge(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    @SubscribeEvent
    public static void onModConfig(ModConfigEvent event) {
        Config.markDirty();
    }
}