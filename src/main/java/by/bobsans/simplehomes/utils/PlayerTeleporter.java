package by.bobsans.simplehomes.utils;

import by.bobsans.simplehomes.core.WarpPoint;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;


public class PlayerTeleporter {
    public static void teleport(Player player, WarpPoint to) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        MinecraftServer server = player.getServer();
        if (server != null) {
            ServerLevel toLevel = server.getLevel(to.world);
            if (toLevel != null) {
                serverPlayer.teleportTo(toLevel, to.x, to.y, to.z, to.rotation.y, to.rotation.x);
            }
        }
    }
}
