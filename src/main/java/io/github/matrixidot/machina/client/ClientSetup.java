package io.github.matrixidot.machina.client;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGeneratorScreen;
import io.github.matrixidot.machina.blocks.processing.ore_crusher.OreCrusherScreen;
import io.github.matrixidot.machina.init.MenuInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Machina.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuInit.STIRLING_GENERATOR.get(), StirlingGeneratorScreen::new);
        event.register(MenuInit.ORE_CRUSHER.get(), OreCrusherScreen::new);
    }
}
