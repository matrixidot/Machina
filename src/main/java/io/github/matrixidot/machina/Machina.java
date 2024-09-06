package io.github.matrixidot.machina;

import io.github.matrixidot.machina.compat.TopCompatibility;
import io.github.matrixidot.machina.datagen.DataGeneration;
import io.github.matrixidot.machina.init.*;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(Machina.MODID)
public class Machina {
    public static final String MODID = "machina";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Machina(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        TabsInit.TAB.register(modEventBus);

        ItemsInit.ITEMS.register(modEventBus);
        BlocksInit.BLOCKS.register(modEventBus);

        RecipeInit.RECIPES.register(modEventBus);
        RecipeInit.SERIALIZERS.register(modEventBus);

        TileInit.TILES.register(modEventBus);
        MenuInit.MENUS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(DataGeneration::generate);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TopCompatibility.register();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TileInit.STIRLING_GENERATOR.get(), (o, dir) -> o.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TileInit.STIRLING_GENERATOR.get(), (o, dir) -> o.getEnergyHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TileInit.ORE_CRUSHER.get(), (o, dir) -> {
            if (dir == null) return o.itemHandler().get();
            if (dir == Direction.DOWN) return o.outputItemHandler().get();
            return o.inputItemHandler().get();
        });
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TileInit.ORE_CRUSHER.get(), (o, dir) -> o.getEnergyHandler());
    }
}
