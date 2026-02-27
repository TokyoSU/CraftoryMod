package net.tokyosu.craftory.menu.extendedcrafting;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.registry.MenuRegistry;
import org.jetbrains.annotations.NotNull;

public class CompressorMenu extends MenuContainerBase {
    public CompressorMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        super(MenuRegistry.COMPRESSOR_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }

    public CompressorMenu(int containerId, @NotNull Inventory playerInventory, @NotNull FriendlyByteBuf ignoredFriendlyByteBuf) {
        super(MenuRegistry.COMPRESSOR_MENU.get(), containerId, playerInventory);
        this.init(playerInventory);
    }
}
