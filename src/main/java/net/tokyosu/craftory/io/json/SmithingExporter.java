package net.tokyosu.craftory.io.json;

import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import org.jetbrains.annotations.NotNull;

public class SmithingExporter {
    public static void export(@NotNull ItemStack template, @NotNull ItemStack base, @NotNull ItemStack addition, @NotNull ItemStack output) {
        if (template == ItemStack.EMPTY || base == ItemStack.EMPTY || addition == ItemStack.EMPTY || output == ItemStack.EMPTY) {
            JsonReporter.reportError(Component.literal("Failed to export smithing recipe: template, base, addition and result should not be empty !"));
            return;
        }

        // Now get resources and if null return.
        var templateResource = ResourceUtils.getResourcebyItem(template.getItem());
        if (templateResource == null) {
            JsonReporter.reportError(Component.literal("Failed to export smithing recipe, failed to get template ResourceLocation, returned null !"));
            return;
        }
        var baseResource = ResourceUtils.getResourcebyItem(base.getItem());
        if (baseResource == null) {
            JsonReporter.reportError(Component.literal("Failed to export smithing recipe, failed to get base ResourceLocation, returned null !"));
            return;
        }
        var outputResource = ResourceUtils.getResourcebyItem(output.getItem());
        if (outputResource == null) {
            JsonReporter.reportError(Component.literal("Failed to export smithing recipe, failed to get output ResourceLocation, returned null !"));
            return;
        }
        var additionResource = ResourceUtils.getResourcebyItem(addition.getItem());
        if (additionResource == null) {
            JsonReporter.reportError(Component.literal("Failed to export smithing recipe, failed to get addition ResourceLocation, returned null !"));
            return;
        }

        // Now create the recipe:
        var recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:smithing_transform");
        recipe.add("template", JsonUtils.createIngredientObject(template, templateResource));
        recipe.add("base", JsonUtils.createIngredientObject(base, baseResource));
        recipe.add("addition", JsonUtils.createIngredientObject(addition, additionResource));
        recipe.add("result", JsonUtils.createIngredientObject(output, outputResource, 1)); // False because the result can hold more than 1 object.

        new JsonSaverBuilder().data(recipe).stack(output).recipe_folder("smithing").suffix("transform").build();
    }
}
