package bobsans.simplehomes.gui;

import bobsans.simplehomes.Reference;
import bobsans.simplehomes.config.Config;
import bobsans.simplehomes.gui.widgets.options.OptionsListWidget;
import bobsans.simplehomes.gui.widgets.options.value.OptionsEntryValue;
import bobsans.simplehomes.gui.widgets.options.value.OptionsEntryValueBoolean;
import bobsans.simplehomes.gui.widgets.options.value.OptionsEntryValueInteger;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class ConfigGUI extends Screen {
    private final Screen parent;
    private final Runnable saver;
    private final Runnable canceller;

    private OptionsListWidget options;
    private OptionsListWidget.Entry lastSelected = null;
    private int tooltipCounter = 0;

    private ConfigGUI(Screen parent, Runnable saver, Runnable canceller) {
        super(new TranslationTextComponent(Reference.MODID + ".config.title"));
        this.parent = parent;
        this.saver = saver;
        this.canceller = canceller;
    }

    public ConfigGUI(Screen parent) {
        this(parent, null, null);
    }

    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new ConfigGUI(null));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);

        options = getOptions();
        children.add(options);
        setFocused(options);

        addButton(new Button(width / 2 - 100, height - 25, 100, 20, I18n.format("gui.done"), w -> {
            options.save();
            if (saver != null) {
                saver.run();
            }
            onClose();
        }));

        addButton(new Button(width / 2 + 5, height - 25, 100, 20, I18n.format("gui.cancel"), w -> {
            if (canceller != null) {
                canceller.run();
            }
            onClose();
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();

        options.render(mouseX, mouseY, partialTicks);

        drawCenteredString(font, title.getFormattedText(), width / 2, 12, 16777215);

        super.render(mouseX, mouseY, partialTicks);

        if (mouseY < 32 || mouseY > height - 32) {
            return;
        }

        OptionsListWidget.Entry entry = options.getSelected();
        if (entry instanceof OptionsEntryValue) {
            if (lastSelected == entry) {
                if (tooltipCounter > 0) {
                    tooltipCounter--;
                    return;
                }
            } else {
                lastSelected = entry;
                tooltipCounter = 100;
                return;
            }

            OptionsEntryValue<?> value = (OptionsEntryValue<?>) entry;

            if (I18n.hasKey(value.getDescription())) {
                int valueX = value.getX() + 10;
                String title = value.getTitle().getFormattedText();
                if (mouseX < valueX || mouseX > valueX + font.getStringWidth(title)) {
                    return;
                }

                List<String> tooltip = Lists.newArrayList(title);
                tooltip.addAll(font.listFormattedStringToWidth(I18n.format(value.getDescription()), 200));
                renderTooltip(tooltip, mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseMoved(double x, double y) {
        options.mouseMoved(x, y);
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(minecraft).displayGuiScreen(parent);
    }

    public void addListener(IGuiEventListener listener) {
        children.add(listener);
    }

    private OptionsListWidget getOptions() {
        OptionsListWidget options = new OptionsListWidget(this, minecraft, width, height, 32, height - 32, 28);

        options.add(new OptionsEntryValueBoolean(Config.COMMON.ALLOW_WARP_POINTS));
        options.add(new OptionsEntryValueInteger(Config.COMMON.MAXIMUM_WARP_POINTS));

        return options;
    }
}
