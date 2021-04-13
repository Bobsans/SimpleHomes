package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.WarpPointNameArgument;
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
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class CommandWarp extends CommandBase {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(build());
    }

    static LiteralArgumentBuilder<CommandSource> build() {
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
                    .argument("name", WarpPointNameArgument.name())
                    .executes((context) -> deleteWarp(context, context.getSource().getPlayerOrException(), WarpPointNameArgument.getName(context, "name")))))
            .then(Commands
                .literal("list")
                .executes((context) -> warpsList(context, context.getSource().getPlayerOrException())))
            .then(Commands
                .argument("name", WarpPointNameArgument.name())
                .executes((context) -> warp(context, context.getSource().getPlayerOrException(), WarpPointNameArgument.getName(context, "name"))));
    }

    public static int setWarp(CommandContext<CommandSource> context, PlayerEntity player, String name) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.size() >= Config.COMMON.MAXIMUM_WARP_POINTS.get()) {
            throw new CommandException(new TranslationTextComponent(Reference.MODID + ".commands.setWarp.reachedMaximum"));
        }

        playerData.addWarp(new WarpPoint(player, name));
        manager.setDirty();

        sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.setWarp", name));

        return Command.SINGLE_SUCCESS;
    }

    public static int deleteWarp(CommandContext<CommandSource> context, PlayerEntity player, String warpName) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.containsKey(warpName)) {
            manager.getOrCreate(player).warps.remove(warpName);
            manager.setDirty();
            sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.deleteWarp", warpName));
        } else {
            throw new CommandException(new TranslationTextComponent(Reference.MODID + ".commands.argument.warp.doesNotExists", warpName));
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int warpsList(CommandContext<CommandSource> context, PlayerEntity player) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.size() > 0) {
            for (WarpPoint warp : playerData.warps.values()) {
                String coords = String.format("%.2f", warp.x) + ", " + String.format("%.2f", warp.y) + ", " + String.format("%.2f", warp.z);
                sendFeedback(context.getSource(), new StringTextComponent(warp.name + ": " + coords));
            }
        } else {
            sendFeedback(context.getSource(), new TranslationTextComponent(Reference.MODID + ".commands.warpsList.empty"));
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int warp(CommandContext<CommandSource> context, PlayerEntity player, String warpName) throws CommandException {
        PlayerDataManager manager = PlayerDataManager.instance();
        PlayerData playerData = manager.getOrCreate(player);

        if (playerData.warps.containsKey(warpName)) {
            PlayerTeleporter.teleport(player, playerData.warps.get(warpName));
        } else {
            throw new CommandException(new TranslationTextComponent(Reference.MODID + ".commands.argument.warp.doesNotExists", warpName));
        }

        return Command.SINGLE_SUCCESS;
    }
}
