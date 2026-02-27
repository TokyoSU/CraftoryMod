package net.tokyosu.craftory.handler;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tokyosu.apocalypselib.utils.ModUtils;
import net.tokyosu.craftory.Craftory;
import net.tokyosu.craftory.registry.MenuRegistry;
import net.tokyosu.craftory.screen.extendedcrafting.*;
import net.tokyosu.craftory.screen.minecraft.*;

@Mod.EventBusSubscriber(modid = Craftory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Minecraft:

            MenuScreens.register(MenuRegistry.BLAST_FURNACE_MENU.get(), BlastFurnaceScreen::new);
            MenuScreens.register(MenuRegistry.CRAFTING_TABLE_MENU.get(), CraftingTableScreen::new);
            MenuScreens.register(MenuRegistry.FURNACE_MENU.get(), FurnaceScreen::new);
            MenuScreens.register(MenuRegistry.GRIND_STONE_MENU.get(), CampfireScreen::new);
            MenuScreens.register(MenuRegistry.SMITHING_MENU.get(), SmithingScreen::new);
            MenuScreens.register(MenuRegistry.SMOKER_MENU.get(), SmokerScreen::new);

            // ExtendedCrafting:
            if (ModUtils.isLoaded("extendedcrafting")) {
                MenuScreens.register(MenuRegistry.ULTIMATE_TABLE_MENU.get(), UltimateCraftingScreen::new);
                MenuScreens.register(MenuRegistry.EPIC_TABLE_MENU.get(), EpicCraftingScreen::new);
                MenuScreens.register(MenuRegistry.BASIC_TABLE_MENU.get(), BasicTableScreen::new);
                MenuScreens.register(MenuRegistry.ELITE_TABLE_MENU.get(), EliteTableScreen::new);
                MenuScreens.register(MenuRegistry.ADVANCED_TABLE_MENU.get(), AdvancedTableScreen::new);
                MenuScreens.register(MenuRegistry.ENDER_CRAFTER_MENU.get(), EnderCrafterScreen::new);
                MenuScreens.register(MenuRegistry.FLUX_CRAFTER_MENU.get(), FluxCrafterScreen::new);
            }
        });
    }
}
