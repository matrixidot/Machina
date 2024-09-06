package io.github.matrixidot.machina.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

    public CustomEnergyStorage(int capacity) {
        super(capacity);
    }

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void distributeEnergy(Level level, BlockPos pos, BlockState state) {
        if (getEnergyStored() <= 0) return;
        for (Direction dir : Direction.values()) {
            var energyStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos.relative(dir), dir.getOpposite());
            if (energyStorage != null && energyStorage.canReceive()) {
                int recieved = energyStorage.receiveEnergy(Math.min(getEnergyStored(), maxExtract), false);
                extractEnergy(recieved, false);
                if (level != null) {
                    level.blockEntityChanged(pos);
                    if (!state.isAir())
                        level.updateNeighbourForOutputSignal(pos, state.getBlock());
                }
            }
        }
    }

    public boolean hasEnough(int energy) {
        return getEnergyStored() <= energy;
    }
}
