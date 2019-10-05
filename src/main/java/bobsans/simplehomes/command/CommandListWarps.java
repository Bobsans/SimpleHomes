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

import java.util.Map;


public class CommandListWarps extends CommandBase {
    public String getName() {
        return "listwarps";
    }

    public String getUsage(ICommandSender sender) {
        return "Use /listwarps to show list of warp points.";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender instanceof EntityPlayer && Config.ALLOW_WARP_POINTS;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            EntityPlayer player = (EntityPlayer)sender;
            Storage storage = Storage.get(player.world);

            if (storage == null) {
                throw new CommandException("Unable to save warp point.");
            }

            PlayerData data = storage.load(PlayerData.class, player.getUniqueID().toString()).setPlayer(player);

            for (Map.Entry<String, WarpPoint> entry : data.warps.entrySet()) {
                sender.sendMessage(new TextComponentString(entry.getKey() + ": " + entry.getValue().x + ", " + entry.getValue().y + ", " + entry.getValue().z));
            }
        } else {
            throw new WrongUsageException("Command /listwarps no take arguments.");
        }
    }
}
