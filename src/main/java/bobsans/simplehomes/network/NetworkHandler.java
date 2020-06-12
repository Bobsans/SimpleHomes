package bobsans.simplehomes.network;

import bobsans.simplehomes.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    public static SimpleChannel NETWORK;

    public static void init() {
        NETWORK = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Reference.MODID, "networking"))
            .clientAcceptedVersions((a) -> true)
            .serverAcceptedVersions((a) -> true)
            .networkProtocolVersion(() -> Reference.VERSION)
            .simpleChannel();

        NETWORK.registerMessage(MessageType.HOME_KEY_PRESSED.ordinal(), KeyBindingMessage.class, KeyBindingMessage::write, KeyBindingMessage::read, KeyBindingMessage.Handler::onMessage);
    }

    public enum MessageType {
        HOME_KEY_PRESSED
    }
}
