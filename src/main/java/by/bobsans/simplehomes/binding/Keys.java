package by.bobsans.simplehomes.binding;

import by.bobsans.simplehomes.Reference;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraftforge.api.distmarker.Dist.CLIENT;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = CLIENT)
public class Keys {
    static {
        FastHomeKeyBinding.binding.setKeyConflictContext(KeyConflictContext.IN_GAME);
        ClientRegistry.registerKeyBinding(FastHomeKeyBinding.binding);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (FastHomeKeyBinding.binding.isDown()) {
            FastHomeKeyBinding.pressKey();
        }
    }
}
