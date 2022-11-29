package by.bobsans.simplehomes.gui;

import by.bobsans.boblib.gui.screens.ConfigScreenBase;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueBoolean;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueInteger;
import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.Config;
import net.minecraft.client.Minecraft;

public class ConfigScreen extends ConfigScreenBase {
    public ConfigScreen() {
        super(Reference.MOD_ID);
    }

    protected OptionsListWidget fillOptions(OptionsListWidget widget) {
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MOD_ID, Config.Common.ALLOW_WARP_POINTS));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MOD_ID, Config.Common.MAXIMUM_WARP_POINTS));
        return widget;
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new ConfigScreen());
    }
}
