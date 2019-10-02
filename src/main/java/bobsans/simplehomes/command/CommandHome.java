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


public class CommandHome extends CommandBase {
    public String getName() {
        return "home";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /home for teleport to home location.";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            EntityPlayer player = (EntityPlayer)sender;

            Storage storage = Storage.get(player.world);

            if (storage == null) {
                throw new CommandException("Unable to load home.");
            }

            PlayerData data = storage.load(PlayerData.class, player.getUniqueID().toString());
            if (data.home == null) {
                throw new CommandException("Please set a home first!");
            }

            PlayerTeleporter.transferPlayer(server, player, data.home);
        } else {
            throw new WrongUsageException("Command /home does not take any arguments.");
        }
    }
}
