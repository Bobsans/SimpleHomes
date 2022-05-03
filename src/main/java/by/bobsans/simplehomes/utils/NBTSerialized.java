package by.bobsans.simplehomes.utils;

import net.minecraft.nbt.CompoundTag;

public interface NBTSerialized {
    String getNBTKey();

    CompoundTag toNBT();
}
