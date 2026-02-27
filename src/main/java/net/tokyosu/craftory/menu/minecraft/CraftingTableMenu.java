package net.tokyosu.craftory.menu.minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.menu.base.MenuCraftingBase;
import net.tokyosu.craftory.registry.MenuRegistry;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class CraftingTableMenu extends MenuCraftingBase {
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
        this.container = new SimpleContainer(this.getSlotCount(true));
        this.setCraftingGridSlots();
    }

    @Override
    public @NotNull Vector2i getSlotStartingPos() {
        return new Vector2i(6, 6);
    }

    @Override
    public @NotNull Vector2i getResultSlotPos() {
        return new Vector2i(99, 24);
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
}
