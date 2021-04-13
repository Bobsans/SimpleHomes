package by.bobsans.simplehomes.network;

import by.bobsans.simplehomes.SimpleHomes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class KeyBindingMessage {
    public static byte HOME_KEY_PRESSED = 1;

    protected byte data;

    public KeyBindingMessage(byte data) {
        this.data = data;
    }

    public static KeyBindingMessage read(PacketBuffer buffer) {
        return new KeyBindingMessage(buffer.readByte());
    }

    public static void write(KeyBindingMessage message, PacketBuffer buffer) {
        buffer.writeByte(message.data);
    }

    public static class Handler {
        public static void onMessage(KeyBindingMessage message, Supplier<NetworkEvent.Context> context) {
            if (message.data == HOME_KEY_PRESSED) {
                context.get().enqueueWork(() -> {
                    CommandDispatcher<CommandSource> dispatcher = ServerLifecycleHooks.getCurrentServer().getCommands().getDispatcher();
                    ServerPlayerEntity player = context.get().getSender();

                    if (player != null) {
                        try {
                            dispatcher.execute("home", player.createCommandSourceStack());
                        } catch (CommandSyntaxException e) {
                            SimpleHomes.LOGGER.error(e.getMessage());
                        }
                    }
                });
            }
            context.get().setPacketHandled(true);
        }
    }
}
