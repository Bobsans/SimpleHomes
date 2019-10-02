package bobsans.simplehomes.network;

import bobsans.simplehomes.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static SimpleNetworkWrapper network;

    public static void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        network.registerMessage(KeyBindingMessage.Handler.class, KeyBindingMessage.class, MessageType.HOME_KEY_PRESSED.ordinal(), Side.SERVER);
    }

    public enum MessageType {
        HOME_KEY_PRESSED
    }
}
