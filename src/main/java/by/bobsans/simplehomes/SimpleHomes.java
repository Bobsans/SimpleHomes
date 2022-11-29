package by.bobsans.simplehomes;

import by.bobsans.simplehomes.binding.KeyHandler;
import by.bobsans.simplehomes.command.ArgumentTypesRegistry;
import by.bobsans.simplehomes.command.CommandHome;
import by.bobsans.simplehomes.command.CommandWarp;
import by.bobsans.simplehomes.gui.ConfigScreen;
import by.bobsans.simplehomes.network.NetworkingManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
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

@Mod(Reference.MOD_ID)
public class SimpleHomes {
    public static final Logger LOGGER = LogManager.getLogger();

    public SimpleHomes() {
        Config.register(ModLoadingContext.get());

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ArgumentTypesRegistry.REGISTRY.register(bus);
        bus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(KeyHandler.class);
    }

    public void setup(final FMLCommonSetupEvent event) {
        NetworkingManager.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ConfigScreen.register(Reference.MOD_ID, (minecraft, screen) -> new ConfigScreen());
        });
    }

    @SubscribeEvent
    public void onRegisterCommands(final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        CommandHome.register(dispatcher);
        CommandWarp.register(dispatcher);
    }
}

