package bobsans.simplehomes.command;

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


public class CommandSetHome extends CommandBase {
    public String getName() {
        return "sethome";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /sethome to set home location.";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            EntityPlayer player = (EntityPlayer)sender;
            Storage storage = Storage.get(player.world);

            if (storage == null) {
                throw new CommandException("Unable to save home point.");
            }

            storage.save(storage.load(PlayerData.class, player.getUniqueID().toString()).setPlayer(player).setHome(new WarpPoint(player, "home")));

            sender.sendMessage(new TextComponentString("Home set"));
        } else {
            throw new WrongUsageException("Command /sethome does not take any arguments.");
        }
    }
}
