package io.github.matrixidot.machina.blocks.power.stirling_generator;

import io.github.matrixidot.machina.blocks.abstracts.AbstractTileEntityImpl;
import io.github.matrixidot.machina.init.TileInit;
import io.github.matrixidot.machina.tools.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class StirlingGeneratorTile extends AbstractTileEntityImpl {
    public static final int GENERATE = 50;
    public static final int CAPACITY = 100_000;

    public static int SLOT_COUNT = 1;
    public static int SLOT = 0;

    private final ItemStackHandler items = createItemHandler(1);
    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> items);

    private final CustomEnergyStorage energy = new CustomEnergyStorage(CAPACITY, 1000, 1000);
    private final Lazy<IEnergyStorage> energyHandler = Lazy.of(() -> energy);

    private int burnTime;
    private int maxBurnTime;

    public StirlingGeneratorTile(BlockPos pos, BlockState blockState) {
        super(TileInit.STIRLING_GENERATOR.get(), pos, blockState);
    }

    @Override
    public void tickServer() {
        generateEnergy();
        energy.distributeEnergy(level, worldPosition, getBlockState());
        super.tickServer();
    }

    private void generateEnergy() {
        if (energy.getEnergyStored() < energy.getMaxEnergyStored()) {
            if (burnTime <= 0) {
                ItemStack fuel = items.getStackInSlot(0);
                if (fuel.isEmpty()) return;
                setBurnTime(fuel.getBurnTime(RecipeType.SMELTING));
                maxBurnTime = fuel.getBurnTime(RecipeType.SMELTING);
                if (burnTime <= 0)
                    return;
                items.extractItem(0, 1, false);
            } else {
                setBurnTime(burnTime - 1);
                energy.receiveEnergy(GENERATE, false);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("burnTime", burnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("fuel", items.serializeNBT(registries));
    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        burnTime = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("maxBurnTime");
        if (tag.contains("fuel"))
            items.deserializeNBT(registries, tag.getCompound("fuel"));

        if (tag.contains("energy"))
            energy.deserializeNBT(registries, IntTag.valueOf(tag.getInt("energy")));
    }

    @Override
    public void dropItems() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.get().getSlots());
        for (int i = 0; i < itemHandler.get().getSlots(); i++) {
            inventory.setItem(i, itemHandler.get().getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, inventory);
    }

    private void setBurnTime(int burnTime) {
        if (this.burnTime == burnTime) return;
        this.burnTime = burnTime;
        if (getBlockState().getValue(BlockStateProperties.POWERED) != burnTime > 0) {
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlockStateProperties.POWERED, burnTime > 0));
        }
        setChanged();
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public IItemHandler getItemHandler() {
        return itemHandler.get();
    }

    public int getEnergyStored() {
        return energy.getEnergyStored();
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler.get();
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }
}
