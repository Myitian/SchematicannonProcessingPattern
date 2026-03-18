package net.myitian.schematicanon_processing_pattern.fabric;

import net.fabricmc.api.ModInitializer;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;

public class SchematicanonProcessingPatternFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SchematicanonProcessingPattern.init();
    }
}