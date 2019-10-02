package bobsans.simplehomes.types;

import bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class WarpPoint implements NBTSerialized {
    public String name;
    public int dimension;
    public double x;
    public double y;
    public double z;
    public float pitch;
    public float yaw;

    private WarpPoint() {}

    public WarpPoint(EntityPlayer player, String name) {
        this.name = name;
        this.dimension = player.getEntityWorld().provider.getDimension();
        this.x = player.posX;
        this.y = player.posY;
        this.z = player.posZ;
        this.pitch = player.rotationPitch;
        this.yaw = player.rotationYaw;
    }

    public WarpPoint(NBTTagCompound tag) {
        this.dimension = tag.getInteger("dimension");
        this.x = tag.getDouble("x");
        this.y = tag.getDouble("y");
        this.z = tag.getDouble("z");
        this.pitch = tag.getFloat("pitch");
        this.yaw = tag.getFloat("yaw");
    }

    public String getNBTKey() {
        return name;
    }

    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("dimension", dimension);
        tag.setDouble("x", x);
        tag.setDouble("y", y);
        tag.setDouble("z", z);
        tag.setFloat("pitch", pitch);
        tag.setFloat("yaw", yaw);
        return tag;
    }
}
