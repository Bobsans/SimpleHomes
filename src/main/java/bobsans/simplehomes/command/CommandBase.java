package bobsans.simplehomes.command;

import bobsans.simplehomes.Reference;
import bobsans.simplehomes.command.arguments.PlayerNameWithHomeArgument;
import bobsans.simplehomes.command.arguments.WarpPointNameArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class CommandBase {
    private static final Style feedbackStyle = new Style().setColor(TextFormatting.DARK_AQUA);

    static void sendFeedback(CommandSource source, ITextComponent message) {
        source.sendFeedback(message.setStyle(feedbackStyle), true);
    }

    public static void registerArguments() {
        ArgumentTypes.register(Reference.MODID + ":player_data", PlayerNameWithHomeArgument.class, new ArgumentSerializer<>(PlayerNameWithHomeArgument::userName));
        ArgumentTypes.register(Reference.MODID + ":warp_point", WarpPointNameArgument.class, new ArgumentSerializer<>(WarpPointNameArgument::name));
    }
}
