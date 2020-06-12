package bobsans.simplehomes.core;

import bobsans.simplehomes.Reference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
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

    public static PlayerDataManager load() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            ServerWorld overworld = server.getWorld(DimensionType.OVERWORLD);
            DimensionSavedDataManager storage = overworld.getSavedData();
            return storage.getOrCreate(PlayerDataManager::new, DATA_NAME);
        }

        return clientInstance;
    }

    public void read(CompoundNBT nbt) {
        ListNBT list = nbt.getList("data", Constants.NBT.TAG_COMPOUND);
        list.forEach((item) -> {
            if (item instanceof CompoundNBT) {
                PlayerData playerData = new PlayerData((CompoundNBT) item);
                data.put(playerData.getNBTKey(), playerData);
            }
        });
    }

    public CompoundNBT write(CompoundNBT compound) {
        ListNBT listNBT = new ListNBT();
        data.forEach((key, playerData) -> listNBT.add(playerData.toNBT()));
        compound.put("data", listNBT);
        return compound;
    }

    public Collection<PlayerData> getPlayerDataList() {
        return data.values();
    }

    public PlayerData getOrCreate(PlayerEntity player) {
        if (data.containsKey(player.getUniqueID().toString())) {
            return data.get(player.getUniqueID().toString());
        }
        PlayerData playerData = new PlayerData(player);
        data.put(playerData.getNBTKey(), playerData);
        markDirty();
        return playerData;
    }
}
