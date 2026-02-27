package net.tokyosu.craftory.menu.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public abstract class MenuCraftingBase extends MenuContainerBase {
    private int resultSlotIndex = 0;

    protected MenuCraftingBase(@NotNull MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    protected MenuCraftingBase(@NotNull MenuType<?> menuType, int containerId, @NotNull Inventory playerInventory) {
        super(menuType, containerId, playerInventory);
    }

    public abstract @NotNull Vector2i getSlotStartingPos();
    public abstract @NotNull Vector2i getResultSlotPos();

    public abstract int getRowCount();
    public abstract int getColumnCount();

    public int getSlotCount(boolean haveResultSlot) {
        return (this.getColumnCount() * this.getRowCount()) + (haveResultSlot ? 1 : 0);
    }

    public @NotNull Slot getResultSlot() {
        return this.slots.get(this.resultSlotIndex);
    }

    public @NotNull ItemStack getResultStack() {
        return this.container.getItem(this.resultSlotIndex);
    }

    public void setCraftingGridSlots() {
        var startPos = this.getSlotStartingPos();
        var resultPos = this.getResultSlotPos();
        int rowCount = this.getRowCount();
        int lastSlotIndex = 0;
        for (int rowId = 0; rowId < rowCount; rowId ++) {
            for (int columnId = 0; columnId < this.getColumnCount(); columnId++) {
                lastSlotIndex = columnId + (rowId * rowCount);
                int x = startPos.x + (columnId * 18);
                int y = startPos.y + (rowId * 18);
                this.addSlot(new Slot(this.container, lastSlotIndex, x, y));
            }
        }
        this.resultSlotIndex = lastSlotIndex + 1;
        this.addSlot(new Slot(this.container, this.resultSlotIndex, resultPos.x, resultPos.y)); // Result
    }
}
