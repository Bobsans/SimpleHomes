package by.bobsans.simplehomes.binding;

import by.bobsans.simplehomes.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraftforge.api.distmarker.Dist.CLIENT;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = CLIENT)
public class KeyBinding {
    public static final KeyMapping FAST_HOME_KEY = new KeyMapping(
        Reference.MOD_ID + ".keys.fastHome",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_H,
        Reference.MOD_ID + ".title"
    );

    @SubscribeEvent
    public static void onRegisterKeymappings(RegisterKeyMappingsEvent event) {
        event.register(FAST_HOME_KEY);
    }
}
