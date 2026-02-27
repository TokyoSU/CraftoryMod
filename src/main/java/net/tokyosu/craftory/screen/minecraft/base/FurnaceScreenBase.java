package net.tokyosu.craftory.screen.minecraft.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public abstract class FurnaceScreenBase<T extends MenuContainerBase> extends EditorBase<T> {
    private EditBox experienceBox;
    private EditBox cookingBox;

    public FurnaceScreenBase(@NotNull T menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    public abstract @NotNull String getDefaultCookTime();

    @Override
    public boolean isCraftingHelpShown() {
        return false;
    }

    @Override
    public @NotNull Component getCraftingHelpText() {
        return Component.empty();
    }

    @Override
    public void initializeAfter() {
        // Experience count.
        this.experienceBox = new EditBox(this.font, this.leftPos - 120, this.topPos, 100, 20, Component.empty());
        this.experienceBox.setFilter(text -> {
            if (text.isEmpty() || text.equals("-") || text.equals(".") || text.equals("-.")) return true;
            try {
                double value = Double.parseDouble(text);
                return value >= 0.0 && value <= 1000000.0; // Range validation
            } catch (NumberFormatException e) {
                return false;
            }
        });
        this.experienceBox.setValue("0.0");
        this.addRenderableWidget(this.experienceBox);

        // Cooking time (in ticks).
        this.cookingBox = new EditBox(this.font, this.leftPos - 120, this.topPos + 22, 100, 20, Component.empty());
        this.cookingBox.setFilter(text -> {
            if (text.isEmpty() || text.equals("-")) return true;
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        this.cookingBox.setValue(this.getDefaultCookTime());
        this.addRenderableWidget(this.cookingBox);
    }

    @Override
    public int getMaxSlots() {
        return 2;
    }

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(69, 63);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(69, -19);
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_not_defined");
    }

    @Override
    public @NotNull Component getTrashButtonTooltipText() {
        return Component.translatable("button.trash_cook");
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(162, 18, 92, 64);
    }

    @Override
    public void renderFrontAfter(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        var posX = this.baseGUI.getPosX();
        var posY = this.baseGUI.getPosY();
        var missingInput = this.noItemInSlot(0);
        var missingOutput = this.noItemInSlot(1);

        if (missingInput) {
            this.baseGUI.drawTooltip(posX + 6, posY + 6, Component.translatable("slot.input.name"), mouseX, mouseY);
        }
        if (missingOutput) {
            this.baseGUI.drawTooltip(posX + 66, posY + 24, Component.translatable("slot.output.name"), mouseX, mouseY);
        }

        int cookingTime = this.getCookingTime();
        if (cookingTime > 0) {
            this.baseGUI.drawTexture(7, 26, Constants.VALID_BURN_TEXTURE);
        } else if (cookingTime < 0) {
            this.baseGUI.drawTexture(7, 26, Constants.INVALID_BURN_TEXTURE);
        }

        // Require all slots !
        if (missingInput || missingOutput) {
            this.baseGUI.drawTexture(30, 24, Constants.INVALID_CRAFT_TEXTURE);
        }

        // Draw experience and burn icon:
        graphics.blit(Constants.EXPERIENCE_ORB_ICON, this.experienceBox.getX() - 22, this.experienceBox.getY(), 0, 0, 20, 20, 20, 20);
        graphics.blit(Constants.BURN_TIME_ICON, this.cookingBox.getX() - 22, this.cookingBox.getY(), 0, 0, 20, 20, 20, 20);
        this.baseGUI.drawTooltip(this.experienceBox.getX() - 22, this.experienceBox.getY(), Component.translatable("cook.experience_gain"), mouseX, mouseY, 20);
        this.baseGUI.drawTooltip(this.cookingBox.getX() - 22, this.cookingBox.getY(), Component.translatable("cook.burn_time"), mouseX, mouseY, 20);
    }

    @Override
    public void onSlotClicked(@NotNull Slot slot, int slotIndex, int mouseType, @NotNull ClickType clickType) {
        super.onSlotClicked(slot, slotIndex, mouseType, clickType);
    }

    @Override
    public void onTrashButton(@NotNull HoverButton hoverButton) {
        this.removeItemInSlot(0);
        this.removeItemInSlot(1);
    }

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {}

    public int getCookingTime() {
        try {
            return Integer.parseInt(cookingBox.getValue());
        } catch (NumberFormatException e) {
            try {
                return Integer.parseInt(this.getDefaultCookTime()); // default
            }
            catch (NumberFormatException b) {
                return 200; // In case default cook time is not valid, shouldn't happen !
            }
        }
    }

    public double getExperience() {
        try {
            return Double.parseDouble(experienceBox.getValue());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
