package bobsans.simplehomes.command;

import bobsans.simplehomes.config.Config;
import bobsans.simplehomes.types.PlayerData;
import bobsans.simplehomes.types.WarpPoint;
import bobsans.simplehomes.utils.Storage;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;


public class CommandSetWarp extends CommandBase {
    public String getName() {
        return "setwarp";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /setwarp <name> to add warp to this location.";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && Config.ALLOW_WARP_POINTS;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            EntityPlayer player = (EntityPlayer)sender;
            Storage storage = Storage.get(player.world);

            if (storage == null) {
                throw new CommandException("Unable to save warp point.");
            }

            PlayerData data = storage.load(PlayerData.class, player.getUniqueID().toString()).setPlayer(player);
            if (data.warps.size() >= Config.MAXIMUM_WARP_POINTS) {
                throw new CommandException("You already set maximum waypoints.");
            }

            storage.save(data.addWarp(new WarpPoint(player, args[0])));

            sender.sendMessage(new TextComponentString("Waypoint \"" + args[0] + "\" set"));
        } else {
            throw new WrongUsageException("Command /setwarp take one argument.");
        }
    }
}
