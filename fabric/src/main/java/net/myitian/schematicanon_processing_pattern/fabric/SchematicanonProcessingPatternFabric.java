package net.myitian.schematicanon_processing_pattern.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;

public final class SchematicanonProcessingPatternFabric implements ModInitializer {
    public static void onServerStarting(MinecraftServer mc) {
        SchematicanonProcessingPattern.reloadConfig();
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(SchematicanonProcessingPatternFabric::onServerStarting);
    }
}