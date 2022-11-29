package by.bobsans.simplehomes.core;

import by.bobsans.simplehomes.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class PlayerDataManager extends SavedData {
    private static final String DATA_NAME = Reference.MOD_ID;
    private final Map<String, PlayerData> data = new HashMap<>();
    private static final PlayerDataManager clientInstance = new PlayerDataManager();

    public PlayerDataManager() {
        super();
    }

    public static PlayerDataManager instance() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            DimensionDataStorage storage = server.overworld().getDataStorage();
            return storage.computeIfAbsent(PlayerDataManager::load, PlayerDataManager::new, DATA_NAME);
        }

        return clientInstance;
    }

    public static PlayerDataManager load(CompoundTag tag) {
        PlayerDataManager instance = new PlayerDataManager();

        ListTag list = tag.getList("data", Tag.TAG_COMPOUND);
        list.forEach((item) -> {
            if (item instanceof CompoundTag) {
                PlayerData playerData = new PlayerData((CompoundTag) item);
                instance.data.put(playerData.getNBTKey(), playerData);
            }
        });

        return instance;
    }

    public @NotNull CompoundTag save(CompoundTag tag) {
        ListTag listNBT = new ListTag();
        data.forEach((key, playerData) -> listNBT.add(playerData.toNBT()));
        tag.put("data", listNBT);
        return tag;
    }

    public Collection<PlayerData> getPlayerDataList() {
        return data.values();
    }

    public PlayerData getOrCreate(Player player) {
        if (data.containsKey(player.getStringUUID())) {
            return data.get(player.getStringUUID());
        }
        PlayerData playerData = new PlayerData(player);
        data.put(playerData.getNBTKey(), playerData);
        setDirty();
        return playerData;
    }
}
