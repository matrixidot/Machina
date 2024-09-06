package io.github.matrixidot.machina.blocks.power.stirling_generator;

import io.github.matrixidot.machina.blocks.abstracts.AbstractEntityBlockImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class StirlingGenerator extends AbstractEntityBlockImpl {
    public static final String SCREEN = "machina.screen.stirling_generator";
    public StirlingGenerator() {
        super(Properties.of().strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.METAL));
    }

    @Override
    public Component translatableScreenName() {
        return Component.translatable(SCREEN);
    }

    @Override
    public AbstractContainerMenu getMenu(int containerId, Player player, BlockPos pos) {
        return new StirlingGeneratorMenu(containerId, player, pos);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new StirlingGeneratorTile(blockPos, blockState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(BlockStateProperties.POWERED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, BlockStateProperties.HORIZONTAL_FACING);
    }
}