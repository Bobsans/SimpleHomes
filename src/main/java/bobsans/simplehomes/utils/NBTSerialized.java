package bobsans.simplehomes.utils;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTSerialized {
    String getNBTKey();

    NBTTagCompound toNBT();
}
