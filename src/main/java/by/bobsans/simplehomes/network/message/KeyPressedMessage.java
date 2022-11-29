package by.bobsans.simplehomes.network.message;

import by.bobsans.simplehomes.SimpleHomes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class KeyPressedMessage {
    public static byte FAST_HOME_KEY_PRESSED = 1;

    protected byte data;

    public KeyPressedMessage(byte data) {
        this.data = data;
    }

    public static KeyPressedMessage decode(FriendlyByteBuf buffer) {
        return new KeyPressedMessage(buffer.readByte());
    }

    public static void encode(KeyPressedMessage message, FriendlyByteBuf buffer) {
        buffer.writeByte(message.data);
    }

    public static void handle(KeyPressedMessage message, Supplier<NetworkEvent.Context> context) {
        if (message.data == FAST_HOME_KEY_PRESSED) {
            context.get().enqueueWork(() -> {
                CommandDispatcher<CommandSourceStack> dispatcher = ServerLifecycleHooks.getCurrentServer().getCommands().getDispatcher();
                ServerPlayer player = context.get().getSender();

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
