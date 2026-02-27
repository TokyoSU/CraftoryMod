package net.tokyosu.craftory.screen.extendedcrafting;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.craftory.menu.extendedcrafting.EliteTableMenu;
import net.tokyosu.craftory.screen.extendedcrafting.base.ExCraftingScreenBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class EliteTableScreen extends ExCraftingScreenBase<EliteTableMenu> {
    public EliteTableScreen(@NotNull EliteTableMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull String getSavedFolderName() {
        return "elite";
    }

    @Override
    public int getCraftTier() {
        return 3;
    }

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(169, 116);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(169, 0);
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(0, 107, 196, 136);
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_extended_table");
    }
}
