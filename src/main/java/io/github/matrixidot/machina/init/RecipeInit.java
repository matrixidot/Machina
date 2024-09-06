package io.github.matrixidot.machina.init;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.recipe.custom.OreCrushingRecipe;
import io.github.matrixidot.machina.recipe.serializer.OreCrushingRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeInit {
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Machina.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Machina.MODID);


    public static final Supplier<RecipeType<OreCrushingRecipe>> ORE_CRUSHING = RECIPES.register("ore_crushing",
            () -> RecipeType.<OreCrushingRecipe>simple(ResourceLocation.fromNamespaceAndPath(Machina.MODID, "ore_crushing")));

    public static final Supplier<RecipeSerializer<OreCrushingRecipe>> ORE_CRUSHING_SLZR = SERIALIZERS.register("ore_crushing", OreCrushingRecipeSerializer::new);



}
