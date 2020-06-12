package bobsans.simplehomes.utils;

import bobsans.simplehomes.core.WarpPoint;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.hooks.BasicEventHooks;


public class PlayerTeleporter {
    public static void teleport(PlayerEntity player, WarpPoint to) {
        DimensionType oldDimension = player.getEntityWorld().getDimension().getType();

        if (!oldDimension.equals(to.dimension)) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            MinecraftServer server = player.getEntityWorld().getServer();
            if (server != null && server.getWorld(to.dimension) != null) {
                changeDimension(serverPlayer, new BlockPos(to.x, to.y, to.z), to.dimension);
            }
        }

        player.rotationYaw = to.yaw;
        player.rotationPitch = to.pitch;
        player.setPositionAndUpdate(to.x, to.y, to.z);
    }

    public static void changeDimension(ServerPlayerEntity player, BlockPos pos, DimensionType type) {
        if (!ForgeHooks.onTravelToDimension(player, type)) {
            return;
        }

        DimensionType dimensiontype = player.dimension;

        ServerWorld srcWorld = player.server.getWorld(dimensiontype);
        player.dimension = type;
        ServerWorld destWorld = player.server.getWorld(type);
        WorldInfo worldinfo = player.world.getWorldInfo();
        player.connection.sendPacket(new SRespawnPacket(type, WorldInfo.byHashing(worldinfo.getSeed()), worldinfo.getGenerator(), player.interactionManager.getGameType()));
        player.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
        PlayerList playerlist = player.server.getPlayerList();
        playerlist.updatePermissionLevel(player);
        srcWorld.removeEntity(player, true);
        player.revive();
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        srcWorld.getProfiler().endSection();
        srcWorld.getProfiler().startSection("placing");
        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);

        srcWorld.getProfiler().endSection();
        player.setWorld(destWorld);
        destWorld.addDuringCommandTeleport(player);
        player.connection.setPlayerLocation(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        player.interactionManager.setWorld(destWorld);
        player.connection.sendPacket(new SPlayerAbilitiesPacket(player.abilities));
        playerlist.sendWorldInfo(player, destWorld);
        playerlist.sendInventory(player);

        for (EffectInstance effectinstance : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
        }

        player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
        BasicEventHooks.firePlayerChangedDimensionEvent(player, dimensiontype, type);
    }
}
