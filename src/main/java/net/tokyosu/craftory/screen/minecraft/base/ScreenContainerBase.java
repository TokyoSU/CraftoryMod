package net.tokyosu.craftory.screen.minecraft.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.craftory.io.json.JsonReporter;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ScreenContainerBase<T extends MenuContainerBase> extends AbstractContainerScreen<T> {
    private ItemStack lastPlacedStack;

    public ScreenContainerBase(@NotNull T menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
        JsonReporter.setPlayer(playerInv.player);
        this.inventoryLabelX = 8000;
        this.inventoryLabelY = 8000;
        this.titleLabelX = 8000;
        this.titleLabelY = 8000;
    }

    public abstract void initialize();

    public abstract void renderFront(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY);

    public abstract void renderBack(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY);

    public abstract void onSlotClicked(@NotNull Slot slot, int slotIndex, int mouseType, @NotNull ClickType clickType);

    public abstract boolean onMouseClicked(double mouseX, double mouseY, int mouseType);

    public abstract boolean onKeyPressed(int keyCode, int scanCode, int modifiers);

    @Override
    protected void init() {
        this.initialize();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(graphics);
        this.renderBack(graphics, partialTick, mouseX, mouseY);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderFront(graphics, partialTick, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseType) {
        if (this.onMouseClicked(mouseX, mouseY, mouseType))
            return true;
        return super.mouseClicked(mouseX, mouseY, mouseType);
    }

    @SuppressWarnings({"DataFlowIssue", "ConstantValue"})
    @Override
    protected void slotClicked(@Nullable Slot slot, int slotIndex, int mouseType, @NotNull ClickType clickType) { // Slot can be null if you click outside of one...
        super.slotClicked(slot, slotIndex, mouseType, clickType);
        if (slot != null) {
            this.onSlotClicked(slot, slotIndex, mouseType, clickType);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.onKeyPressed(keyCode, scanCode, modifiers)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public @NotNull ItemStack getItemInSlot(int slotIndex) {
        return this.menu.container == null ? ItemStack.EMPTY : this.menu.container.getItem(slotIndex);
    }

    public @NotNull ItemStack getLastPlacedStack() {
        return this.lastPlacedStack != null ? this.lastPlacedStack : ItemStack.EMPTY;
    }

    public boolean noItemInSlot(int slotIndex) {
        return this.menu.container == null || this.menu.container.getItem(slotIndex) == ItemStack.EMPTY;
    }

    public void removeItemInSlot(int slotIndex) {
        this.menu.container.setItem(slotIndex, ItemStack.EMPTY);
    }

    public void setLastPlacedStack(@NotNull ItemStack stack) {
        this.lastPlacedStack = stack;
    }

    /**
     * Set the slot ingredient from JEI, REI etc...
     * @param slotIndex A slot index.
     * @param ingredient A valid ItemStack ingredient.
     */
    public abstract void setSlotIngredient(int slotIndex, @NotNull ItemStack ingredient);
}
