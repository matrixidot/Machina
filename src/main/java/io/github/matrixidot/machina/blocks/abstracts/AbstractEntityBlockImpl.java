package io.github.matrixidot.machina.blocks.abstracts;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEntityBlockImpl extends Block implements EntityBlock {
    public AbstractEntityBlockImpl(Properties properties) {
        super(properties);
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;

        return (lvl, pos, st, tile) -> {
            if (tile instanceof AbstractTileEntityImpl baseTile) {
                baseTile.tickServer();
            }
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof AbstractTileEntityImpl) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return translatableScreenName();
                    }

                    @Override
                    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
                        return getMenu(containerId, player, pos);
                    }
                };
                player.openMenu(containerProvider, buf -> buf.writeBlockPos(pos));
                menuOpened(player);
            } else {
                throw new IllegalStateException("Named container provider is missing");
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof AbstractTileEntityImpl baseTile) {
                baseTile.dropItems();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public abstract Component translatableScreenName();
    public abstract AbstractContainerMenu getMenu(int containerId, Player player, BlockPos pos);
    public void menuOpened(Player player) {}
}
