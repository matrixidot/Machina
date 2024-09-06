package io.github.matrixidot.machina.datagen;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.init.BlocksInit;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Machina.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(BlocksInit.STIRLING_GENERATOR.deferredBlock().getId().getPath(), modLoc("block/stirling_generator_off"));
        withExistingParent(BlocksInit.ORE_CRUSHER.deferredBlock().getId().getPath(), modLoc("block/stirling_generator_off"));
    }
}
