package bobsans.simplehomes.proxy;

import bobsans.simplehomes.SimpleHomesMod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class ServerProxy extends CommonProxy {
    public void init(FMLInitializationEvent e) {
        super.init(e);
        SimpleHomesMod.logger.info("Loading @MOD_NAME@ v@MOD_VERSION@");
    }

    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        SimpleHomesMod.logger.info("@MOD_NAME@ v@MOD_VERSION@ loaded!");
    }
}
