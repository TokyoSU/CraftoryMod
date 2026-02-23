package net.tokyosu.craftory.handler;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tokyosu.apocalypselib.utils.ModUtils;
import net.tokyosu.craftory.Craftory;
import net.tokyosu.craftory.MenuType;
import net.tokyosu.craftory.network.NetworkHandler;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Craftory.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void onChatCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("craftory").requires(source -> source.hasPermission(1)).then(
                        Commands.literal("open").then(
                                Commands.argument("menu_name", ResourceLocationArgument.id()).suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                        Arrays.stream(MenuType.values()).filter(menuType -> ModUtils.isLoaded(menuType.namespace)).map(MenuType::getResourceLocation),
                                        builder
                                )).executes((e) -> {
                                    ResourceLocation menuId = ResourceLocationArgument.getId(e, "menu_name");
                                    String menuName = menuId.getPath();
                                    try {
                                        var type = MenuType.fromName(menuName);
                                        if (type != null) {
                                            NetworkHandler.sendOpenEditor(type);
                                        } else {
                                            e.getSource().sendFailure(Component.literal("Invalid menu type: " + menuName + ", returned value is null !").withStyle(ChatFormatting.RED));
                                            return 0;
                                        }
                                    }
                                    catch (IllegalArgumentException err) {
                                        e.getSource().sendFailure(Component.literal("Invalid menu type: " + menuName).withStyle(ChatFormatting.RED));
                                        return 0;
                                    }
                                    return 1;
                                }).build()
                        )
                )
        );
    }
}
