package io.github.halffocused.diamond_is_uncraftable.network.message.server;

import io.github.halffocused.diamond_is_uncraftable.entity.stand.AbstractStandEntity;
import io.github.halffocused.diamond_is_uncraftable.network.message.IMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncStandMasterPacket implements IMessage<SSyncStandMasterPacket> {
    private int standID;
    private int masterID;

    public SSyncStandMasterPacket() {
    }

    public SSyncStandMasterPacket(int standID, int masterID) {
        this.standID = standID;
        this.masterID = masterID;
    }

    @Override
    public void encode(SSyncStandMasterPacket message, PacketBuffer buffer) {
        buffer.writeInt(message.standID);
        buffer.writeInt(message.masterID);
    }

    @Override
    public SSyncStandMasterPacket decode(PacketBuffer buffer) {
        return new SSyncStandMasterPacket(buffer.readInt(), buffer.readInt());
    }

    @Override
    public void handle(SSyncStandMasterPacket message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                if (Minecraft.getInstance().world == null) return;
                Entity stand = Minecraft.getInstance().world.getEntityByID(message.standID);
                Entity master = Minecraft.getInstance().world.getEntityByID(message.masterID);
                if (stand != null && master != null)
                    ((AbstractStandEntity) stand).setMaster((PlayerEntity) master);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
