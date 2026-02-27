package net.tokyosu.craftory.screen.extendedcrafting;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.craftory.menu.extendedcrafting.BasicTableMenu;
import net.tokyosu.craftory.screen.extendedcrafting.base.ExCraftingScreenBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class BasicTableScreen extends ExCraftingScreenBase<BasicTableMenu> {
    public BasicTableScreen(@NotNull BasicTableMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull String getSavedFolderName() {
        return "basic";
    }

    @Override
    public int getCraftTier() {
        return 1;
    }

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(98, 63);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(98, -19);
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(165, 0, 125, 64);
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_extended_table");
    }
}
