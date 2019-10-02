package bobsans.simplehomes.gui;

import bobsans.simplehomes.Reference;
import bobsans.simplehomes.config.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModConfigGUIScreen extends GuiConfig {
    public ModConfigGUIScreen(GuiScreen parent) {
        super(parent, new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, ModConfigGUIScreen.getAbridgedConfigPath(Config.config.toString()));
    }
}
