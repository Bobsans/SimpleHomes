package by.bobsans.simplehomes.utils;

import net.minecraft.nbt.CompoundNBT;

public interface NBTSerialized {
    String getNBTKey();

    CompoundNBT toNBT();
}
