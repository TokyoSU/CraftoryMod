package net.tokyosu.craftory.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tokyosu.craftory.Craftory;
import net.tokyosu.craftory.menu.extendedcrafting.*;
import net.tokyosu.craftory.menu.minecraft.*;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Craftory.MOD_ID);

    // Minecraft Menus.

    public static final RegistryObject<MenuType<BlastFurnaceMenu>> BLAST_FURNACE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".blastfurnace.editor.name", () -> IForgeMenuType.create(BlastFurnaceMenu::new));
    public static final RegistryObject<MenuType<CraftingTableMenu>> CRAFTING_TABLE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".craftingtable.editor.name", () -> IForgeMenuType.create(CraftingTableMenu::new));
    public static final RegistryObject<MenuType<FurnaceMenu>> FURNACE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".furnace.editor.name", () -> IForgeMenuType.create(FurnaceMenu::new));
    public static final RegistryObject<MenuType<CampfireMenu>> GRIND_STONE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".grindstone.editor.name", () -> IForgeMenuType.create(CampfireMenu::new));
    public static final RegistryObject<MenuType<SmithingMenu>> SMITHING_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".smithing.editor.name", () -> IForgeMenuType.create(SmithingMenu::new));
    public static final RegistryObject<MenuType<SmokerMenu>> SMOKER_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".smoker.editor.name", () -> IForgeMenuType.create(SmokerMenu::new));

    // ExtendedCrafting Menus.

    public static final RegistryObject<MenuType<AdvancedTableMenu>> ADVANCED_TABLE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".advancedtable.editor.name", () -> IForgeMenuType.create(AdvancedTableMenu::new));
    public static final RegistryObject<MenuType<BasicTableMenu>> BASIC_TABLE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".basictable.editor.name", () -> IForgeMenuType.create(BasicTableMenu::new));
    public static final RegistryObject<MenuType<EliteTableMenu>> ELITE_TABLE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".elitetable.editor.name", () -> IForgeMenuType.create(EliteTableMenu::new));
    public static final RegistryObject<MenuType<UltimateTableMenu>> ULTIMATE_TABLE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".ultimatetable.editor.name", () -> IForgeMenuType.create(UltimateTableMenu::new));
    public static final RegistryObject<MenuType<EpicTableMenu>> EPIC_TABLE_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".epictable.editor.name", () -> IForgeMenuType.create(EpicTableMenu::new));
    public static final RegistryObject<MenuType<CompressorMenu>> COMPRESSOR_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".compressor.editor.name", () -> IForgeMenuType.create(CompressorMenu::new));
    public static final RegistryObject<MenuType<EnderCrafterMenu>> ENDER_CRAFTER_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".endercrafter.editor.name", () -> IForgeMenuType.create(EnderCrafterMenu::new));
    public static final RegistryObject<MenuType<FluxCrafterMenu>> FLUX_CRAFTER_MENU = MENUS.register("menu." + Craftory.MOD_ID + ".fluxcrafter.editor.name", () -> IForgeMenuType.create(FluxCrafterMenu::new));

    public static void init(IEventBus bus) {
        MENUS.register(bus);
    }
}
