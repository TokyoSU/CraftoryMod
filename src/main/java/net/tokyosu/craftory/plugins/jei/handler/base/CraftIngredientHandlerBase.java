package net.tokyosu.craftory.plugins.jei.handler.base;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.tokyosu.craftory.menu.base.MenuCraftingBase;
import net.tokyosu.craftory.plugins.jei.handler.slot.ISlotTarget;
import net.tokyosu.craftory.screen.minecraft.base.ScreenContainerBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CraftIngredientHandlerBase<T extends ScreenContainerBase<S>, S extends MenuCraftingBase> implements IGhostIngredientHandler<T> {
    @Override
    public <I> @NotNull List<Target<I>> getTargetsTyped(@NotNull T craftingTableScreen, @NotNull ITypedIngredient<I> iTypedIngredient, boolean b) {
        var menu = craftingTableScreen.getMenu();
        var startPos = menu.getSlotStartingPos();
        var resultPos = menu.getResultSlotPos();
        int rowCount = menu.getRowCount();
        int columnCount = menu.getColumnCount();

        List<Target<I>> slots = new ArrayList<>();
        int slotIndex = 0;
        for (int rowId = 0; rowId < rowCount; rowId ++) {
            for (int columnId = 0; columnId < columnCount; columnId++) {
                slotIndex = columnId + (rowId * rowCount);
                int x = startPos.x + (columnId * 18);
                int y = startPos.y + (rowId * 18);
                slots.add(new ISlotTarget<>(craftingTableScreen, x, y, slotIndex));
            }
        }
        slots.add(new ISlotTarget<>(craftingTableScreen, resultPos.x, resultPos.y, slotIndex + 1));
        return slots;
    }

    @Override
    public void onComplete() {}
}
