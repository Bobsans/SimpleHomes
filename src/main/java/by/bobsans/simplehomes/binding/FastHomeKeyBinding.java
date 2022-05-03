package by.bobsans.simplehomes.binding;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.network.KeyBindingMessage;
import by.bobsans.simplehomes.network.NetworkingManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class FastHomeKeyBinding {
    public static final KeyMapping binding = new KeyMapping(Reference.MODID + ".keys.fastHome", InputConstants.KEY_H, "simplehomes.title");

    public static void pressKey() {
        NetworkingManager.CHANNEL.sendToServer(new KeyBindingMessage(KeyBindingMessage.HOME_KEY_PRESSED));
    }
}
