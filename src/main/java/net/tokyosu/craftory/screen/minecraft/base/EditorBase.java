package net.tokyosu.craftory.screen.minecraft.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.builder.InventoryBuilder;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

public abstract class EditorBase<T extends MenuContainerBase> extends ScreenContainerBase<T> {
    protected final InventoryBuilder baseGUI;
    private HoverButton saveButton;
    private HoverButton trashButton;

    public abstract @NotNull ResourceLocation getGuiTexture();

    public @NotNull Vector2i getGuiTextureSize() {
        return new Vector2i(256, 256);
    }

    public EditorBase(@NotNull T menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
        var rect = this.getWindowRect();
        var size = this.getGuiTextureSize();
        this.baseGUI = new InventoryBuilder(this.getGuiTexture(), rect.getWidth(), rect.getHeight(), size.x, size.y);
    }

    public abstract @NotNull Vector2i getSaveButtonPos();

    public abstract @NotNull Vector2i getTrashButtonPos();

    public abstract void initializeAfter();

    public abstract boolean isCraftingHelpShown();

    public abstract @NotNull Component getCraftingHelpText();

    @Override
    public void initialize() {
        this.baseGUI.setFont(this.font);
        this.baseGUI.init(this.width, this.height);
        this.leftPos = this.baseGUI.getPosX();
        this.topPos = this.baseGUI.getPosY();
        var saveRect = this.getSaveButtonPos();
        var trashRect = this.getTrashButtonPos();
        this.saveButton = this.baseGUI.createHoveredButton(saveRect.x, saveRect.y, true).sizeGui(40, 20).texture(Constants.SAVE_BUTTON).size(20, 20).bounds(new Rect2i(0, 0, 20, 20), new Rect2i(20, 0, 20, 20)).onHoverPress(this::onSaveButton).build();
        this.trashButton = this.baseGUI.createHoveredButton(trashRect.x, trashRect.y, true).sizeGui(40, 20).texture(Constants.TRASH_BUTTON).size(20, 20).bounds(new Rect2i(0, 0, 20, 20), new Rect2i(20, 0, 20, 20)).onHoverPress(this::onTrashButton).build();
        this.initializeAfter();
    }

    public abstract void onTrashButton(@NotNull HoverButton hoverButton);

    public abstract void onSaveButton(@NotNull HoverButton hoverButton);

    public abstract @NotNull Component getSaveButtonTooltipText();

    public abstract @NotNull Component getTrashButtonTooltipText();

    public abstract @NotNull Rect2i getWindowRect();

    public abstract void renderFrontAfter(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY);

    public abstract int getMaxSlots();

    @Override
    public void renderFront(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        this.saveButton.render(graphics, mouseX, mouseY, partialTick);
        this.trashButton.render(graphics, mouseX, mouseY, partialTick);

        this.baseGUI.drawTooltip(this.saveButton.getX(), this.saveButton.getY(), this.getSaveButtonTooltipText(), mouseX, mouseY, 19);
        this.baseGUI.drawTooltip(this.trashButton.getX(), this.trashButton.getY(), this.getTrashButtonTooltipText(), mouseX, mouseY, 19);

        if (this.isCraftingHelpShown()) {
            this.baseGUI.drawString(0, this.getWindowRect().getHeight() + 30, this.getCraftingHelpText(), 0xFFFFFF);
        }

        this.renderFrontAfter(graphics, partialTick, mouseX, mouseY);

        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public void renderBack(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        var rect = this.getWindowRect();
        this.baseGUI.setGraphics(graphics);
        this.baseGUI.drawBackground(0, 0, rect.getX(), rect.getY());
    }

    @Override
    public void onSlotClicked(@NotNull Slot slot, int slotIndex, int mouseType, @NotNull ClickType clickType) {
        if (mouseType == 0 && Screen.hasControlDown()) { // Last slot can be placed with CTRL + left click.
            this.setSlotIngredient(slotIndex, this.getLastPlacedStack());
        }
        if (mouseType == 1) { // When item is right-clicked, remove it !
            this.removeItemInSlot(slotIndex);
        }
    }

    @Override
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean onMouseClicked(double mouseX, double mouseY, int mouseType) {
        return this.saveButton.mouseClicked(mouseX, mouseY, mouseType) || this.trashButton.mouseClicked(mouseX, mouseY, mouseType);
    }

    @Override
    public void setSlotIngredient(int slotIndex, @NotNull ItemStack ingredient) {
        if (slotIndex < 0 || slotIndex > this.getMaxSlots()) return;
        this.menu.container.setItem(slotIndex, ingredient);
        this.setLastPlacedStack(ingredient);
    }
}
