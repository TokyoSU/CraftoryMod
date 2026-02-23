package net.tokyosu.craftory.menu.extendedcrafting;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.menu.base.MenuBase;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.registry.MenuRegistry;
import org.jetbrains.annotations.NotNull;

public class EpicTableMenu extends MenuBase {
    public EpicTableMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(MenuRegistry.EPIC_TABLE_MENU.get(), containerId, playerInventory);

    }

    public EpicTableMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(MenuRegistry.EPIC_TABLE_MENU.get(), containerId, playerInventory);

    }

    @Override
    public void init(@NotNull Inventory inventory) {

    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return false;
    }
}
