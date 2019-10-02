package bobsans.simplehomes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class KeyBindingMessage implements IMessage {
    public static byte HOME_KEY_PRESSED = 1;

    protected byte data;

    public KeyBindingMessage() {}

    public KeyBindingMessage(byte data) {
        this.data = data;
    }

    public void fromBytes(ByteBuf buf) {
        this.data = buf.readByte();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeByte(data);
    }

    public static class Handler implements IMessageHandler<KeyBindingMessage, IMessage> {
        @Override
        public IMessage onMessage(KeyBindingMessage message, MessageContext ctx) {
            IThreadListener thread = ctx.side == Side.SERVER ? (WorldServer)ctx.getServerHandler().player.world : Minecraft.getMinecraft();

            if (message.data == HOME_KEY_PRESSED) {
                thread.addScheduledTask(() -> FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(ctx.getServerHandler().player, "home"));
            }

            return null;
        }
    }
}
