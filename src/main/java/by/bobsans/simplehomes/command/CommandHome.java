package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.PlayerWithHomeArgument;
import by.bobsans.simplehomes.core.PlayerData;
import by.bobsans.simplehomes.core.PlayerDataManager;
import by.bobsans.simplehomes.core.WarpPoint;
import by.bobsans.simplehomes.utils.PlayerTeleporter;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class CommandHome extends BaseCommand {
    private static final String name = "home";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(build());
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands
            .literal(name)
            .executes((context) -> goHome(context, context.getSource().getPlayerOrException()))
            .then(Commands
                .literal("set")
                .executes((context) -> setHome(context, context.getSource().getPlayerOrException())))
            .then(Commands
                .literal("meet")
                .then(Commands
                    .argument("target", PlayerWithHomeArgument.userName())
                    .executes((context) -> meetPlayerHome(
                        context,
                        context.getSource().getPlayerOrException(),
                        PlayerWithHomeArgument.getUserName(context, "target")
                    ))));
    }

    private static int setHome(CommandContext<CommandSourceStack> context, Player player) {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        playerData.setHome(new WarpPoint(player, "home"));
        manager.setDirty();

        sendFeedback(context.getSource(), Component.translatable(Reference.MOD_ID + ".commands.setHome"));

        return Command.SINGLE_SUCCESS;
    }

    private static int meetPlayerHome(CommandContext<CommandSourceStack> context, Player player, String targetUserName) {
        Player target = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByName(targetUserName);

        if (target != null) {
            PlayerDataManager manager = PlayerDataManager.instance();
            PlayerData targetPlayerData = manager.getOrCreate(target);

            if (targetPlayerData.home != null) {
                PlayerTeleporter.teleport(player, targetPlayerData.home);
                sendFeedback(context.getSource(), Component.translatable(Reference.MOD_ID + ".commands.meet", targetPlayerData.name));
            } else {
                throw new CommandRuntimeException(Component.translatable(Reference.MOD_ID + ".commands.meet.hasNoHome", targetPlayerData.name));
            }
        } else {
            throw new CommandRuntimeException(Component.translatable(Reference.MOD_ID + ".commands.meet.playerNotFound", targetUserName));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int goHome(CommandContext<CommandSourceStack> context, Player player) {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.home != null) {
            PlayerTeleporter.teleport(player, playerData.home);
            sendFeedback(context.getSource(), Component.translatable(Reference.MOD_ID + ".commands.goHome"));
        } else {
            throw new CommandRuntimeException(Component.translatable(Reference.MOD_ID + ".commands.goHome.notSet"));
        }

        return Command.SINGLE_SUCCESS;
    }
}
