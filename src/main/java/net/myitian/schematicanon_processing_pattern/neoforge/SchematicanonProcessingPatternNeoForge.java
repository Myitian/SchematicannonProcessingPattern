package net.myitian.schematicanon_processing_pattern.neoforge;

import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;
import net.myitian.schematicanon_processing_pattern.config.Config;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(SchematicanonProcessingPattern.MOD_ID)
@EventBusSubscriber(modid = SchematicanonProcessingPattern.MOD_ID)
public class SchematicanonProcessingPatternNeoForge {
    public SchematicanonProcessingPatternNeoForge(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    @SubscribeEvent
    public static void onModConfig(ModConfigEvent event) {
        Config.markDirty();
    }
}