package bobsans.simplehomes.command;

import bobsans.simplehomes.config.Config;
import bobsans.simplehomes.types.PlayerData;
import bobsans.simplehomes.utils.Storage;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;


public class CommandDelWarp extends CommandBase {
    public String getName() {
        return "delwarp";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /delwarp <name> to remove warp point.";
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

            storage.save(data.deleteWarp(args[0]));

            sender.sendMessage(new TextComponentString("Waypoint \"" + args[0] + "\" deleted"));
        } else {
            throw new WrongUsageException("Command /delwarp take one argument.");
        }
    }
}
