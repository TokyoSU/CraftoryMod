package net.tokyosu.craftory.io.json;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CraftingExporter {
    public static void export(@NotNull SimpleContainer container, @NotNull ItemStack result, boolean shaped, int width, int height, @NotNull String recipe_type, int tier) {
        export(container, result, shaped, width, height, recipe_type, "crafting", tier);
    }

    public static void export(@NotNull SimpleContainer container, @NotNull ItemStack result, boolean shaped, int width, int height, @NotNull String recipe_type, @NotNull String recipe_folder, int tier) {
        var builder = new CraftingBuilder();
        if (!shaped)
            builder = builder.shapeless();
        if (width > 3 && height > 3)
            builder = builder.extended_crafting();
        builder.container(container).folder(recipe_folder).width(width).height(height).tier(tier).result(result).type(recipe_type).export();
    }
}
