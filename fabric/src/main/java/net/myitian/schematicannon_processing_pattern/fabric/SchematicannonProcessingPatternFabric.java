package net.myitian.schematicannon_processing_pattern.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;

public final class SchematicannonProcessingPatternFabric implements ModInitializer {
    public static void onServerStarting(MinecraftServer mc) {
        SchematicannonProcessingPattern.reloadConfig();
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(SchematicannonProcessingPatternFabric::onServerStarting);
    }
}