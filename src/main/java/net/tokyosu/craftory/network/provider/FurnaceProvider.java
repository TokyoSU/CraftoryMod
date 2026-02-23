package net.tokyosu.craftory.network.provider;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.tokyosu.craftory.menu.minecraft.FurnaceMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FurnaceProvider implements MenuProvider {
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("menu.furnace_editor.name");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new FurnaceMenu(containerId, inventory, player);
    }
}
