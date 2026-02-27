package net.tokyosu.craftory.plugins.jei.handler.slot;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.screen.minecraft.base.ScreenContainerBase;
import org.jetbrains.annotations.NotNull;

public record ISlotTarget<T extends ScreenContainerBase<S>, S extends MenuContainerBase, C>(@NotNull T gui, int x, int y, int slotIndex) implements IGhostIngredientHandler.Target<C> {
    @Override
    public @NotNull Rect2i getArea() {
        return new Rect2i(gui.getGuiLeft() + x, gui.getGuiTop() + y, 16, 16);
    }

    @Override
    public void accept(@NotNull C ingredient) {
        // Handle the dropped ingredient
        if (ingredient instanceof ItemStack stack) {
            gui.setSlotIngredient(slotIndex, stack);
        }
    }
}
