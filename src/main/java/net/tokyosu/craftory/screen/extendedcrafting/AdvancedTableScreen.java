package net.tokyosu.craftory.screen.extendedcrafting;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.craftory.menu.extendedcrafting.AdvancedTableMenu;
import net.tokyosu.craftory.screen.extendedcrafting.base.ExCraftingScreenBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class AdvancedTableScreen extends ExCraftingScreenBase<AdvancedTableMenu> {
    public AdvancedTableScreen(@NotNull AdvancedTableMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull String getSavedFolderName() {
        return "advanced";
    }

    @Override
    public int getCraftTier() {
        return 2;
    }

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(130, 80);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(130, 0);
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(0, 0, 157, 100);
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_extended_table");
    }
}
