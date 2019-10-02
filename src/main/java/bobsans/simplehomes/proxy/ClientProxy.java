package bobsans.simplehomes.proxy;

import bobsans.simplehomes.binding.Keys;
import bobsans.simplehomes.config.Config;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        Config.init(e.getSuggestedConfigurationFile());
    }

    public void init(FMLInitializationEvent e) {
        super.init(e);
        Keys.init();
    }
}

