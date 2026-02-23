package net.tokyosu.craftory.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.tokyosu.craftory.MenuType;
import net.tokyosu.craftory.network.provider.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EditorOpenPacket {
    private final MenuType menuType;

    public EditorOpenPacket(MenuType menuType) {
        this.menuType = menuType;
    }

    public static void encode(@NotNull EditorOpenPacket msg, @NotNull FriendlyByteBuf buf) {
        buf.writeEnum(msg.menuType);
    }

    public static @NotNull EditorOpenPacket decode(@NotNull FriendlyByteBuf buf) {
        return new EditorOpenPacket(buf.readEnum(MenuType.class));
    }

    public static void handle(@NotNull EditorOpenPacket msg, @NotNull Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = ctx.get().getSender();
            if (player != null) {
                switch (msg.menuType) {
                    case BLAST_FURNACE -> NetworkHooks.openScreen(player, new BlastFurnaceProvider());
                    case CRAFTING_TABLE -> NetworkHooks.openScreen(player, new CraftingTableProvider());
                    case FURNACE -> NetworkHooks.openScreen(player, new FurnaceProvider());
                    case CAMPFIRE -> NetworkHooks.openScreen(player, new GrindStoneProvider());
                    case SMITHING -> NetworkHooks.openScreen(player, new SmithingProvider());
                    case SMOKER -> NetworkHooks.openScreen(player, new SmokerProvider());
                    default -> throw new IllegalStateException("Unexpected value: " + msg.menuType);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
