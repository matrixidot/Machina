package io.github.matrixidot.machina.recipe.builder;

import io.github.matrixidot.machina.recipe.custom.OreCrushingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class OreCrushingRecipeBuilder extends SimpleRecipeBuilder {
    private final Ingredient input;
    private final int powerUsage;
    private final int processingTicks;

    public OreCrushingRecipeBuilder(ItemStack result, Ingredient input, int powerUsage, int processingTicks) {
        super(result);
        this.input = input;
        this.powerUsage = powerUsage;
        this.processingTicks = processingTicks;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder adv = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(adv::addCriterion);
        OreCrushingRecipe recipe = new OreCrushingRecipe(input, result, powerUsage, processingTicks);
        output.accept(id, recipe, adv.build(id.withPrefix("recipes/")));
    }
}
