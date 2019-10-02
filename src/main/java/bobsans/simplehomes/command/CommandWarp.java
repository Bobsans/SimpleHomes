package bobsans.simplehomes.command;

import bobsans.simplehomes.config.Config;
import bobsans.simplehomes.types.PlayerData;
import bobsans.simplehomes.types.WarpPoint;
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
import java.util.List;
import java.util.Map;


public class CommandWarp extends CommandBase {
    public String getName() {
        return "warp";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /warp <name> for teleport to passed warp location.";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && Config.ALLOW_WARP_POINTS;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        EntityPlayer player = (EntityPlayer)sender;
        ArrayList<String> result = new ArrayList<>();

        Storage storage = Storage.get(player.world);
        if (storage == null) {
            return result;
        }

        PlayerData data = storage.load(PlayerData.class, player.getUniqueID().toString());

        if (args.length == 1) {
            for (String name : data.warps.keySet()) {
                if (name.startsWith(args[0])) {
                    result.add(name);
                }
            }
        } else {
            result.addAll(data.warps.keySet());
        }

        return result;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            EntityPlayer player = (EntityPlayer)sender;

            Storage storage = Storage.get(player.world);

            if (storage == null) {
                throw new CommandException("Unable to load warp list.");
            }

            PlayerData data = storage.load(PlayerData.class, player.getUniqueID().toString());
            for (Map.Entry<String, WarpPoint> entry: data.warps.entrySet()) {
                if (entry.getKey().equals(args[0])) {
                    PlayerTeleporter.transferPlayer(server, player, entry.getValue());
                    return;
                }
            }

            throw new CommandException("Warp point with name \"" + args[0] + "\" not exist!");
        } else {
            throw new WrongUsageException("Command /warp take one arguments.");
        }
    }
}
