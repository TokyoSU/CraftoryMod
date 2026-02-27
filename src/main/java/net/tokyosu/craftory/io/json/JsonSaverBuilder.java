package net.tokyosu.craftory.io.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.tokyosu.apocalypselib.utils.ResourceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;

public class JsonSaverBuilder {
    private @Nullable String namespace_name;
    private @Nullable String path_name;
    private @Nullable String folder_name;
    private @Nullable String recipe_type_prefix;
    private @Nullable String recipe_type_suffix;
    private @Nullable JsonObject data;

    /**
     * A valid ItemStack namespace name. (ResourceLocation getNamespace()).
     * @param namespace_name A valid path name.
     */
    public @NotNull JsonSaverBuilder namespace(@NotNull String namespace_name) {
        this.namespace_name = namespace_name;
        return this;
    }

    /**
     * A valid ItemStack path name. (ResourceLocation getPath()).
     * @param path_name A valid path name.
     */
    public @NotNull JsonSaverBuilder path(@NotNull String path_name) {
        this.path_name = path_name;
        return this;
    }

    /**
     * Get namespace and path through a ItemStack directly.
     * @param stack A valid ItemStack.
     */
    public @NotNull JsonSaverBuilder stack(@NotNull ItemStack stack) {
        var resource = ResourceUtils.getResourcebyItem(stack.getItem());
        if (resource == null) {
            JsonReporter.reportError(Component.literal("Failed to get resource namespace and path, stack is null !"));
            return this;
        }
        this.namespace_name = resource.getNamespace();
        this.path_name = resource.getPath();
        return this;
    }

    /**
     * Folder where the file will be created.
     * @param path A valid path.
     */
    public @NotNull JsonSaverBuilder recipe_folder(@NotNull String path) {
        this.folder_name = path;
        return this;
    }

    /**
     * Prefix of the recipe type, can be null.
     * @param type_prefix Example: "shaped"_crafting
     */
    public @NotNull JsonSaverBuilder prefix(@NotNull String type_prefix) {
        this.recipe_type_prefix = type_prefix + "_";
        return this;
    }

    /**
     * Suffix of the recipe type, can be null.
     * @param type_suffix Example: crafting_"shaped"
     */
    public @NotNull JsonSaverBuilder suffix(@NotNull String type_suffix) {
        this.recipe_type_suffix = "_" + type_suffix;
        return this;
    }

    /**
     * Data of the JSON to write.
     * @param object A valid JsonObject !
     */
    public @NotNull JsonSaverBuilder data(@NotNull JsonObject object) {
        this.data = object;
        return this;
    }

    /**
     * Create the file.
     */
    public void build() {
        if ((this.namespace_name == null || this.path_name == null) || (this.namespace_name.isEmpty() || this.path_name.isEmpty())) {
            JsonReporter.reportError(Component.literal("Failed to build JsonSaver, namespace or path is null or empty !"));
            return;
        }

        if (this.data == null || this.data.isJsonNull()) {
            JsonReporter.reportError(Component.literal("Failed to build JsonSaver, data is null or empty !"));
            return;
        }

        var strBuilder = new StringBuilder();
        strBuilder.append("config/craftory/generated_recipes/"); // Build base path.
        strBuilder.append(this.namespace_name).append("/"); // Now get mod path.
        strBuilder.append("recipes/");

        if (this.folder_name != null && !this.folder_name.isEmpty()) {
            strBuilder.append(this.folder_name).append("/"); // Now the recipe name.
        }

        if (this.recipe_type_prefix != null && !this.recipe_type_prefix.isEmpty()) {
            strBuilder.append(this.recipe_type_prefix);
        }
        strBuilder.append(this.path_name);
        if (this.recipe_type_suffix != null && !this.recipe_type_suffix.isEmpty()) {
            strBuilder.append(this.recipe_type_suffix);
        }

        var gameDir = FMLPaths.GAMEDIR.get();
        var resultPath = gameDir.resolve(strBuilder.append(".json").toString());

        try
        {
            Files.createDirectories(resultPath.getParent());
            Files.writeString(resultPath, new GsonBuilder().setPrettyPrinting().create().toJson(this.data));
        }
        catch (IOException ex) {
            JsonReporter.reportError(Component.literal("Failed to export recipe: " + ex.getMessage()));
        }
        finally {
            JsonReporter.reportInfo(Component.translatable("recipe_exporter.success"));
        }
    }
}
