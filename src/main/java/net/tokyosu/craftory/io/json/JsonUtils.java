package net.tokyosu.craftory.io.json;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JsonUtils {
    // Get recipe folder from recipe type name
    public static @NotNull String getRecipeFolder(@NotNull String recipe_type) {
        // Extract the path from the recipe type (e.g., "minecraft:crafting_shaped" -> "crafting")
        String[] parts = recipe_type.split(":");
        String recipePath = parts.length > 1 ? parts[1] : parts[0];

        // Remove _shaped or _shapeless suffix if present
        recipePath = recipePath.replace("_shaped", "").replace("_shapeless", "");

        return recipePath.isEmpty() ? "crafting" : recipePath;
    }

    // Helper to trim empty rows/columns from pattern
    @SuppressWarnings("unused")
    public static @NotNull String[] trimPattern(@NotNull String[] pattern, int originalWidth, int originalHeight) {
        // Find bounds
        int minRow = originalHeight, maxRow = -1, minCol = originalWidth, maxCol = -1;

        for (int row = 0; row < originalHeight; row++) {
            for (int col = 0; col < originalWidth; col++) {
                if (col < pattern[row].length() && pattern[row].charAt(col) != ' ') {
                    minRow = Math.min(minRow, row);
                    maxRow = Math.max(maxRow, row);
                    minCol = Math.min(minCol, col);
                    maxCol = Math.max(maxCol, col);
                }
            }
        }

        // Empty pattern - return single space
        if (maxRow == -1) {
            return new String[]{" "};
        }

        // Trim
        String[] trimmed = new String[maxRow - minRow + 1];
        for (int i = 0; i < trimmed.length; i++) {
            int rowIndex = minRow + i;
            if (rowIndex < pattern.length && pattern[rowIndex].length() > minCol) {
                int endCol = Math.min(maxCol + 1, pattern[rowIndex].length());
                trimmed[i] = pattern[rowIndex].substring(minCol, endCol);
            } else {
                trimmed[i] = " ";
            }
        }

        return trimmed;
    }

    public static @NotNull JsonObject createIngredientObject(@NotNull ItemStack stack, @NotNull ResourceLocation location) {
        return createIngredientObject(stack, location, 0);
    }

    public static @NotNull JsonObject createIngredientObject(@NotNull ItemStack stack, @NotNull ResourceLocation location, int resultCount) {
        var ingredientObject = new JsonObject();
        if (stack.hasTag()) {
            ingredientObject.addProperty("type", "forge:nbt");
        }
        ingredientObject.addProperty("item", location.toString());
        if (resultCount > 0) { // Avoid adding count if the item is not inside result, craft or smelting don't support stack crafting...
            ingredientObject.addProperty("count", resultCount);
        }
        if (stack.hasTag()) {
            ingredientObject.addProperty("nbt", stack.getTag().toString());
        }
        return ingredientObject;
    }
}
