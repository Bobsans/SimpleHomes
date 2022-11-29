package by.bobsans.simplehomes.network;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.network.message.KeyPressedMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkingManager {
    public static SimpleChannel CHANNEL;
    private static int id = 0;

    private static int nextId() {
        return id++;
    }

    public static void init() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MOD_ID, "main"),
            () -> Reference.MOD_VERSION,
            (version) -> version.equals(Reference.MOD_VERSION),
            (version) -> version.equals(Reference.MOD_VERSION)
        );

        CHANNEL.registerMessage(nextId(), KeyPressedMessage.class, KeyPressedMessage::encode, KeyPressedMessage::decode, KeyPressedMessage::handle);
    }
}
