package io.github.matrixidot.machina.recipe.builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SimpleRecipeBuilder implements RecipeBuilder {
    protected final ItemStack result;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public SimpleRecipeBuilder(ItemStack result) {
        this.result = result;
    }

    @Override
    public SimpleRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SimpleRecipeBuilder group(@Nullable String group) {
        return this;
    }


    @Override
    public Item getResult() {
        return this.result.getItem();
    }
}
