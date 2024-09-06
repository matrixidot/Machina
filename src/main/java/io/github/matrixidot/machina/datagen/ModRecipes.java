package io.github.matrixidot.machina.datagen;

import io.github.matrixidot.machina.init.BlocksInit;
import io.github.matrixidot.machina.recipe.builder.OreCrushingRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipes extends RecipeProvider {

    public ModRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }



    @Override
    protected void buildRecipes(RecipeOutput output) {
        new OreCrushingRecipeBuilder(new ItemStack(Items.RAW_IRON), Ingredient.of(Items.STONE), 32, 100)
                .unlockedBy(getHasName(BlocksInit.ORE_CRUSHER.block()), has(BlocksInit.ORE_CRUSHER.block()));

    }
}
