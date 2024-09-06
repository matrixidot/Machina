package io.github.matrixidot.machina.init;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGeneratorMenu;
import io.github.matrixidot.machina.blocks.processing.ore_crusher.OreCrusherMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, Machina.MODID);

    public static final Supplier<MenuType<StirlingGeneratorMenu>> STIRLING_GENERATOR = MENUS.register("stirling_generator",
            () -> IMenuTypeExtension.create((id, inv, data) -> new StirlingGeneratorMenu(id, inv.player, data.readBlockPos())));

    public static final Supplier<MenuType<OreCrusherMenu>> ORE_CRUSHER = MENUS.register("ore_crusher",
            () -> IMenuTypeExtension.create((id, inv, data) -> new OreCrusherMenu(id, inv.player, data.readBlockPos())));
}
