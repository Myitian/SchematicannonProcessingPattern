package net.myitian.schematicanon_processing_pattern.forge;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;

@Mod(SchematicanonProcessingPattern.MOD_ID)
@Mod.EventBusSubscriber(
    modid = SchematicanonProcessingPattern.MOD_ID,
    bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SchematicanonProcessingPatternForge {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        SchematicanonProcessingPattern.reloadConfig();
    }
}