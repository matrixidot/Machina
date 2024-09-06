package io.github.matrixidot.machina.blocks.processing.ore_crusher;

import io.github.matrixidot.machina.blocks.abstracts.AbstractTileEntityImpl;
import io.github.matrixidot.machina.init.RecipeInit;
import io.github.matrixidot.machina.init.TileInit;
import io.github.matrixidot.machina.recipe.custom.OreCrushingRecipe;
import io.github.matrixidot.machina.tools.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;

public class OreCrusherTile extends AbstractTileEntityImpl {
    public static final int CAPACITY = 8_192;

    public static int SLOT_COUNT = 2;

    private final ItemStackHandler input = createItemHandler(1);
    private final ItemStackHandler output = createItemHandler(1);

    private final Lazy<IItemHandler> inputItemHandler = Lazy.of(() -> input);
    private final Lazy<IItemHandler> outputItemHandler = Lazy.of(() -> output);
    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> new CombinedInvWrapper(input, output));

    private final CustomEnergyStorage energy = new CustomEnergyStorage(CAPACITY, 1000, 1000);
    private final Lazy<IEnergyStorage> energyHandler = Lazy.of(() -> energy);

    private final RecipeManager.CachedCheck<SingleRecipeInput, OreCrushingRecipe> recipeCheck;

    private int powerUsage;
    private int progress;
    private int maxProgress;

    public OreCrusherTile(BlockPos pos, BlockState blockState) {
        super(TileInit.ORE_CRUSHER.get(), pos, blockState);
        recipeCheck = RecipeManager.createCheck(RecipeInit.ORE_CRUSHING.get());
    }

    @Override
    public void tickServer() {
        if (!canCraft()) return;
        if (!energy.hasEnough(powerUsage)) {
            progress = Math.max(0, progress - 2);
            return;
        }
        if (progress >= maxProgress) {
            crush();
            return;
        }
        progress++;
        super.tickServer();
    }

    private void crush() {
        if (!canCraft()) return;
        ItemStack inputStack = input.getStackInSlot(0);
        RecipeHolder<OreCrushingRecipe> recipe = recipeCheck.getRecipeFor(new SingleRecipeInput(inputStack), level).orElse(null);

        ItemStack outputSlot = output.getStackInSlot(0);
        ItemStack outputItem = recipe.value().assemble(new SingleRecipeInput(inputStack), level.registryAccess());
        if (outputSlot.isEmpty()) {
            output.setStackInSlot(0, outputItem);
        } else {
            outputSlot.grow(outputItem.getCount());
        }
        inputStack.shrink(recipe.value().input().getItems()[0].getCount());
    }

    // No recipe found, or conflicting items in output slot with recipe or too many items in output slot
    private boolean canCraft() {
        ItemStack inputStack = input.getStackInSlot(0);
        if (inputStack.isEmpty()) return false;
        RecipeHolder<OreCrushingRecipe> recipe = recipeCheck.getRecipeFor(new SingleRecipeInput(inputStack), level).orElse(null);
        if (recipe == null) {
            powerUsage = 0;
            maxProgress = 200;
            progress = 0;
            return false;
        }

        ItemStack outStack = output.getStackInSlot(0);
        ItemStack result = recipe.value().output();
        // There is an item in the output and either its not the same item or the total sizes are greater than the max size
        if (!outStack.isEmpty() && (!ItemStack.isSameItemSameComponents(outStack, result) || outStack.getCount() + result.getCount() > outStack.getMaxStackSize())) {
            powerUsage = 0;
            maxProgress = 200;
            progress = 0;
            return false;
        }
        powerUsage = recipe.value().powerUsage();
        maxProgress = recipe.value().processingTicks();
        return true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("progress", progress);
        tag.putInt("max_progress", maxProgress);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("input", input.serializeNBT(registries));
        tag.put("output", output.serializeNBT(registries));

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
        energy.deserializeNBT(registries, IntTag.valueOf(tag.getInt("energy")));

        if (tag.contains("input"))
            input.deserializeNBT(registries, tag.getCompound("input"));
        if (tag.contains("output"))
            output.deserializeNBT(registries, tag.getCompound("output"));

    }

    @Override
    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    public ItemStackHandler input() {
        return input;
    }

    public ItemStackHandler output() {
        return output;
    }

    public Lazy<IItemHandler> inputItemHandler() {
        return inputItemHandler;
    }

    public Lazy<IItemHandler> outputItemHandler() {
        return outputItemHandler;
    }

    public Lazy<IItemHandler> itemHandler() {
        return itemHandler;
    }

    public int energy() {
        return energy.getEnergyStored();
    }

    public int maxEnergy() {
        return energy.getMaxEnergyStored();
    }

    public int progress() {
        return progress;
    }

    public int maxProgress() {
        return maxProgress;
    }

    public int powerUsage() {
        return powerUsage;
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler.get();
    }
}
