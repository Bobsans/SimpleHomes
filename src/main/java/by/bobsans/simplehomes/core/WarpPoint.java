package by.bobsans.simplehomes.core;

import by.bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

public class WarpPoint implements NBTSerialized {
    public String name;
    public ResourceKey<Level> world;
    public double x;
    public double y;
    public double z;
    public Vec2 rotation;

    private WarpPoint() {}

    public WarpPoint(Player player, String name) {
        this.name = name;
        this.world = player.getCommandSenderWorld().dimension();
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
        this.rotation = player.getRotationVector();
    }

    public WarpPoint(CompoundTag tag) {
        this.name = tag.getString("name");
        this.world = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("world")));
        this.x = tag.getDouble("x");
        this.y = tag.getDouble("y");
        this.z = tag.getDouble("z");
        this.rotation = new Vec2(tag.getFloat("rotx"), tag.getFloat("roty"));
    }

    public String getNBTKey() {
        return name;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        tag.putString("world", world.location().toString());
        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        tag.putFloat("rotx", rotation.x);
        tag.putFloat("roty", rotation.y);
        return tag;
    }
}
