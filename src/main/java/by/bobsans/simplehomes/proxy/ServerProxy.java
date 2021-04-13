package by.bobsans.simplehomes.proxy;

import by.bobsans.simplehomes.SimpleHomes;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ServerProxy implements IProxy {
    public void setup(final FMLCommonSetupEvent event) {
        SimpleHomes.LOGGER.info("Loading Simple Homes...");
    }
}
