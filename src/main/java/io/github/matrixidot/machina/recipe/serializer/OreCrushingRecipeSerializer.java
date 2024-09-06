package io.github.matrixidot.machina.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.matrixidot.machina.recipe.custom.OreCrushingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class OreCrushingRecipeSerializer implements RecipeSerializer<OreCrushingRecipe> {
    public static final MapCodec<OreCrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(OreCrushingRecipe::input),
            ItemStack.CODEC.fieldOf("result").forGetter(OreCrushingRecipe::output),
            Codec.INT.fieldOf("powerUsage").forGetter(OreCrushingRecipe::powerUsage),
            Codec.INT.fieldOf("processingTicks").forGetter(OreCrushingRecipe::processingTicks)
    ).apply(inst, OreCrushingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, OreCrushingRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, OreCrushingRecipe::input,
            ItemStack.STREAM_CODEC, OreCrushingRecipe::output,
            ByteBufCodecs.INT, OreCrushingRecipe::powerUsage,
            ByteBufCodecs.INT, OreCrushingRecipe::processingTicks,
            OreCrushingRecipe::new
    );


    @Override
    public MapCodec<OreCrushingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, OreCrushingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
