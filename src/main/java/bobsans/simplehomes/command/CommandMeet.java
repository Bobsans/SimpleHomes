package bobsans.simplehomes.command;

import bobsans.simplehomes.types.PlayerData;
import bobsans.simplehomes.utils.PlayerTeleporter;
import bobsans.simplehomes.utils.Storage;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommandMeet extends CommandBase {
    public String getName() {
        return "meet";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /meet <user> to meet user's home.";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (String name : server.getOnlinePlayerNames()) {
                if (name.startsWith(args[0])) {
                    result.add(name);
                }
            }
        } else {
            Collections.addAll(result, server.getOnlinePlayerNames());
        }

        return result;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            EntityPlayer player = (EntityPlayer)sender;

            Storage storage = Storage.get(player.world);

            if (storage == null) {
                throw new CommandException("Unable to load " + args[0] + " home.");
            }

            for (String uid : storage.data.getKeySet()) {
                PlayerData data = storage.load(PlayerData.class, uid);

                if (data.name.toLowerCase().equals(args[0].toLowerCase())) {
                    if (data.home == null) {
                        throw new CommandException(args[0] + " no have home!");
                    }

                    PlayerTeleporter.transferPlayer(server, player, data.home);
                    break;
                }
            }
        } else {
            throw new WrongUsageException("Command /meet take one argument.");
        }
    }
}
