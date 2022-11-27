package by.bobsans.simplehomes.binding;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.network.KeyBindingMessage;
import by.bobsans.simplehomes.network.NetworkingManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class FastHomeKeyBinding {
    public static final KeyMapping binding = new KeyMapping(
        Reference.MODID + ".keys.fast_home",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_H,
        Reference.MODID + ".title"
    );

    public static void pressKey() {
        NetworkingManager.CHANNEL.sendToServer(new KeyBindingMessage(KeyBindingMessage.HOME_KEY_PRESSED));
    }
}
