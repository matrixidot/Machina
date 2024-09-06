package io.github.matrixidot.machina.recipe.custom;

import io.github.matrixidot.machina.init.RecipeInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class OreCrushingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient input;
    private final ItemStack output;
    private final int powerUsage;
    private final int processingTicks;

    public OreCrushingRecipe(Ingredient input, ItemStack output, int powerUsage, int processingTicks) {
        this.input = input;
        this.output = output;
        this.powerUsage = powerUsage;
        this.processingTicks = processingTicks;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.input);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.input.test(input.item());
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.ORE_CRUSHING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.ORE_CRUSHING_SLZR.get();
    }

    public Ingredient input() {
        return input;
    }

    public ItemStack output() {
        return output;
    }

    public int powerUsage() {
        return powerUsage;
    }

    public int processingTicks() {
        return processingTicks;
    }
}
