package net.tokyosu.craftory.menu.minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.registry.MenuRegistry;
import org.jetbrains.annotations.NotNull;

public class CraftingTableMenu extends MenuContainerBase {
    public CraftingTableMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(MenuRegistry.CRAFTING_TABLE_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    public CraftingTableMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(MenuRegistry.CRAFTING_TABLE_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    @Override
    public void init(@NotNull Inventory inventory) {
        this.container = new SimpleContainer(10);
        int lastSlotIndex = 0;
        for (int rowId = 0; rowId < 3; rowId ++) {
            for (int columnId = 0; columnId < 3; columnId++) {
                lastSlotIndex = columnId + (rowId * 3);
                int x = 6 + (columnId * 18);
                int y = 6 + (rowId * 18);
                this.addSlot(new Slot(this.container, lastSlotIndex, x, y));
            }
        }
        this.addSlot(new Slot(this.container, lastSlotIndex + 1, 99, 24));
    }
}
