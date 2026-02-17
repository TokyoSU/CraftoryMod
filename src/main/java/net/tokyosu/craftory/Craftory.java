package net.tokyosu.elemental_worlds;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tokyosu.elemental_worlds.init.EntityRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("removal")
@Mod(ElementalWorlds.MOD_ID)
public class ElementalWorlds
{
    public static final String MOD_ID = "assets/elemental_worlds";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public ElementalWorlds() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityRegistry.ENTITIES.register(bus);
    }
}
