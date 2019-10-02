package bobsans.simplehomes.config;

import bobsans.simplehomes.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static boolean ALLOW_WARP_POINTS = true;
    public static int MAXIMUM_WARP_POINTS = 10;

    // Realization

    public static Configuration config;

    public static void init(File configFile) {
        config = new Configuration(configFile);
        syncConfig();

        MinecraftForge.EVENT_BUS.register(ConfigChangeHandler.class);
    }

    private static void syncConfig() {
        config.load();

        List<String> orderedKeys = new ArrayList<>();

        ALLOW_WARP_POINTS = config.getBoolean(ConfigItem.ALLOW_WARP_POINTS.key(), Configuration.CATEGORY_GENERAL, true, ConfigItem.ALLOW_WARP_POINTS.desc(), ConfigItem.ALLOW_WARP_POINTS.languageKey());
        orderedKeys.add(ConfigItem.ALLOW_WARP_POINTS.key());
        MAXIMUM_WARP_POINTS = config.getInt(ConfigItem.MAXIMUM_WARP_POINTS.key(), Configuration.CATEGORY_GENERAL, 10, 0, 100, ConfigItem.MAXIMUM_WARP_POINTS.desc(), ConfigItem.MAXIMUM_WARP_POINTS.languageKey());
        orderedKeys.add(ConfigItem.MAXIMUM_WARP_POINTS.key());

        config.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, orderedKeys);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static class ConfigChangeHandler {
        @SubscribeEvent(priority = EventPriority.NORMAL)
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (Reference.MOD_ID.equals(event.getModID())) {
                config.save();
                syncConfig();
            }
        }
    }
}