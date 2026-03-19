package net.myitian.schematicanon_processing_pattern.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.Minecraft;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;

public class SchematicanonProcessingPatternFabric implements ModInitializer {
    private static void commonSetup(Minecraft client) {
        SchematicanonProcessingPattern.reloadConfig();
    }

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(SchematicanonProcessingPatternFabric::commonSetup);
    }
}