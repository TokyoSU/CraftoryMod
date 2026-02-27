package net.tokyosu.craftory.plugins.jei.handler.minecraft;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.craftory.screen.minecraft.SmithingScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SmithingIngredientHandler implements IGhostIngredientHandler<SmithingScreen> {
    @Override
    public <I> @NotNull List<Target<I>> getTargetsTyped(@NotNull SmithingScreen smithingScreen, @NotNull ITypedIngredient<I> iTypedIngredient, boolean b) {
        List<Target<I>> targets = new ArrayList<>();
        targets.add(new SlotTarget<>(smithingScreen, 48,  14, 0));
        targets.add(new SlotTarget<>(smithingScreen, 66,  14, 1));
        targets.add(new SlotTarget<>(smithingScreen, 84,  14, 2));
        targets.add(new SlotTarget<>(smithingScreen, 138, 14, 3));
        return targets;
    }

    @Override
    public void onComplete() {}

    private record SlotTarget<I>(@NotNull SmithingScreen gui, int x, int y, int slotIndex) implements Target<I> {
        @Override
        public @NotNull Rect2i getArea() {
            return new Rect2i(gui.getGuiLeft() + x, gui.getGuiTop() + y, 16, 16);
        }

        @Override
        public void accept(@NotNull I ingredient) {
            // Handle the dropped ingredient
            if (ingredient instanceof ItemStack stack) {
                gui.setSlotIngredient(slotIndex, stack);
            }
        }
    }
}
