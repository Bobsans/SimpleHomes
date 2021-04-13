package by.bobsans.simplehomes.gui;

import by.bobsans.boblib.gui.screens.ConfigScreenBase;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueBoolean;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueInteger;
import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.config.Config;
import net.minecraft.client.gui.screen.Screen;

public class ConfigGUI extends ConfigScreenBase {
    public ConfigGUI(Screen parent) {
        super(parent, Reference.MODID, null, null);
    }

    protected OptionsListWidget fillOptions(OptionsListWidget widget) {
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.COMMON.ALLOW_WARP_POINTS));
        widget.add(new OptionsEntryValueInteger(Reference.MODID, Config.COMMON.MAXIMUM_WARP_POINTS));
        return widget;
    }
}
