package io.github.matrixidot.machina.datagen;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.init.BlocksInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Machina.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlocksInit.STIRLING_GENERATOR.block(),
                        BlocksInit.ORE_CRUSHER.block());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlocksInit.STIRLING_GENERATOR.block(),
                        BlocksInit.ORE_CRUSHER.block());
    }
}
