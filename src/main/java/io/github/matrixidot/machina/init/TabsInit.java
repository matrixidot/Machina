package io.github.matrixidot.machina.init;

import io.github.matrixidot.machina.Machina;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TabsInit {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Machina.MODID);

    //TODO: Items, Blocks, Machines Tabs

    public static Supplier<CreativeModeTab> MACHINE_TAB = TAB.register("machina_machines", () -> CreativeModeTab.builder()
            .title(Component.translatable("tab.machina_machines"))
            .icon(() -> new ItemStack(BlocksInit.STIRLING_GENERATOR.block()))
            .displayItems((featureFlags, output) -> {
                output.accept(BlocksInit.STIRLING_GENERATOR.block());
            })
            .build());
}
