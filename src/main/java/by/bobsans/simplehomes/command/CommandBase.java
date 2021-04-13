package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.PlayerNameWithHomeArgument;
import by.bobsans.simplehomes.command.arguments.WarpPointNameArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandBase {
    static void sendFeedback(CommandSource source, ITextComponent message) {
        source.sendSuccess(message.copy().withStyle(TextFormatting.DARK_AQUA), true);
    }

    public static void registerArguments() {
        ArgumentTypes.register(Reference.MODID + ":player_data", PlayerNameWithHomeArgument.class, new ArgumentSerializer<>(PlayerNameWithHomeArgument::userName));
        ArgumentTypes.register(Reference.MODID + ":warp_point", WarpPointNameArgument.class, new ArgumentSerializer<>(WarpPointNameArgument::name));
    }
}
