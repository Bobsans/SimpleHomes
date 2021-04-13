package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.PlayerNameWithHomeArgument;
import by.bobsans.simplehomes.core.PlayerData;
import by.bobsans.simplehomes.core.PlayerDataManager;
import by.bobsans.simplehomes.core.WarpPoint;
import by.bobsans.simplehomes.utils.PlayerTeleporter;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CommandHome extends CommandBase {
    private static final String name = "home";

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(build());
    }

    public static LiteralArgumentBuilder<CommandSource> build() {
        return Commands
            .literal(name)
            .executes((context) -> goHome(context, context.getSource().getPlayerOrException()))
            .then(Commands
                .literal("set")
                .executes((context) -> setHome(context, context.getSource().getPlayerOrException())))
            .then(Commands
                .literal("meet")
                .then(Commands
                    .argument("target", PlayerNameWithHomeArgument.userName())
                    .executes((context) -> meetPlayerHome(
                        context,
                        context.getSource().getPlayerOrException(),
                        PlayerNameWithHomeArgument.getUserName(context, "target")
                    ))));
    }

    private static int setHome(CommandContext<CommandSource> context, PlayerEntity player) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        playerData.setHome(new WarpPoint(player, "home"));
        manager.setDirty();

        sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.setHome"));

        return Command.SINGLE_SUCCESS;
    }

    private static int meetPlayerHome(CommandContext<CommandSource> context, PlayerEntity player, String targetUserName) throws CommandException {
        PlayerEntity target = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByName(targetUserName);

        if (target != null) {
            PlayerDataManager manager = PlayerDataManager.instance();
            PlayerData targetPlayerData = manager.getOrCreate(target);

            if (targetPlayerData.home != null) {
                PlayerTeleporter.teleport(player, targetPlayerData.home);
                sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.meet", targetPlayerData.name));
            } else {
                throw new CommandException(new TranslationTextComponent(Reference.MODID + ".commands.meet.hasNoHome", targetPlayerData.name));
            }
        } else {
            throw new CommandException(new TranslationTextComponent(Reference.MODID + ".commands.meet.playerNotFound", targetUserName));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int goHome(CommandContext<CommandSource> context, PlayerEntity player) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.home != null) {
            PlayerTeleporter.teleport(player, playerData.home);
            sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.goHome"));
        } else {
            throw new CommandException(new TranslationTextComponent(Reference.MODID + ".commands.goHome.notSet"));
        }

        return Command.SINGLE_SUCCESS;
    }
}
