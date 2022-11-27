package by.bobsans.simplehomes.command;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class BaseCommand {
    static void sendFeedback(CommandSourceStack source, Component message) {
        source.sendSuccess(message.copy().withStyle(ChatFormatting.DARK_AQUA), true);
    }
}
