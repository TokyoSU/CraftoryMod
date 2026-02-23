package net.tokyosu.craftory.screen.minecraft.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.json.RecipeExporter;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public abstract class CraftingScreenBase<T extends MenuContainerBase> extends EditorBase<T> {
    private Checkbox enableShapeless;

    public CraftingScreenBase(@NotNull T menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    public abstract int getRowCount();

    public abstract int getColumnCount();

    public abstract @NotNull String getShapedCraftType();

    public abstract @NotNull String getShapelessCraftType();

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(101, 63);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(101, -19);
    }

    @Override
    public void initializeAfter() {
        this.enableShapeless = new Checkbox(this.leftPos, this.topPos - 40, 20, 20, Component.translatable("checkbox.enable_shapeless"), false);
        this.addRenderableWidget(this.enableShapeless);
    }

    @Override
    public void onTrashButton(@NotNull HoverButton hoverButton) {
        for (int i = 0; i < this.getMaxSlots(); i++) {
            this.removeItemInSlot(i);
        }
        if (this.enableShapeless.selected()) {
            this.enableShapeless.onPress();
        }
    }

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {
        RecipeExporter.exportCrafting(this.menu.container, this.menu.container.getItem(this.getMaxSlots() - 1), !this.isShapeless(), this.getRowCount(), this.getColumnCount(), this.isShapeless() ? this.getShapelessCraftType() : this.getShapedCraftType());
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_craftingtable");
    }

    @Override
    public @NotNull Component getTrashButtonTooltipText() {
        return Component.translatable("button.trash");
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(1, 42, 125, 64);
    }

    @Override
    public void renderFrontAfter(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {

    }

    @Override
    public int getMaxSlots() {
        return 10;
    }

    public boolean isShapeless() {
        return this.enableShapeless.selected();
    }
}
