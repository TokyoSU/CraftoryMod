package net.tokyosu.craftory.screen.minecraft;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.Constants;
import net.tokyosu.craftory.io.json.FurnaceExporter;
import net.tokyosu.craftory.menu.minecraft.SmokerMenu;
import net.tokyosu.craftory.screen.minecraft.base.FurnaceScreenBase;
import org.jetbrains.annotations.NotNull;

public class SmokerScreen extends FurnaceScreenBase<SmokerMenu> {
    public SmokerScreen(@NotNull SmokerMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull ResourceLocation getGuiTexture() {
        return Constants.MINECRAFT_EDITOR_TEXTURE;
    }

    @Override
    public @NotNull String getDefaultCookTime() {
        return "50";
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_smoker");
    }

    @Override
    public void onSaveButton(@NotNull HoverButton hoverButton) {
        FurnaceExporter.export(this.getItemInSlot(0), this.getItemInSlot(1), this.getExperience(), this.getCookingTime(), "minecraft:smoking");
    }
}
