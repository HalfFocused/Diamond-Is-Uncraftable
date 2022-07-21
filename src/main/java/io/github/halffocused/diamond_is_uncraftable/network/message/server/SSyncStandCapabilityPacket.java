package io.github.halffocused.diamond_is_uncraftable.network.message.server;

import io.github.halffocused.diamond_is_uncraftable.DiamondIsUncraftable;
import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.network.message.IMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncStandCapabilityPacket implements IMessage<SSyncStandCapabilityPacket> {
    private INBT data;

    public SSyncStandCapabilityPacket() {
    }

    private SSyncStandCapabilityPacket(CompoundNBT compoundNBT) {
        data = compoundNBT;
    }

    public SSyncStandCapabilityPacket(Stand props) {
        data = Stand.STAND.getStorage().writeNBT(Stand.STAND, props, null);
    }

    @Override
    public void encode(SSyncStandCapabilityPacket message, PacketBuffer buffer) {
        buffer.writeCompoundTag((CompoundNBT) message.data);
    }

    @Override
    public SSyncStandCapabilityPacket decode(PacketBuffer buffer) {
        return new SSyncStandCapabilityPacket(buffer.readCompoundTag());
    }

    @Override
    public void handle(SSyncStandCapabilityPacket message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = DiamondIsUncraftable.PROXY.getPlayer();
                if (player == null) return;
                Stand.getLazyOptional(player).ifPresent(props -> Stand.STAND.getStorage().readNBT(Stand.STAND, props, null, message.data));
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
