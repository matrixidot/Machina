package io.github.matrixidot.machina.tools;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public record DeferredBIPair(DeferredBlock<Block> deferredBlock, DeferredItem<BlockItem> deferredBlockItem) {
    public Block block() {
        return deferredBlock.get();
    }

    public BlockItem item() {
        return deferredBlockItem.get();
    }
}
