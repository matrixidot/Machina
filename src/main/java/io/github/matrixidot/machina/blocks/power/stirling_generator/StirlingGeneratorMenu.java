package io.github.matrixidot.machina.blocks.power.stirling_generator;

import io.github.matrixidot.machina.init.BlocksInit;
import io.github.matrixidot.machina.init.MenuInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.items.SlotItemHandler;

import static io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGeneratorTile.SLOT;
import static io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGeneratorTile.SLOT_COUNT;

public class StirlingGeneratorMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final StirlingGeneratorTile generator;
    public StirlingGeneratorMenu(int containerId, Player player, BlockPos pos) {
        super(MenuInit.STIRLING_GENERATOR.get(), containerId);
        this.pos = pos;
        generator = player.level().getBlockEntity(pos) instanceof StirlingGeneratorTile tile ? tile : null;
        if (generator != null) {
            addSlot(new SlotItemHandler(generator.getItems(), SLOT, 80, 42){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getBurnTime(RecipeType.SMELTING) > 0;
                }
            });
            layoutPlayerInventorySlots(player.getInventory(), 8, 84);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index < SLOT_COUNT) {
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, SLOT, SLOT+1, false)) {
                if (index < 27 + SLOT_COUNT) {
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, BlocksInit.STIRLING_GENERATOR.block());
    }

    public int getPower() {
        return generator.getEnergyStored();
    }

    public int getBurnTime() {
        return generator.getBurnTime();
    }

    public int getMaxBurnTime() {
        return generator.getMaxBurnTime();
    }

    public float getFuelProgress() {
        int maxFuelTicks = generator.getMaxBurnTime();
        if (maxFuelTicks == 0) maxFuelTicks = 200;
        return Mth.clamp((float) generator.getBurnTime() / (float) maxFuelTicks, 0, 1);
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++)  {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(Container playerInventory, int leftCol, int topRow) {
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
