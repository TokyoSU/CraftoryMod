package net.tokyosu.craftory.io.json;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.tokyosu.craftory.Craftory;
import org.jetbrains.annotations.NotNull;

public class JsonReporter {
    private static Player PLAYER;

    public static void setPlayer(@NotNull Player player) {
        PLAYER = player;
    }

    public static void reportInfo(@NotNull MutableComponent infoMsg) {
        if (PLAYER != null) {
            PLAYER.sendSystemMessage(infoMsg);
        }
        Craftory.LOGGER.info(infoMsg.getString());
    }

    public static void reportWarning(@NotNull MutableComponent warnMsg) {
        if (PLAYER != null) {
            PLAYER.sendSystemMessage(warnMsg.withStyle(ChatFormatting.GOLD));
        }
        Craftory.LOGGER.warn(warnMsg.getString());
    }

    public static void reportError(@NotNull MutableComponent errorMsg) {
        if (PLAYER != null) {
            PLAYER.sendSystemMessage(errorMsg.withStyle(ChatFormatting.RED));
        }
        Craftory.LOGGER.error(errorMsg.getString());
    }
}
