package by.bobsans.simplehomes.core;

import by.bobsans.simplehomes.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class PlayerDataManager extends WorldSavedData {
    private static final String DATA_NAME = Reference.MODID;
    private final Map<String, PlayerData> data = new HashMap<>();
    private static final PlayerDataManager clientInstance = new PlayerDataManager();

    public PlayerDataManager() {
        super(DATA_NAME);
    }

    public static PlayerDataManager instance() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            DimensionSavedDataManager storage = server.overworld().getDataStorage();
            return storage.computeIfAbsent(PlayerDataManager::new, DATA_NAME);
        }

        return clientInstance;
    }

    public void load(CompoundNBT nbt) {
        ListNBT list = nbt.getList("data", Constants.NBT.TAG_COMPOUND);
        list.forEach((item) -> {
            if (item instanceof CompoundNBT) {
                PlayerData playerData = new PlayerData((CompoundNBT) item);
                data.put(playerData.getNBTKey(), playerData);
            }
        });
    }

    public CompoundNBT save(CompoundNBT compound) {
        ListNBT listNBT = new ListNBT();
        data.forEach((key, playerData) -> listNBT.add(playerData.toNBT()));
        compound.put("data", listNBT);
        return compound;
    }

    public Collection<PlayerData> getPlayerDataList() {
        return data.values();
    }

    public PlayerData getOrCreate(PlayerEntity player) {
        if (data.containsKey(player.getStringUUID())) {
            return data.get(player.getStringUUID());
        }
        PlayerData playerData = new PlayerData(player);
        data.put(playerData.getNBTKey(), playerData);
        setDirty();
        return playerData;
    }
}
