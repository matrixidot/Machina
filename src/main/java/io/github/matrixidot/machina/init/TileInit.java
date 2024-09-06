package io.github.matrixidot.machina.init;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGeneratorTile;
import io.github.matrixidot.machina.blocks.processing.ore_crusher.OreCrusherTile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TileInit {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Machina.MODID);


    public static final Supplier<BlockEntityType<StirlingGeneratorTile>> STIRLING_GENERATOR = TILES.register("stirling_generator",
            () -> BlockEntityType.Builder.of(StirlingGeneratorTile::new, BlocksInit.STIRLING_GENERATOR.block()).build(null));

    public static final Supplier<BlockEntityType<OreCrusherTile>> ORE_CRUSHER = TILES.register("ore_crusher",
            () -> BlockEntityType.Builder.of(OreCrusherTile::new, BlocksInit.ORE_CRUSHER.block()).build(null));
}
