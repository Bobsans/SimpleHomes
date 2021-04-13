package by.bobsans.simplehomes.proxy;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.gui.ConfigGUI;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
    public void setup(final FMLCommonSetupEvent event) {
        ModList.get()
            .getModContainerById(Reference.MODID)
            .ifPresent((consumer) -> consumer.registerExtensionPoint(
                ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, parent) -> new ConfigGUI(parent)
            ));
    }
}

