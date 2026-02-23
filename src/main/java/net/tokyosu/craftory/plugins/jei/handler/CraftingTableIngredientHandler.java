package net.tokyosu.craftory.plugins.jei.handler;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.craftory.screen.minecraft.CraftingTableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CraftingTableIngredientHandler implements IGhostIngredientHandler<CraftingTableScreen> {
    @Override
    public <I> @NotNull List<Target<I>> getTargetsTyped(@NotNull CraftingTableScreen craftingTableScreen, @NotNull ITypedIngredient<I> iTypedIngredient, boolean b) {
        List<Target<I>> slots = new ArrayList<>();
        int slotIndex = 0;
        for (int rowId = 0; rowId < 3; rowId ++) {
            for (int columnId = 0; columnId < 3; columnId++) {
                slotIndex = columnId + (rowId * 3);
                int x = 6 + (columnId * 18);
                int y = 6 + (rowId * 18);
                slots.add(new SlotTarget<>(craftingTableScreen, x, y, slotIndex));
            }
        }
        slots.add(new SlotTarget<>(craftingTableScreen, 99, 24, slotIndex + 1));
        return slots;
    }

    @Override
    public void onComplete() {}

    private record SlotTarget<I>(@NotNull CraftingTableScreen gui, int x, int y, int slotIndex) implements Target<I> {
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
