package bobsans.simplehomes.types;

import bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;


public class PlayerData implements NBTSerialized {
    public String uid;
    public String name;
    public WarpPoint home = null;
    public Map<String, WarpPoint> warps = new HashMap<>();

    public PlayerData(EntityPlayer player) {
        this.setPlayer(player);
    }

    public PlayerData(NBTTagCompound tag) {
        this.updateFromNBT(tag);
    }

    public PlayerData setPlayer(EntityPlayer player) {
        uid = player.getUniqueID().toString();
        name = player.getName();
        return this;
    }

    public PlayerData updateFromNBT(NBTTagCompound tag) {
        uid = tag.getString("uid");
        name = tag.getString("name");

        NBTTagCompound homeTag = tag.getCompoundTag("home");
        if (homeTag.getSize() != 0) {
            home = new WarpPoint(homeTag);
        }

        NBTTagCompound warpsTag = tag.getCompoundTag("warps");
        for (String key : warpsTag.getKeySet()) {
            warps.put(key, new WarpPoint(warpsTag.getCompoundTag(key)));
        }
        return this;
    }

    public PlayerData setHome(WarpPoint home) {
        this.home = home;
        return this;
    }

    public PlayerData deleteWarp(String name) {
        this.warps.remove(name);
        return this;
    }

    public PlayerData addWarp(WarpPoint point) {
        this.warps.put(point.name, point);
        return this;
    }

    public String getNBTKey() {
        return uid;
    }

    public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("uid", uid);
        tag.setString("name", name);

        if (home != null) {
            tag.setTag("home", home.toNBT());
        }

        if (!warps.isEmpty()) {
            NBTTagCompound warpsTag = new NBTTagCompound();
            for (Map.Entry<String, WarpPoint> warp : this.warps.entrySet()) {
                warpsTag.setTag(warp.getKey(), warp.getValue().toNBT());
            }
            tag.setTag("warps", warpsTag);
        }

        return tag;
    }
}
