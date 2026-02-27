package net.tokyosu.craftory;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final ResourceLocation MINECRAFT_EDITOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/minecraft_editor.png");
    public static final Rect2i VALID_BURN_TEXTURE = new Rect2i(184, 1, 14, 14);
    public static final Rect2i INVALID_CRAFT_TEXTURE = new Rect2i(161, 1, 22, 15);
    public static final Rect2i INVALID_BURN_TEXTURE = new Rect2i(199, 1, 13, 13);
    public static final ResourceLocation EXTENDEDCRAFTING_EDITOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/extendedcrafting_editor.png");
    public static final ResourceLocation SAVE_BUTTON = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/save_button.png");
    public static final ResourceLocation TRASH_BUTTON = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/trash_button.png");
    public static final ResourceLocation EXPERIENCE_ORB_ICON = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/experience_orb.png");
    public static final ResourceLocation BURN_TIME_ICON = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/burn_time.png");
    public static final ResourceLocation ENERGY_ICON = ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "textures/gui/energy.png");
}
