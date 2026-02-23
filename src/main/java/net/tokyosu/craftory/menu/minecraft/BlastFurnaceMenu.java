package net.tokyosu.craftory.menu.minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.registry.MenuRegistry;
import org.jetbrains.annotations.NotNull;

public class BlastFurnaceMenu extends MenuContainerBase {
    public BlastFurnaceMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(MenuRegistry.BLAST_FURNACE_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    public BlastFurnaceMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(MenuRegistry.BLAST_FURNACE_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    @Override
    public void init(@NotNull Inventory inventory) {
        this.container = new SimpleContainer(2);
        this.addSlot(new Slot(this.container, 0, 6, 6));
        this.addSlot(new Slot(this.container, 1, 66, 24));
    }
}