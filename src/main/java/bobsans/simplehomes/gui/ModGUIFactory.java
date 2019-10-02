package bobsans.simplehomes.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class ModGUIFactory implements IModGuiFactory {
    public void initialize(Minecraft minecraftInstance) { }

    public boolean hasConfigGui() {
        return true;
    }

    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ModConfigGUIScreen(parentScreen);
    }

    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}
