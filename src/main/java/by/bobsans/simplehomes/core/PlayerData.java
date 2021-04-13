package by.bobsans.simplehomes.core;

import by.bobsans.simplehomes.utils.NBTSerialized;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class PlayerData implements NBTSerialized {
    public String uuid;
    public String name;
    public WarpPoint home = null;
    public Map<String, WarpPoint> warps = new HashMap<>();

    public PlayerData(PlayerEntity player) {
        this.setPlayer(player);
    }

    public PlayerData(CompoundNBT tag) {
        this.updateFromNBT(tag);
    }

    public void setPlayer(PlayerEntity player) {
        uuid = player.getStringUUID();
        name = player.getName().getString();
    }

    public void updateFromNBT(CompoundNBT tag) {
        uuid = tag.getString("uuid");
        name = tag.getString("name");

        CompoundNBT homeTag = tag.getCompound("home");
        if (homeTag.size() != 0) {
            home = new WarpPoint(homeTag);
        }

        INBT warpsList = tag.get("warps");
        if (warpsList instanceof ListNBT) {
            for (INBT warpTag : (ListNBT) warpsList) {
                WarpPoint warp = new WarpPoint((CompoundNBT) warpTag);
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

    public CompoundNBT toNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("uuid", uuid);
        tag.putString("name", name);

        if (home != null) {
            tag.put("home", home.toNBT());
        }

        if (!warps.isEmpty()) {
            tag.put("warps", this.warps.values().stream().map(WarpPoint::toNBT).collect(Collectors.toCollection(ListNBT::new)));
        }

        return tag;
    }
}
