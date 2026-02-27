package net.tokyosu.craftory.io.json;

import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import org.jetbrains.annotations.NotNull;

public class FurnaceExporter {
    public static void export(@NotNull ItemStack input, @NotNull ItemStack output, double experience, int cookingTime, @NotNull String recipeType) {
        if (input == ItemStack.EMPTY || output == ItemStack.EMPTY) {
            JsonReporter.reportError(Component.literal("Failed to export " + recipeType + " recipe: input, output should not be empty !"));
            return;
        }

        // Now get resources and if null return.
        var inputResource = ResourceUtils.getResourcebyItem(input.getItem());
        if (inputResource == null) {
            JsonReporter.reportError(Component.literal("Failed to export" + recipeType + " recipe, failed to get input ResourceLocation, returned null !"));
            return;
        }
        var outputResource = ResourceUtils.getResourcebyItem(output.getItem());
        if (outputResource == null) {
            JsonReporter.reportError(Component.literal("Failed to export " + recipeType + " recipe, failed to get output ResourceLocation, returned null !"));
            return;
        }

        // Avoid negative experience !
        if (experience < 0.0) {
            JsonReporter.reportWarning(Component.translatable("recipe_exporter.experience_failure"));
            experience = 0.0;
        }

        var recipe = new JsonObject();
        recipe.addProperty("type", recipeType);
        recipe.add("ingredient", JsonUtils.createIngredientObject(input, inputResource));
        recipe.addProperty("result", outputResource.toString());
        recipe.addProperty("experience", experience);
        recipe.addProperty("cookingtime", cookingTime);

        new JsonSaverBuilder().data(recipe).stack(output).recipe_folder(recipeType.replace("minecraft:", "")).suffix("shapeless").build();
    }
}
