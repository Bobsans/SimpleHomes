package by.bobsans.simplehomes.network;

import by.bobsans.simplehomes.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkingManager {
    public static SimpleChannel CHANNEL;
    private static int id = 0;

    private static int nextId() {
        return id++;
    }

    public static void init() {
        CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Reference.MODID, "main"), () -> Reference.VERSION, (s) -> true, (s) -> true);

        CHANNEL.registerMessage(nextId(), KeyBindingMessage.class, KeyBindingMessage::write, KeyBindingMessage::read, KeyBindingMessage.Handler::onMessage);
    }
}
