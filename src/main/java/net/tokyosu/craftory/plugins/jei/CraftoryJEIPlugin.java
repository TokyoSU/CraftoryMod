package net.tokyosu.craftory.plugins.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.ResourceLocation;
import net.tokyosu.apocalypselib.utils.ModUtils;
import net.tokyosu.craftory.Craftory;
import net.tokyosu.craftory.plugins.jei.handler.extendedcrafting.*;
import net.tokyosu.craftory.plugins.jei.handler.minecraft.*;
import net.tokyosu.craftory.screen.extendedcrafting.*;
import net.tokyosu.craftory.screen.minecraft.*;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@JeiPlugin
public class CraftoryJEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(SmithingScreen.class, new SmithingIngredientHandler());
        registration.addGhostIngredientHandler(BlastFurnaceScreen.class, new BlastFurnaceIngredientHandler());
        registration.addGhostIngredientHandler(SmokerScreen.class, new SmokerIngredientHandler());
        registration.addGhostIngredientHandler(FurnaceScreen.class, new FurnaceIngredientHandler());
        registration.addGhostIngredientHandler(CraftingTableScreen.class, new CraftingTableIngredientHandler());
        registration.addGhostIngredientHandler(CampfireScreen.class, new CampfireIngredientHandler());

        if (ModUtils.isLoaded("extendedcrafting")) {
            registration.addGhostIngredientHandler(AdvancedTableScreen.class, new AdvancedCraftingIngredientHandler());
            registration.addGhostIngredientHandler(BasicTableScreen.class, new BasicCraftingIngredientHandler());
            registration.addGhostIngredientHandler(EliteTableScreen.class, new EliteCraftingIngredientHandler());
            registration.addGhostIngredientHandler(FluxCrafterScreen.class, new FluxCrafterIngredientHandler());
            registration.addGhostIngredientHandler(EnderCrafterScreen.class, new EnderCrafterIngredientHandler());
            registration.addGhostIngredientHandler(EpicCraftingScreen.class, new EpicCraftingIngredientHandler());
            registration.addGhostIngredientHandler(UltimateCraftingScreen.class, new UltimateCraftingIngredientHandler());
        }
    }
}
