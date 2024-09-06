package io.github.matrixidot.machina.datagen;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.init.BlocksInit;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.BiConsumer;

public class ModBlockStates extends BlockStateProvider {
    public static final ResourceLocation BOTTOM = ResourceLocation.fromNamespaceAndPath(Machina.MODID, "block/machine_bottom");
    public static final ResourceLocation TOP = ResourceLocation.fromNamespaceAndPath(Machina.MODID, "block/machine_top");
    public static final ResourceLocation SIDE = ResourceLocation.fromNamespaceAndPath(Machina.MODID, "block/machine_side");
    private final ExistingFileHelper helper;
    public ModBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Machina.MODID, exFileHelper);
        this.helper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        registerMachine(BlocksInit.STIRLING_GENERATOR.deferredBlock(), "stirling_generator");
        registerMachine(BlocksInit.ORE_CRUSHER.deferredBlock(), "stirling_generator");
    }

    private void registerMachine(DeferredBlock<?> block, String name) {
        BlockModelBuilder on = models().cube(block.getId().getPath()+"_on", BOTTOM, TOP, modLoc("block/" + name + "_on"),
                SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder off = models().cube(block.getId().getPath()+"_off", BOTTOM, TOP, modLoc("block/" + name),
                SIDE, SIDE, SIDE).texture("particle", SIDE);

        horizontalDirectionBlock(block.get(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? on : off));
    }



    private VariantBlockStateBuilder horizontalDirectionBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
            return bld.build();
        });
        return builder;
    }
    private void applyRotationBld(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case DOWN -> builder.rotationX(90);
            case UP -> builder.rotationX(-90);
            case NORTH -> { }
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }
}
