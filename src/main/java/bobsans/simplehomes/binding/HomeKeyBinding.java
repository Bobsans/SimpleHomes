package bobsans.simplehomes.binding;

import bobsans.simplehomes.network.KeyBindingMessage;
import bobsans.simplehomes.network.NetworkHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class HomeKeyBinding {
    public static KeyBinding binding = new KeyBinding("bobsans.simplehomes.keys.fastHome", Keyboard.KEY_H, "bobsans.simplehomes.keys.general");

    public static void init() {
        ClientRegistry.registerKeyBinding(binding);
    }

    public static void pressKey() {
        NetworkHandler.network.sendToServer(new KeyBindingMessage(KeyBindingMessage.HOME_KEY_PRESSED));
    }
}
