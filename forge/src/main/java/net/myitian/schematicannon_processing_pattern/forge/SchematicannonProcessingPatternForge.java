package net.myitian.schematicannon_processing_pattern.forge;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;

@Mod(SchematicannonProcessingPattern.MOD_ID)
@Mod.EventBusSubscriber(
    modid = SchematicannonProcessingPattern.MOD_ID,
    bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SchematicannonProcessingPatternForge {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        SchematicannonProcessingPattern.reloadConfig();
    }
}