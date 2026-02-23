package net.tokyosu.craftory.plugins.jei.handler.base;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.tokyosu.craftory.menu.base.MenuContainerBase;
import net.tokyosu.craftory.plugins.jei.handler.slot.FurnaceSlotTarget;
import net.tokyosu.craftory.screen.minecraft.base.ScreenContainerBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FurnaceIngredientHandlerBase<T extends ScreenContainerBase<S>, S extends MenuContainerBase> implements IGhostIngredientHandler<T> {
    @Override
    public <I> @NotNull List<Target<I>> getTargetsTyped(@NotNull T blastFurnace, @NotNull ITypedIngredient<I> iTypedIngredient, boolean b) {
        List<Target<I>> targets = new ArrayList<>();
        targets.add(new FurnaceSlotTarget<>(blastFurnace, 6, 6, 0));
        targets.add(new FurnaceSlotTarget<>(blastFurnace, 66, 24, 1));
        return targets;
    }

    @Override
    public void onComplete() {}
}
