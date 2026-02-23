package net.tokyosu.craftory.screen.minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.json.RecipeExporter;
import net.tokyosu.craftory.menu.minecraft.FurnaceMenu;
import net.tokyosu.craftory.screen.minecraft.base.FurnaceScreenBase;
import org.jetbrains.annotations.NotNull;

public class FurnaceScreen extends FurnaceScreenBase<FurnaceMenu> {
    public FurnaceScreen(@NotNull FurnaceMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull String getDefaultCookTime() {
        return "200";
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_furnace");
    }

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {
        RecipeExporter.exportFurnace(this.getItemInSlot(0), this.getItemInSlot(1), this.getExperience(), this.getCookingTime(), "minecraft:smelting");
    }
}
