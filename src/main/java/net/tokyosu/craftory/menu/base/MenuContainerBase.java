package net.tokyosu.craftory.menu.base;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.menu.base.MenuBase;
import org.jetbrains.annotations.NotNull;

public abstract class MenuContainerBase extends MenuBase {
    public SimpleContainer container;

    protected MenuContainerBase(@NotNull MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    protected MenuContainerBase(@NotNull MenuType<?> menuType, int containerId, @NotNull Inventory playerInventory) {
        super(menuType, containerId, playerInventory);
    }

    @Override
    public void init(@NotNull Inventory inventory) {}

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
