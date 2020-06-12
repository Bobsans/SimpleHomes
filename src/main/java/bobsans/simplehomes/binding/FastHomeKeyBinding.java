package bobsans.simplehomes.binding;

import bobsans.simplehomes.Reference;
import bobsans.simplehomes.network.KeyBindingMessage;
import bobsans.simplehomes.network.NetworkHandler;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class FastHomeKeyBinding {
    public static final KeyBinding binding = new KeyBinding(Reference.MODID + ".keys.fastHome", GLFW.GLFW_KEY_H, "simplehomes.title");

    public static void pressKey() {
        NetworkHandler.NETWORK.sendToServer(new KeyBindingMessage(KeyBindingMessage.HOME_KEY_PRESSED));
    }
}
