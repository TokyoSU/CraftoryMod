package net.tokyosu.craftory.screen.extendedcrafting;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.io.json.CraftingBuilder;
import net.tokyosu.craftory.menu.extendedcrafting.FluxCrafterMenu;
import net.tokyosu.craftory.screen.extendedcrafting.base.ExCraftingScreenBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class FluxCrafterScreen extends ExCraftingScreenBase<FluxCrafterMenu> {
    private EditBox energyRequiredBox;

    public FluxCrafterScreen(@NotNull FluxCrafterMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public void initializeAfter() {
        super.initializeAfter();
        // Cooking time (in ticks).
        this.energyRequiredBox = new EditBox(this.font, this.leftPos - 120, this.topPos + 22, 100, 20, Component.empty());
        this.energyRequiredBox.setFilter(text -> {
            if (text.isEmpty() || text.equals("-")) return true;
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        this.energyRequiredBox.setValue("1");
        this.addRenderableWidget(this.energyRequiredBox);
    }

    @Override
    public void renderFrontAfter(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderFrontAfter(graphics, partialTick, mouseX, mouseY);
        graphics.blit(Constants.ENERGY_ICON, this.energyRequiredBox.getX() - 22, this.energyRequiredBox.getY(), 0, 0, 14, 20, 14, 20);
        this.baseGUI.drawTooltip(this.energyRequiredBox.getX() - 22, this.energyRequiredBox.getY(), Component.translatable("rf.required"), mouseX, mouseY, 20);
    }

    @Override
    public @NotNull String getSavedFolderName() {
        return "flux_crafter";
    }

    @Override
    public int getCraftTier() {
        return 0;
    }

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {
        var builder = new CraftingBuilder();
        if (this.isShapeless())
            builder = builder.shapeless();
        builder.flux_crafting(this.getEnergyRequired()).folder(this.getSavedFolderName()).type("flux_crafter").type_prefix("extendedcrafting:").container(this.menu.container).width(this.menu.getColumnCount()).height(this.menu.getRowCount()).result(this.menu.getResultStack()).tier(this.getCraftTier()).export();
    }

    @Override
    public @NotNull Vector2i getSaveButtonPos() {
        return new Vector2i(100, 63);
    }

    @Override
    public @NotNull Vector2i getTrashButtonPos() {
        return new Vector2i(100, -19);
    }

    @Override
    public @NotNull Rect2i getWindowRect() {
        return new Rect2i(197, 154, 126, 64);
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_extended_table");
    }

    public int getEnergyRequired() {
        try {
            return Integer.parseInt(this.energyRequiredBox.getValue());
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
