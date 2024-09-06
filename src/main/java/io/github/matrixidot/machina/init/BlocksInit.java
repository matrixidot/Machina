package io.github.matrixidot.machina.init;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGenerator;
import io.github.matrixidot.machina.blocks.processing.ore_crusher.OreCrusher;
import io.github.matrixidot.machina.tools.DeferredBIPair;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlocksInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Machina.MODID);

    // Power Generator
    public static final DeferredBIPair STIRLING_GENERATOR = registerBlockAndItem("stirling_generator", StirlingGenerator::new);

    // Copper Age
    public static final DeferredBIPair ORE_CRUSHER = registerBlockAndItem("ore_crusher", OreCrusher::new);

    private static <B extends Block> DeferredBIPair registerBlockAndItem(String name, Supplier<B> block, Item.Properties properties) {
        DeferredBlock<Block> deferredBlock = BLOCKS.register(name, block);
        DeferredItem<BlockItem> deferredItem = ItemsInit.ITEMS.registerSimpleBlockItem(deferredBlock, properties);
        return new DeferredBIPair(deferredBlock, deferredItem);
    }
    private static <B extends Block> DeferredBIPair registerBlockAndItem(String name, Supplier<B> block) {
        DeferredBlock<Block> deferredBlock = BLOCKS.register(name, block);
        DeferredItem<BlockItem> deferredItem = ItemsInit.ITEMS.registerSimpleBlockItem(deferredBlock, new Item.Properties());
        return new DeferredBIPair(deferredBlock, deferredItem);
    }
}

