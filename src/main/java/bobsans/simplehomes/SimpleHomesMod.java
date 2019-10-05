package bobsans.simplehomes;

import bobsans.simplehomes.command.*;
import bobsans.simplehomes.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MC_VERSION, updateJSON = Reference.UPDATE_JSON, guiFactory = Reference.GUI_FACTORY)
public class SimpleHomesMod {
    @Mod.Metadata(value = Reference.MOD_ID)
    public static ModMetadata metadata;

    @Mod.Instance(Reference.MOD_ID)
    public static SimpleHomesMod instance;

    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
    public static CommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        metadata = event.getModMetadata();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetHome());
        event.registerServerCommand(new CommandHome());
        event.registerServerCommand(new CommandMeet());
        event.registerServerCommand(new CommandSetWarp());
        event.registerServerCommand(new CommandDelWarp());
        event.registerServerCommand(new CommandListWarps());
        event.registerServerCommand(new CommandWarp());
    }
}
