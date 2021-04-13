package by.bobsans.simplehomes.binding;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.network.KeyBindingMessage;
import by.bobsans.simplehomes.network.NetworkingManager;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class FastHomeKeyBinding {
    public static final KeyBinding binding = new KeyBinding(Reference.MODID + ".keys.fastHome", GLFW.GLFW_KEY_H, "simplehomes.title");

    public static void pressKey() {
        NetworkingManager.CHANNEL.sendToServer(new KeyBindingMessage(KeyBindingMessage.HOME_KEY_PRESSED));
    }
}
