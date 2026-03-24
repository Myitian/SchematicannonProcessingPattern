package net.myitian.schematicannon_processing_pattern.neoforge;

import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SchematicannonProcessingPattern.MOD_ID, dist = Dist.CLIENT)
public final class SchematicannonProcessingPatternNeoForgeClient {
    public SchematicannonProcessingPatternNeoForgeClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}