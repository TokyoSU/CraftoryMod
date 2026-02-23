package net.tokyosu.craftory.menu.minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.registry.MenuRegistry;
import org.jetbrains.annotations.NotNull;

public class SmithingMenu extends MenuContainerBase {
    public SmithingMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(MenuRegistry.SMITHING_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    public SmithingMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(MenuRegistry.SMITHING_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    @Override
    public void init(@NotNull Inventory inventory) {
        this.container = new SimpleContainer(4);
        this.addSlot(new Slot(this.container, 0, 48, 14));
        this.addSlot(new Slot(this.container, 1, 66, 14));
        this.addSlot(new Slot(this.container, 2, 84, 14));
        this.addSlot(new Slot(this.container, 3, 138, 14)); // Result slot.
    }
}
