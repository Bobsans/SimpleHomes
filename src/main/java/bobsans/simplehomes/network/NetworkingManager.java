package bobsans.simplehomes.network;

import bobsans.simplehomes.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
