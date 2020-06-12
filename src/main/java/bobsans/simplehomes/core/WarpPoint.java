package bobsans.simplehomes.core;

import bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.dimension.DimensionType;

public class WarpPoint implements NBTSerialized {
    public String name;
    public DimensionType dimension;
    public double x;
    public double y;
    public double z;
    public float pitch;
    public float yaw;

    private WarpPoint() { }

    public WarpPoint(PlayerEntity player, String name) {
        this.name = name;
        this.dimension = player.getEntityWorld().getDimension().getType();
        this.x = player.getPosX();
        this.y = player.getPosY();
        this.z = player.getPosZ();
        this.pitch = player.rotationPitch;
        this.yaw = player.rotationYaw;
    }

    public WarpPoint(CompoundNBT tag) {
        this.name = tag.getString("name");
        this.dimension = DimensionType.getById(tag.getInt("dimension"));
        this.x = tag.getDouble("x");
        this.y = tag.getDouble("y");
        this.z = tag.getDouble("z");
        this.pitch = tag.getFloat("pitch");
        this.yaw = tag.getFloat("yaw");
    }

    public String getNBTKey() {
        return name;
    }

    public CompoundNBT toNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("name", name);
        tag.putInt("dimension", dimension.getId());
        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        tag.putFloat("pitch", pitch);
        tag.putFloat("yaw", yaw);
        return tag;
    }
}
