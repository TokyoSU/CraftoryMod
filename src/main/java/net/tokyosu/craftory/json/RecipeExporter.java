package net.tokyosu.craftory.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import net.tokyosu.craftory.Craftory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class RecipeExporter {
    private static Player PLAYER;

    public static void setPlayer(@NotNull Player player) {
        PLAYER = player;
    }

    /// Export a crafting table recipe, support any grid size !
    public static void exportCrafting(@NotNull SimpleContainer container, @NotNull ItemStack result, boolean shaped, int gridWidth, int gridHeight, @NotNull String recipeTypeName) {
        if (result == ItemStack.EMPTY) {
            reportError("Failed to export crafting recipe: result should not be empty!");
            return;
        }

        var resultResource = ResourceUtils.getResourcebyItem(result.getItem());
        if (resultResource == null) {
            reportError("Failed to export crafting recipe, failed to get result ResourceLocation!");
            return;
        }

        if (shaped) {
            exportShapedCrafting(container, result, resultResource, gridWidth, gridHeight, recipeTypeName);
        } else {
            exportShapelessCrafting(container, result, resultResource, recipeTypeName);
        }
    }

    // Shapeless crafting
    private static void exportShapelessCrafting(@NotNull SimpleContainer container, @NotNull ItemStack result, @NotNull ResourceLocation resultResource, @NotNull String recipeTypeName) {
        var recipe = new JsonObject();
        recipe.addProperty("type", recipeTypeName);

        // Add ingredients
        var ingredientsArray = new JsonArray();
        for (int i = 0; i < container.getContainerSize() - 1; i++) { // Avoid result at the end !
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                var ingredient = new JsonObject();
                var itemResource = ResourceUtils.getResourcebyItem(stack.getItem());
                if (itemResource != null) {
                    ingredient.addProperty("item", itemResource.toString());
                    ingredientsArray.add(ingredient);
                }
            }
        }

        if (ingredientsArray.isEmpty()) {
            reportError("No ingredients found for shapeless recipe!");
            return;
        }

        recipe.add("ingredients", ingredientsArray);

        // Add result
        var resultObject = new JsonObject();
        resultObject.addProperty("item", resultResource.toString());
        resultObject.addProperty("count", result.getCount());
        recipe.add("result", resultObject);

        String folder = getRecipeFolder(recipeTypeName);
        saveToFile(resultResource.getNamespace() + "/" + folder, resultResource.getPath() + "_shapeless", recipe);
    }

    // Shaped crafting
    private static void exportShapedCrafting(@NotNull SimpleContainer container, @NotNull ItemStack result, @NotNull ResourceLocation resultResource, int gridWidth, int gridHeight, @NotNull String recipeTypeName) {
        var recipe = new JsonObject();
        recipe.addProperty("type", recipeTypeName);

        // Build pattern and key mapping
        Map<Item, Character> itemToKey = new HashMap<>();
        char currentKey = 'A';
        String[] pattern = new String[gridHeight];

        for (int row = 0; row < gridHeight; row++) {
            StringBuilder rowPattern = new StringBuilder();
            for (int col = 0; col < gridWidth; col++) {
                int slot = row * gridWidth + col;
                ItemStack stack = container.getItem(slot);

                if (stack.isEmpty()) {
                    rowPattern.append(' ');
                } else {
                    Item item = stack.getItem();
                    if (!itemToKey.containsKey(item)) {
                        itemToKey.put(item, currentKey++);
                    }
                    rowPattern.append(itemToKey.get(item));
                }
            }
            pattern[row] = rowPattern.toString();
        }

        if (itemToKey.isEmpty()) {
            reportError("No ingredients found for shaped recipe!");
            return;
        }

        // Add pattern
        var patternArray = new JsonArray();
        for (String row : pattern) {
            patternArray.add(row);
        }
        recipe.add("pattern", patternArray);

        // Add key mappings
        var keyObject = new JsonObject();
        for (Map.Entry<Item, Character> entry : itemToKey.entrySet()) {
            var itemResource = ResourceUtils.getResourcebyItem(entry.getKey());
            if (itemResource != null) {
                var ingredient = new JsonObject();
                ingredient.addProperty("item", itemResource.toString());
                keyObject.add(String.valueOf(entry.getValue()), ingredient);
            }
        }
        recipe.add("key", keyObject);

        // Add result
        var resultObject = new JsonObject();
        resultObject.addProperty("item", resultResource.toString());
        resultObject.addProperty("count", result.getCount());
        recipe.add("result", resultObject);

        String folder = getRecipeFolder(recipeTypeName);
        saveToFile(resultResource.getNamespace() + "/" + folder, resultResource.getPath() + "_shaped", recipe);
    }

    public static void exportSmithing(@NotNull ItemStack template, @NotNull ItemStack base, @NotNull ItemStack addition, @NotNull ItemStack result) {
        if (template == ItemStack.EMPTY || base == ItemStack.EMPTY || addition == ItemStack.EMPTY || result == ItemStack.EMPTY) {
            reportError("Failed to export smithing recipe: template, base, addition and result should not be empty !");
            return;
        }

        // Now get resources and if null return.
        var templateResource = ResourceUtils.getResourcebyItem(template.getItem());
        if (templateResource == null) {
            reportError("Failed to export smithing recipe, failed to get template ResourceLocation, returned null !");
            return;
        }
        var baseResource = ResourceUtils.getResourcebyItem(base.getItem());
        if (baseResource == null) {
            reportError("Failed to export smithing recipe, failed to get base ResourceLocation, returned null !");
            return;
        }
        var resultResource = ResourceUtils.getResourcebyItem(result.getItem());
        if (resultResource == null) {
            reportError("Failed to export smithing recipe, failed to get result ResourceLocation, returned null !");
            return;
        }
        var additionResource = ResourceUtils.getResourcebyItem(addition.getItem());
        if (additionResource == null) {
            reportError("Failed to export smithing recipe, failed to get addition ResourceLocation, returned null !");
            return;
        }

        // Now create the recipe:
        var recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:smithing_transform");

        var templateObject = new JsonObject();
        templateObject.addProperty("item", templateResource.toString());
        recipe.add("template", templateObject);

        var baseObject = new JsonObject();
        baseObject.addProperty("item", baseResource.toString());
        recipe.add("base", baseObject);

        var additionObject = new JsonObject();
        additionObject.addProperty("item", additionResource.toString());
        recipe.add("addition", additionObject);

        var resultObject = new JsonObject();
        resultObject.addProperty("item", resultResource.toString());
        recipe.add("result", resultObject);

        saveToFile(resultResource.getNamespace() + "/smithing", resultResource.getPath(), recipe);
    }

    @SuppressWarnings("DuplicatedCode")
    public static void exportFurnace(@NotNull ItemStack input, @NotNull ItemStack output, double experience, int cookingTime, @NotNull String recipeType) {
        if (input == ItemStack.EMPTY || output == ItemStack.EMPTY) {
            reportError("Failed to export " + recipeType + " recipe: input, output should not be empty !");
            return;
        }

        // Now get resources and if null return.
        var inputResource = ResourceUtils.getResourcebyItem(input.getItem());
        if (inputResource == null) {
            reportError("Failed to export" + recipeType + " recipe, failed to get input ResourceLocation, returned null !");
            return;
        }
        var outputResource = ResourceUtils.getResourcebyItem(output.getItem());
        if (outputResource == null) {
            reportError("Failed to export " + recipeType + " recipe, failed to get output ResourceLocation, returned null !");
            return;
        }

        // Avoid negative experience !
        if (experience < 0.0) {
            if (PLAYER != null) {
                PLAYER.sendSystemMessage(Component.literal("Experience is in negative value, set to 0 by default !").withStyle(ChatFormatting.GOLD)); // Warning.
            }
            experience = 0.0;
        }

        var recipe = new JsonObject();
        recipe.addProperty("type", recipeType);
        var ingredientObject = new JsonObject();
        ingredientObject.addProperty("item", inputResource.toString());
        recipe.add("ingredient", ingredientObject);
        recipe.addProperty("result", outputResource.toString());
        recipe.addProperty("experience", experience);
        recipe.addProperty("cookingtime", cookingTime);

        saveToFile(outputResource.getNamespace() + "/" + recipeType.replace("minecraft:", ""), outputResource.getPath(), recipe);
    }

    private static void saveToFile(@NotNull String path, @NotNull String filename, @NotNull JsonObject recipe) {
        var recipePath = FMLPaths.GAMEDIR.get().resolve("config/craftory/recipes/" + path + "/" + filename + ".json");
        try
        {
            Files.createDirectories(recipePath.getParent());
            Files.writeString(recipePath, new GsonBuilder().setPrettyPrinting().create().toJson(recipe));
        }
        catch (IOException ex) {
            reportError("Failed to export recipe: " + ex.getMessage());
        }
        finally {
            if (PLAYER != null) {
                PLAYER.sendSystemMessage(Component.literal("Saved!").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    private static void reportError(@NotNull String errorMsg) {
        if (PLAYER != null) {
            PLAYER.sendSystemMessage(Component.literal(errorMsg).withStyle(ChatFormatting.RED));
        }
        Craftory.LOGGER.error(errorMsg);
    }

    // Get recipe folder from recipe type name
    private static String getRecipeFolder(String recipeTypeName) {
        // Extract the path from the recipe type (e.g., "minecraft:crafting_shaped" -> "crafting")
        String[] parts = recipeTypeName.split(":");
        String recipePath = parts.length > 1 ? parts[1] : parts[0];

        // Remove _shaped or _shapeless suffix if present
        recipePath = recipePath.replace("_shaped", "").replace("_shapeless", "");

        return recipePath.isEmpty() ? "crafting" : recipePath;
    }

    // Helper to trim empty rows/columns from pattern
    private static String[] trimPattern(String[] pattern, int originalWidth, int originalHeight) {
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
}
