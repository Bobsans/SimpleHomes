package by.bobsans.simplehomes.utils;

import by.bobsans.simplehomes.core.WarpPoint;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;


public class PlayerTeleporter {
    public static void teleport(PlayerEntity player, WarpPoint to) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        MinecraftServer server = player.getServer();
        if (server != null) {
            ServerWorld toWorld = server.getLevel(to.world);
            if (toWorld != null) {
                serverPlayer.teleportTo(toWorld, to.x, to.y, to.z, to.rotation.y, to.rotation.x);
            }
        }
    }
}
