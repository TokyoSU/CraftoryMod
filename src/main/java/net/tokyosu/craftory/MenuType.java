package net.tokyosu.craftory;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum MenuType {
    // Minecraft:

    BLAST_FURNACE("minecraft"),
    CRAFTING_TABLE("minecraft"),
    FURNACE("minecraft"),
    CAMPFIRE("minecraft"),
    SMITHING("minecraft"),
    SMOKER("minecraft"),

    // Extended Crafting:

    ADVANCED_TABLE("extendedcrafting"),
    BASIC_TABLE("extendedcrafting"),
    COMPRESSOR("extendedcrafting"),
    ELITE_TABLE("extendedcrafting"),
    ENDER_CRAFTER("extendedcrafting"),
    EPIC_TABLE("extendedcrafting"),
    FLUX_CRAFTER("extendedcrafting"),
    ULTIMATE_TABLE("extendedcrafting");

    public final String namespace;

    public @NotNull String getResourceLocation() {
        return this.namespace + ":" + this.name().toLowerCase(Locale.ROOT);
    }

    public static MenuType fromName(String name) {
        try {
            return valueOf(name.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return null; // or throw a custom exception
        }
    }

    public static MenuType fromResourceLocation(String resourceLocation) {
        String[] parts = resourceLocation.split(":");
        if (parts.length != 2) return null;
        return fromName(parts[1]);
    }

    MenuType(@NotNull String namespace) {
        this.namespace = namespace;
    }
}
