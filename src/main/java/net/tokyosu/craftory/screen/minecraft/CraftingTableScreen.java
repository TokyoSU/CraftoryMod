package net.tokyosu.craftory.screen.minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.menu.minecraft.CraftingTableMenu;
import net.tokyosu.craftory.screen.minecraft.base.CraftingScreenBase;
import org.jetbrains.annotations.NotNull;

public class CraftingTableScreen extends CraftingScreenBase<CraftingTableMenu> {
    public CraftingTableScreen(@NotNull CraftingTableMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull ResourceLocation getGuiTexture() {
        return Constants.MINECRAFT_EDITOR_TEXTURE;
    }

    @Override
    public int getRowCount() {
        return this.menu.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return this.menu.getColumnCount();
    }

    @Override
    public @NotNull String getShapedCraftType() {
        return "minecraft:crafting_shaped";
    }
    
    @Override
    public @NotNull String getShapelessCraftType() {
        return "minecraft:crafting_shapeless";
    }

    @Override
    public int getCraftTier() {
        return 0;
    }

    @Override
    public int getMaxSlots() {
        return this.menu.getSlotCount(true);
    }
}
