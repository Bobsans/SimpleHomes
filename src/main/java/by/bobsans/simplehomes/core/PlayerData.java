package by.bobsans.simplehomes.core;

import by.bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class PlayerData implements NBTSerialized {
    public String uuid;
    public String name;
    public WarpPoint home = null;
    public Map<String, WarpPoint> warps = new HashMap<>();

    public PlayerData(Player player) {
        this.setPlayer(player);
    }

    public PlayerData(CompoundTag tag) {
        this.updateFromNBT(tag);
    }

    public void setPlayer(Player player) {
        uuid = player.getStringUUID();
        name = player.getName().getString();
    }

    public void updateFromNBT(CompoundTag tag) {
        uuid = tag.getString("uuid");
        name = tag.getString("name");

        CompoundTag homeTag = tag.getCompound("home");
        if (homeTag.size() != 0) {
            home = new WarpPoint(homeTag);
        }

        Tag warpList = tag.get("warps");
        if (warpList instanceof ListTag) {
            for (Tag warpTag : (ListTag) warpList) {
                WarpPoint warp = new WarpPoint((CompoundTag) warpTag);
                warps.put(warp.name, warp);
            }
        }
    }

    public void setHome(WarpPoint home) {
        this.home = home;
    }

    public void deleteWarp(WarpPoint warp) {
        this.warps.remove(warp.name);
    }

    public void addWarp(WarpPoint warp) {
        this.warps.put(warp.name, warp);
    }

    public String getNBTKey() {
        return uuid;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("uuid", uuid);
        tag.putString("name", name);

        if (home != null) {
            tag.put("home", home.toNBT());
        }

        if (!warps.isEmpty()) {
            tag.put("warps", this.warps.values().stream().map(WarpPoint::toNBT).collect(Collectors.toCollection(ListTag::new)));
        }

        return tag;
    }
}
