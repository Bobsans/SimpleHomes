package by.bobsans.simplehomes.binding;

import by.bobsans.simplehomes.network.message.KeyPressedMessage;
import by.bobsans.simplehomes.network.NetworkingManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player != null) {
            if (KeyBinding.FAST_HOME_KEY.isDown()) {
                NetworkingManager.CHANNEL.sendToServer(new KeyPressedMessage(KeyPressedMessage.FAST_HOME_KEY_PRESSED));
            }
        }
    }
}
