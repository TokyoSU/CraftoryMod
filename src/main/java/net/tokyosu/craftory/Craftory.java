package net.tokyosu.craftory;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tokyosu.craftory.network.NetworkHandler;
import net.tokyosu.craftory.registry.MenuRegistry;
import net.tokyosu.craftory.utils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("removal")
@Mod(Craftory.MOD_ID)
public class Craftory
{
    public static final String MOD_ID = "craftory";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public Craftory() {
        MenuRegistry.init(FMLJavaModLoadingContext.get().getModEventBus());
        NetworkHandler.register();
    }
}
