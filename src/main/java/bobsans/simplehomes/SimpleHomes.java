package bobsans.simplehomes;

import bobsans.simplehomes.command.CommandBase;
import bobsans.simplehomes.command.CommandHome;
import bobsans.simplehomes.command.CommandWarp;
import bobsans.simplehomes.config.Config;
import bobsans.simplehomes.network.NetworkHandler;
import bobsans.simplehomes.proxy.ClientProxy;
import bobsans.simplehomes.proxy.IProxy;
import bobsans.simplehomes.proxy.ServerProxy;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
public class SimpleHomes {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public SimpleHomes() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        NetworkHandler.init();
        Config.register(ModLoadingContext.get());
        CommandBase.registerArguments();
        proxy.setup(event);
    }

    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getCommandDispatcher();

        CommandHome.register(dispatcher);
        CommandWarp.register(dispatcher);
    }
}

