package net.tokyosu.craftory.screen.minecraft;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.io.json.SmithingExporter;
import net.tokyosu.craftory.menu.minecraft.SmithingMenu;
import net.tokyosu.craftory.screen.minecraft.base.EditorBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class SmithingScreen extends EditorBase<SmithingMenu> {
    public SmithingScreen(@NotNull SmithingMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull ResourceLocation getGuiTexture() {
        return Constants.MINECRAFT_EDITOR_TEXTURE;
    }

    @Override
    public boolean isCraftingHelpShown() {
        return false;
    }

    @Override
    public @NotNull Component getCraftingHelpText() {
        return Component.empty();
    }

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(137, 40);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(137, -19);
    }

    @Override
    public void initializeAfter() {

    }

    @Override
    public void onTrashButton(@NotNull HoverButton hoverButton) {
        this.removeItemInSlot(0);
        this.removeItemInSlot(1);
        this.removeItemInSlot(2);
    }

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {
        SmithingExporter.export(this.getItemInSlot(0), this.getItemInSlot(1), this.getItemInSlot(2), this.getItemInSlot(3));
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_smithing");
    }

    @Override
    public @NotNull Component getTrashButtonTooltipText() {
        return Component.translatable("button.trash");
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(0, 0, 160, 41);
    }

    @Override
    public void renderFrontAfter(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int posX = this.baseGUI.getPosX();
        int posY = this.baseGUI.getPosY();

        // Show tooltip for slot type.
        var missingTemplate = this.noItemInSlot(0);
        var missingBase = this.noItemInSlot(1);
        var missingResult = this.noItemInSlot(3);

        if (missingTemplate) {
            this.baseGUI.drawTooltip(posX + 48, posY + 14, Component.translatable("slot.template.name"), mouseX, mouseY);
        }
        if (missingBase) {
            this.baseGUI.drawTooltip(posX + 66, posY + 14, Component.translatable("slot.base.name"), mouseX, mouseY);
        }
        if (this.noItemInSlot(2)) {
            this.baseGUI.drawTooltip(posX + 84, posY + 14, Component.translatable("slot.addition.name"), mouseX, mouseY);
        }
        if (missingResult) {
            this.baseGUI.drawTooltip(posX + 138, posY + 14, Component.translatable("slot.output.name"), mouseX, mouseY);
        }
        // If recipe is invalid.
        if (missingTemplate || missingBase || missingResult) {
            this.baseGUI.drawTexture(108, 15, Constants.INVALID_CRAFT_TEXTURE);
        }
    }

    @Override
    public int getMaxSlots() {
        return 3;
    }
}
