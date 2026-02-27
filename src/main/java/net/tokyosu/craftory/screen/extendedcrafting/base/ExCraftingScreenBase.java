package net.tokyosu.craftory.screen.extendedcrafting.base;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.io.json.CraftingExporter;
import net.tokyosu.craftory.menu.base.MenuCraftingBase;
import net.tokyosu.craftory.screen.minecraft.base.CraftingScreenBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public abstract class ExCraftingScreenBase<T extends MenuCraftingBase> extends CraftingScreenBase<T> {
    public ExCraftingScreenBase(@NotNull T menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull Vector2i getGuiTextureSize() {
        return new Vector2i(1024, 1024);
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
        return "extendedcrafting:shaped_table";
    }

    @Override
    public @NotNull String getShapelessCraftType() {
        return "extendedcrafting:shapeless_table";
    }

    @Override
    public @NotNull ResourceLocation getGuiTexture() {
        return Constants.EXTENDEDCRAFTING_EDITOR_TEXTURE;
    }

    @Override
    public int getMaxSlots() {
        return this.menu.getSlotCount(true);
    }

    public abstract @NotNull String getSavedFolderName();

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {
        CraftingExporter.export(this.menu.container, this.menu.getResultStack(), !this.isShapeless(), this.getColumnCount(), this.getRowCount(), this.isShapeless() ? this.getShapelessCraftType() : this.getShapedCraftType(), this.getSavedFolderName(), this.getCraftTier());
    }
}
