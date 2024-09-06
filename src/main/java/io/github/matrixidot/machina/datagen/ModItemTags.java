package io.github.matrixidot.machina.datagen;

import io.github.matrixidot.machina.Machina;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(output, lookupProvider, blockTags.contentsGetter(), Machina.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}
