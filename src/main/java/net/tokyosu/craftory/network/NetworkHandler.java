package net.tokyosu.craftory.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.tokyosu.craftory.Craftory;
import net.tokyosu.craftory.MenuType;
import net.tokyosu.craftory.network.packet.EditorOpenPacket;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(Craftory.MOD_ID, "craftory_network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        NETWORK_CHANNEL.registerMessage(0, EditorOpenPacket.class, EditorOpenPacket::encode, EditorOpenPacket::decode, EditorOpenPacket::handle);
    }

    public static void sendOpenEditor(MenuType menuType) {
        NETWORK_CHANNEL.sendToServer(new EditorOpenPacket(menuType));
    }
}
