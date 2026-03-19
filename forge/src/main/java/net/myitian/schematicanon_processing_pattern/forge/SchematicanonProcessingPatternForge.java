package net.myitian.schematicanon_processing_pattern.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;

@Mod(SchematicanonProcessingPattern.MOD_ID)
public class SchematicanonProcessingPatternForge {
    @Mod.EventBusSubscriber(
        modid = SchematicanonProcessingPattern.MOD_ID,
        value = Dist.DEDICATED_SERVER,
        bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBus {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            SchematicanonProcessingPattern.reloadConfig();
        }
    }
}