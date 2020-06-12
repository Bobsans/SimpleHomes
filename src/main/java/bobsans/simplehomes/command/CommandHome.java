package bobsans.simplehomes.command;

import bobsans.simplehomes.Reference;
import bobsans.simplehomes.command.arguments.PlayerNameWithHomeArgument;
import bobsans.simplehomes.core.PlayerData;
import bobsans.simplehomes.core.PlayerDataManager;
import bobsans.simplehomes.core.WarpPoint;
import bobsans.simplehomes.utils.PlayerTeleporter;
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
            .executes((context) -> goHome(context, context.getSource().asPlayer()))
            .then(Commands
                .literal("set")
                .executes((context) -> setHome(context, context.getSource().asPlayer())))
            .then(Commands
                .literal("meet")
                .then(Commands
                    .argument("target", PlayerNameWithHomeArgument.userName())
                    .executes((context) -> meetPlayerHome(context, context.getSource().asPlayer(), PlayerNameWithHomeArgument.getUserName(context, "target")))));
    }

    private static int setHome(CommandContext<CommandSource> context, PlayerEntity player) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.load();
        PlayerData playerData = manager.getOrCreate(player);

        playerData.setHome(new WarpPoint(player, "home"));
        manager.markDirty();

        sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.setHome"));

        return Command.SINGLE_SUCCESS;
    }

    private static int meetPlayerHome(CommandContext<CommandSource> context, PlayerEntity player, String targetUserName) throws CommandException {
        PlayerEntity target = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(targetUserName);

        if (target != null) {
            PlayerDataManager manager = PlayerDataManager.load();
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
        PlayerDataManager manager = PlayerDataManager.load();
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
