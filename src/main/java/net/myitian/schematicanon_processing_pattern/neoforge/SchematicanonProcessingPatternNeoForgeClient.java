package net.myitian.schematicanon_processing_pattern.neoforge;

import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SchematicanonProcessingPattern.MOD_ID, dist = Dist.CLIENT)
public final class SchematicanonProcessingPatternNeoForgeClient {
    public SchematicanonProcessingPatternNeoForgeClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}