package bobsans.simplehomes.utils;

import bobsans.simplehomes.Reference;
import bobsans.simplehomes.SimpleHomesMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;


public class Storage extends WorldSavedData {
    private static final String DATA_NAME = Reference.MOD_ID;

    public NBTTagCompound data = new NBTTagCompound();

    public Storage(String name) {
        super(name);
        if (!name.equals(DATA_NAME)) {
            SimpleHomesMod.logger.warn("Created Storage with invalid name: \"" + name + "\"");
        }
    }

    public static Storage get(World world) {
        MapStorage storage = world.getMapStorage();

        if (storage != null) {
            Storage save = (Storage)storage.getOrLoadData(Storage.class, DATA_NAME);

            if (save == null) {
                save = new Storage(DATA_NAME);
                storage.setData(DATA_NAME, save);
            }

            return save;
        }

        return null;
    }

    public <T> T load(Class<T> cls, String name) {
        try {
            return cls.getConstructor(NBTTagCompound.class).newInstance(data.getCompoundTag(name));
        } catch (Exception exception) {
            throw new RuntimeException("Failed to instantiate " + cls, exception);
        }
    }

    public void save(NBTSerialized obj) {
        data.setTag(obj.getNBTKey(), obj.toNBT());
        markDirty();
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.data = nbt;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return this.data;
    }
}
