package bobsans.simplehomes.utils;

import bobsans.simplehomes.types.WarpPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;


public class PlayerTeleporter extends Teleporter {
    private boolean coordCalc;
    private WarpPoint warpPoint;

    public PlayerTeleporter(WorldServer worldIn, WarpPoint warpPoint, boolean doCoordCalc) {
        super(worldIn);
        this.coordCalc = doCoordCalc;
        this.warpPoint = warpPoint;
    }

    public static void transferPlayer(MinecraftServer server, EntityPlayer player, WarpPoint to) {
        WorldServer currentWorld = server.getWorld(player.getEntityWorld().provider.getDimension());
        WorldServer destinationWorld = server.getWorld(to.dimension);

        EntityPlayerMP playerMP = (EntityPlayerMP)player;

        if (destinationWorld != currentWorld) {
            PlayerList list = server.getPlayerList();
            list.transferPlayerToDimension(playerMP, to.dimension, new PlayerTeleporter(destinationWorld, to, true));
        }

        player.rotationYaw = to.yaw;
        player.rotationPitch = to.pitch;
        player.setPositionAndUpdate(to.x, to.y, to.z);
        playerMP.setPositionAndUpdate(to.x, to.y, to.z);
    }

    public void placeInPortal(Entity entityIn, float rotationYaw) {
        if (this.coordCalc) {
            entityIn.setLocationAndAngles(warpPoint.x, warpPoint.y, warpPoint.z, warpPoint.yaw, warpPoint.pitch);
            entityIn.motionX = 0.0D;
            entityIn.motionY = 0.0D;
            entityIn.motionZ = 0.0D;
        }
    }

    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
        return false;
    }
}
