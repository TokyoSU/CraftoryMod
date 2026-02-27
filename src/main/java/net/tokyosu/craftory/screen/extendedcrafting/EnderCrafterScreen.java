package net.tokyosu.craftory.screen.extendedcrafting;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.tokyosu.apocalypselib.menu.button.HoverButton;
import net.tokyosu.craftory.io.json.CraftingBuilder;
import net.tokyosu.craftory.menu.extendedcrafting.EnderCrafterMenu;
import net.tokyosu.craftory.screen.extendedcrafting.base.ExCraftingScreenBase;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class EnderCrafterScreen extends ExCraftingScreenBase<EnderCrafterMenu> {
    public EnderCrafterScreen(@NotNull EnderCrafterMenu menu, @NotNull Inventory playerInv, @NotNull Component menuName) {
        super(menu, playerInv, menuName);
    }

    @Override
    public @NotNull String getSavedFolderName() {
        return "ender_crafter";
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
        builder.ender_crafting().folder(this.getSavedFolderName()).type("ender_crafter").type_prefix("extendedcrafting:").container(this.menu.container).width(this.menu.getColumnCount()).height(this.menu.getRowCount()).result(this.menu.getResultStack()).tier(this.getCraftTier()).export();
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
        return new Rect2i(197, 89, 126, 64);
    }

    @Override
    public @NotNull Component getSaveButtonTooltipText() {
        return Component.translatable("button.save_to_json_extended_table");
    }
}
