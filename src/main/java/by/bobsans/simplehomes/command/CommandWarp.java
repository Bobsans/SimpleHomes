package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.WarpPointArgument;
import by.bobsans.simplehomes.config.Config;
import by.bobsans.simplehomes.core.PlayerData;
import by.bobsans.simplehomes.core.PlayerDataManager;
import by.bobsans.simplehomes.core.WarpPoint;
import by.bobsans.simplehomes.utils.PlayerTeleporter;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;


public class CommandWarp extends BaseCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(build());
    }

    static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("warp")
            .requires((source) -> Config.COMMON.ALLOW_WARP_POINTS.get())
            .then(Commands
                .literal("set")
                .then(Commands
                    .argument("name", StringArgumentType.word())
                    .executes((context) -> setWarp(context, context.getSource().getPlayerOrException(), StringArgumentType.getString(context, "name")))))
            .then(Commands
                .literal("delete")
                .then(Commands
                    .argument("name", WarpPointArgument.warpPoint())
                    .executes((context) -> deleteWarp(context, context.getSource().getPlayerOrException(), WarpPointArgument.getName(context, "name")))))
            .then(Commands
                .literal("list")
                .executes((context) -> warpList(context, context.getSource().getPlayerOrException())))
            .then(Commands
                .argument("name", WarpPointArgument.warpPoint())
                .executes((context) -> warp(context, context.getSource().getPlayerOrException(), WarpPointArgument.getName(context, "name"))));
    }

    public static int setWarp(CommandContext<CommandSourceStack> context, Player player, String name) {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.size() >= Config.COMMON.MAXIMUM_WARP_POINTS.get()) {
            throw new CommandRuntimeException(Component.translatable(Reference.MODID + ".commands.setWarp.maximumReached"));
        }

        playerData.addWarp(new WarpPoint(player, name));
        manager.setDirty();

        sendFeedback(context.getSource(), Component.translatable(Reference.MODID + ".commands.setWarp", name));

        return Command.SINGLE_SUCCESS;
    }

    public static int deleteWarp(CommandContext<CommandSourceStack> context, Player player, String warpName) {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.containsKey(warpName)) {
            manager.getOrCreate(player).warps.remove(warpName);
            manager.setDirty();
            sendFeedback(context.getSource(), Component.translatable(Reference.MODID + ".commands.deleteWarp", warpName));
        } else {
            throw new CommandRuntimeException(Component.translatable(Reference.MODID + ".commands.argument.warp.doesNotExists", warpName));
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int warpList(CommandContext<CommandSourceStack> context, Player player) {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.size() > 0) {
            for (WarpPoint warp : playerData.warps.values()) {
                String coords = String.format("%.2f", warp.x) + ", " + String.format("%.2f", warp.y) + ", " + String.format("%.2f", warp.z);
                sendFeedback(context.getSource(), Component.literal(warp.name + ": " + coords));
            }
        } else {
            sendFeedback(context.getSource(), Component.translatable(Reference.MODID + ".commands.warpList.empty"));
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int warp(CommandContext<CommandSourceStack> context, Player player, String warpName) {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.containsKey(warpName)) {
            PlayerTeleporter.teleport(player, playerData.warps.get(warpName));
        } else {
            throw new CommandRuntimeException(Component.translatable(Reference.MODID + ".commands.argument.warp.doesNotExists", warpName));
        }

        return Command.SINGLE_SUCCESS;
    }
}
