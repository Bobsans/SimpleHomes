package bobsans.simplehomes.binding;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Keys {
    public static void init() {
        HomeKeyBinding.init();

        MinecraftForge.EVENT_BUS.register(EventHandler.class);
    }

    public static class EventHandler {
        @SideOnly(Side.CLIENT)
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            if (HomeKeyBinding.binding.isPressed()) {
                HomeKeyBinding.pressKey();
            }
        }
    }
}
