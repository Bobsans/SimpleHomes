package by.bobsans.simplehomes.core;

import by.bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class WarpPoint implements NBTSerialized {
    public String name;
    public RegistryKey<World> world;
    public double x;
    public double y;
    public double z;
    public Vector2f rotation;

    private WarpPoint() { }

    public WarpPoint(PlayerEntity player, String name) {
        this.name = name;
        this.world = player.getCommandSenderWorld().dimension();
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
        this.rotation = player.getRotationVector();
    }

    public WarpPoint(CompoundNBT tag) {
        this.name = tag.getString("name");
        this.world = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("world")));
        this.x = tag.getDouble("x");
        this.y = tag.getDouble("y");
        this.z = tag.getDouble("z");
        this.rotation = new Vector2f(tag.getFloat("rotx"), tag.getFloat("roty"));
    }

    public String getNBTKey() {
        return name;
    }

    public CompoundNBT toNBT() {
        CompoundNBT tag = new CompoundNBT();
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
