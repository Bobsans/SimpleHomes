package by.bobsans.simplehomes.gui;

import by.bobsans.boblib.gui.screens.ConfigScreenBase;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueBoolean;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueInteger;
import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.config.Config;

public class ConfigGUI extends ConfigScreenBase {
    public ConfigGUI() {
        super(Reference.MODID);
    }

    protected OptionsListWidget fillOptions(OptionsListWidget widget) {
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.COMMON.ALLOW_WARP_POINTS));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.COMMON.MAXIMUM_WARP_POINTS));
        return widget;
    }
}
