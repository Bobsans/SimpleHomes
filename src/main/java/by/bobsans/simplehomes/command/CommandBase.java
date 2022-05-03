package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.PlayerNameWithHomeArgument;
import by.bobsans.simplehomes.command.arguments.WarpPointNameArgument;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.network.chat.Component;

public class CommandBase {
    static void sendFeedback(CommandSourceStack source, Component message) {
        source.sendSuccess(message.copy().withStyle(ChatFormatting.DARK_AQUA), true);
    }

    public static void registerArguments() {
        ArgumentTypes.register(Reference.MODID + ":player_data", PlayerNameWithHomeArgument.class, new EmptyArgumentSerializer<>(PlayerNameWithHomeArgument::userName));
        ArgumentTypes.register(Reference.MODID + ":warp_point", WarpPointNameArgument.class, new EmptyArgumentSerializer<>(WarpPointNameArgument::name));
    }
}
