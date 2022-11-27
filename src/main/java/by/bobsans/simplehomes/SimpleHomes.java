package by.bobsans.simplehomes;

import by.bobsans.simplehomes.command.ArgumentTypesRegistry;
import by.bobsans.simplehomes.command.CommandHome;
import by.bobsans.simplehomes.command.CommandWarp;
import by.bobsans.simplehomes.config.Config;
import by.bobsans.simplehomes.network.NetworkingManager;
import by.bobsans.simplehomes.proxy.ClientProxy;
import by.bobsans.simplehomes.proxy.IProxy;
import by.bobsans.simplehomes.proxy.ServerProxy;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
public class SimpleHomes {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public SimpleHomes() {
        Config.register(ModLoadingContext.get());

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ArgumentTypesRegistry.REGISTRY.register(bus);
        bus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        NetworkingManager.init();
        proxy.setup(event);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        CommandHome.register(dispatcher);
        CommandWarp.register(dispatcher);
    }
}

