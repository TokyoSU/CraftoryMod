package net.tokyosu.craftory.io.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/// SimpleContainer container, ItemStack result, boolean shaped, int width, int height, String recipe_type, String recipe_folder, int tier

public class CraftingBuilder {
    private @Nullable SimpleContainer simpleContainer;
    private @Nullable ItemStack result;
    private @Nullable String recipe_type;
    private @Nullable String recipe_type_prefix;
    private @Nullable String recipe_type_suffix;
    private @Nullable String recipe_folder;
    private int width;
    private int height;
    private int tier;
    private int energy_required;
    private boolean isShapeless;
    private boolean is_extended_crafting;
    private boolean is_ender_craft;
    private boolean is_flux_craft;

    /**
     * A valid container for the craft to export item.
     * @param data A valid SimpleContainer !
     */
    public @NotNull CraftingBuilder container(@NotNull SimpleContainer data) {
        this.simpleContainer = data;
        return this;
    }

    /**
     * Set the item that will be crafted if the recipe is valid.
     * @param stack A valid ItemStack.
     */
    public @NotNull CraftingBuilder result(@NotNull ItemStack stack) {
        this.result = stack;
        return this;
    }

    /**
     * The recipe type that will be used to craft the item.
     * @param recipeType A valid recipe type.
     */
    public @NotNull CraftingBuilder type(@NotNull String recipeType) {
        this.recipe_type = recipeType;
        return this;
    }

    /**
     * The recipe prefix type that will be added when wrote.
     * @param recipeTypePrefix A valid recipe type prefix.
     */
    public @NotNull CraftingBuilder type_prefix(@NotNull String recipeTypePrefix) {
        this.recipe_type_prefix = recipeTypePrefix;
        return this;
    }

    /**
     * The recipe suffix type that will be added when wrote.
     * @param recipeTypeSuffix A valid recipe type suffix.
     */
    public @NotNull CraftingBuilder type_suffix(@NotNull String recipeTypeSuffix) {
        this.recipe_type_suffix = recipeTypeSuffix;
        return this;
    }

    /**
     * Where the file will be saved.
     * @param savedFolderName A folder name or path.
     */
    public @NotNull CraftingBuilder folder(@NotNull String savedFolderName) {
        this.recipe_folder = savedFolderName;
        return this;
    }

    /**
     * Size of the crafting table.
     * @param size Column count.
     */
    public @NotNull CraftingBuilder width(int size) {
        this.width = size;
        return this;
    }

    /**
     * Size of the crafting table.
     * @param size Row count.
     */
    public @NotNull CraftingBuilder height(int size) {
        this.height = size;
        return this;
    }

    /**
     * Only if extended crafting is used.
     * @param value A tier, 0 = crafting table, 4 = ultimate table, 5 = epic table (only if extended crafting: expanded is used).
     */
    public @NotNull CraftingBuilder tier(int value) {
        this.tier = value;
        return this;
    }

    /**
     * Convert the export to shapeless instead of shaped.
     */
    public @NotNull CraftingBuilder shapeless() {
        this.isShapeless = true;
        return this;
    }

    /**
     * Does this craft export support extended crafting table ?
     */
    public @NotNull CraftingBuilder extended_crafting() {
        this.is_extended_crafting = true;
        return this;
    }

    /**
     * Does this craft use Flux Crafter ?
     */
    public @NotNull CraftingBuilder flux_crafting(int energy_required) {
        this.is_flux_craft = true;
        this.is_ender_craft = false;
        this.is_extended_crafting = true;
        this.energy_required = energy_required;
        return this;
    }

    /**
     * Does this craft use Ender Crafter ?
     */
    public @NotNull CraftingBuilder ender_crafting() {
        this.is_ender_craft = true;
        this.is_flux_craft = false;
        this.is_extended_crafting = true;
        return this;
    }

    public void export() {
        if (this.result == null || this.result == ItemStack.EMPTY || this.result.isEmpty()) {
            JsonReporter.reportError(Component.literal("Failed to export crafting recipe: result should not be null or empty !"));
            return;
        }

        if (this.simpleContainer == null || this.simpleContainer.isEmpty()) {
            JsonReporter.reportError(Component.literal("Failed to export crafting recipe: container should not be null or empty !"));
            return;
        }

        // These can be null, no need to return error.
        if (this.recipe_type == null || this.recipe_type.isEmpty()) {
            this.recipe_type = this.isShapeless ? "minecraft:crafting_shapeless" : "minecraft:crafting_shaped";
            JsonReporter.reportWarning(Component.literal("Recipe type shouldn't be null or empty !"));
        }

        if (this.is_ender_craft || this.is_flux_craft) { // If both suffix and prefix is empty, add ender and flux prefix.
            this.recipe_type = this.isShapeless ? "shapeless_" : "shaped_" + this.recipe_type;
        }

        if (this.recipe_type_prefix != null || this.recipe_type_suffix != null) {
            if (this.recipe_type_prefix != null && !this.recipe_type_prefix.isEmpty()) {
                this.recipe_type = this.recipe_type_prefix + this.recipe_type;
            }
            if (this.recipe_type_suffix != null && !this.recipe_type_suffix.isEmpty()) {
                this.recipe_type = this.recipe_type + this.recipe_type_suffix;
            }
        }

        if (this.recipe_folder == null || this.recipe_folder.isEmpty()) {
            this.recipe_folder = "crafting";
            JsonReporter.reportWarning(Component.literal("Recipe folder shouldn't be null or empty !"));
        }

        var result_resource = ResourceUtils.getResourcebyItem(this.result.getItem());
        if (result_resource == null) {
            JsonReporter.reportError(Component.literal("Failed to export crafting recipe, failed to get result ResourceLocation !"));
            return;
        }

        if (this.isShapeless) {
            exportShapeless(this.simpleContainer, result, result_resource, recipe_type, recipe_folder, tier);
        } else {
            exportShaped(this.simpleContainer, result, result_resource, recipe_type, recipe_folder, tier);
        }
    }

    private void checkExtendedCraftingHeader(@NotNull JsonObject recipe) {
        if (this.is_extended_crafting) {
            // Check for tier, ender and flux use minecraft craft:
            if (!(this.is_ender_craft || this.is_flux_craft)) {
                recipe.addProperty("tier", tier);
            }
            if (this.is_flux_craft && this.energy_required != 0) {
                recipe.addProperty("powerRequired", this.energy_required);
            }
        }
    }

    private void exportShapeless(@NotNull SimpleContainer container, @NotNull ItemStack result, @NotNull ResourceLocation result_resource, @NotNull String recipe_type, @NotNull String recipe_folder, int tier) {
        var recipe = new JsonObject();
        recipe.addProperty("type", recipe_type);

        this.checkExtendedCraftingHeader(recipe);

        // Add ingredients
        var ingredients = this.createIngredientPatternShapeless(container);
        if (ingredients.isEmpty()) {
            JsonReporter.reportError(Component.literal("No ingredients found for shapeless recipe!"));
            return;
        }
        recipe.add("ingredients", ingredients);
        recipe.add("result", JsonUtils.createIngredientObject(result, result_resource, 1));

        new JsonSaverBuilder().data(recipe).stack(result).suffix("shapeless").recipe_folder(recipe_folder).build();
    }

    // Shaped crafting
    private void exportShaped(@NotNull SimpleContainer container, @NotNull ItemStack result, @NotNull ResourceLocation result_resource, @NotNull String recipe_type, @NotNull String recipe_folder, int tier) {
        var recipe = new JsonObject();
        recipe.addProperty("type", recipe_type);

        this.checkExtendedCraftingHeader(recipe);

        // Build pattern with NBT-aware ingredients
        var keyAndPattern = this.createIngredientPatternShaped(container);
        if (keyAndPattern.getB().isEmpty()) {
            JsonReporter.reportError(Component.literal("No ingredients found for shaped recipe!"));
            return;
        }
        recipe.add("pattern", keyAndPattern.getB());
        recipe.add("key", keyAndPattern.getA());
        recipe.add("result", JsonUtils.createIngredientObject(result, result_resource, 1));

        new JsonSaverBuilder().data(recipe).stack(result).suffix("shaped").recipe_folder(recipe_folder).build();
    }

    private @NotNull Tuple<JsonObject, JsonArray> createIngredientPatternShaped(@NotNull SimpleContainer container) {
        var itemToKey = new HashMap<ItemStackKey, Character>();
        var pattern = new String[this.height];
        var keyObject = new JsonObject();
        var patternArray = new JsonArray();
        var currentKey = 'A';

        for (int row = 0; row < this.height; row++) {
            StringBuilder rowPattern = new StringBuilder();
            for (int col = 0; col < this.width; col++) {
                int slot = row * this.width + col;
                ItemStack stack = container.getItem(slot);

                if (stack.isEmpty()) {
                    rowPattern.append(' ');
                } else {
                    ItemStackKey key = new ItemStackKey(stack);
                    if (!itemToKey.containsKey(key)) {
                        itemToKey.put(key, currentKey++);
                    }
                    rowPattern.append(itemToKey.get(key));
                }
            }
            pattern[row] = rowPattern.toString();
        }

        if (itemToKey.isEmpty()) {
            JsonReporter.reportError(Component.literal("No ingredients found for shaped recipe!"));
            return new Tuple<>(keyObject, patternArray);
        }

        // Add pattern
        for (String row : pattern) {
            patternArray.add(row);
        }

        // Add key mappings with NBT
        for (Map.Entry<ItemStackKey, Character> entry : itemToKey.entrySet()) {
            var stack = entry.getKey().stack();
            var itemResource = ResourceUtils.getResourcebyItem(stack.getItem());
            if (itemResource != null) {
                keyObject.add(String.valueOf(entry.getValue()), JsonUtils.createIngredientObject(stack, itemResource));
            }
        }

        return new Tuple<>(keyObject, patternArray);
    }

    private @NotNull JsonArray createIngredientPatternShapeless(@NotNull SimpleContainer container) {
        var ingredientsArray = new JsonArray();
        for (int i = 0; i < container.getContainerSize() - 1; i++) { // Avoid result at the end !
            var stack = container.getItem(i);
            if (!stack.isEmpty()) {
                var itemResource = ResourceUtils.getResourcebyItem(stack.getItem());
                if (itemResource != null) {
                    ingredientsArray.add(JsonUtils.createIngredientObject(stack, itemResource));
                }
            }
        }
        return ingredientsArray;
    }
}
