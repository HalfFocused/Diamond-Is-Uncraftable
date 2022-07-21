package io.github.halffocused.diamond_is_uncraftable.entity.stand;

import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.capability.StandChunkEffects;
import io.github.halffocused.diamond_is_uncraftable.init.EntityInit;
import io.github.halffocused.diamond_is_uncraftable.util.movesets.HoveringMoveHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

@SuppressWarnings("ConstantConditions")
public class EchoesAct2Entity extends AbstractStandEntity {
    public EchoesAct2Entity(EntityType<? extends AbstractStandEntity> type, World world) {
        super(type, world);
    }

    public HoveringMoveHandler getController(){
        return null;
    }

    public void addSoundEffect() {
        if (getMaster() == null) return;
        Stand.getLazyOptional(master).ifPresent(props -> {
            if (props.getAbilityUseCount() < 4) {
                Vec3d position = master.getLookVec().mul(3, 1, 3).add(master.getPositionVec());
                if (world.getBlockState(new BlockPos(position)).isSolid() && world.getBlockState(new BlockPos(position)).getBlock() != Blocks.BEDROCK)
                    StandChunkEffects.getLazyOptional(world.getChunkAt(new BlockPos(position))).ifPresent(chunkEffects -> {
                        if (chunkEffects.getSoundEffects().containsKey(master.getUniqueID()) && chunkEffects.getSoundEffects().get(master.getUniqueID()).contains(new BlockPos(position)))
                            return;
                        chunkEffects.addSoundEffect(master, new BlockPos(position));
                        props.addAffectedChunk(world.getChunkAt(new BlockPos(position)).getPos());
                        props.setAbilityUseCount(props.getAbilityUseCount() + 1);
                        master.sendStatusMessage(new StringTextComponent("Echoes has added an effect to the block at X" + (int) position.getX() + " Y" + (int) position.getY() + " Z" + (int) position.getZ() + "."), true);
                    });
            }
        });
    }

    public void removeAllSoundEffects() {
        if (getMaster() == null) return;
        Stand.getLazyOptional(master).ifPresent(props -> {
            props.getAffectedChunkList().forEach(pos -> {
                Chunk chunk = world.getChunk(pos.x, pos.z);
                if (world.getChunkProvider().isChunkLoaded(pos))
                    StandChunkEffects.getLazyOptional(chunk).ifPresent(chunkEffects -> {
                        if (chunkEffects.getSoundEffects().containsKey(master.getUniqueID())) {
                            chunkEffects.getSoundEffects().remove(master.getUniqueID());
                            props.setAbilityUseCount(props.getAbilityUseCount() - 1);
                        }
                    });
            });
            if (!master.world.isRemote)
                master.sendStatusMessage(new StringTextComponent("Removed all sound effects."), true);
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (getMaster() != null) {
            Stand.getLazyOptional(master).ifPresent(props -> {
                ability = props.getAbility();
                if (props.getAct() == props.getMaxAct() - 1 && props.getStandOn()) {
                    remove();
                    EchoesAct1Entity echoesAct1Entity = new EchoesAct1Entity(EntityInit.ECHOES_ACT_1.get(), world);
                    Vec3d position = master.getLookVec().mul(0.5, 1, 0.5).add(master.getPositionVec()).add(0, 0.5, 0);
                    echoesAct1Entity.setLocationAndAngles(position.getX(), position.getY(), position.getZ(), master.rotationYaw, master.rotationPitch);
                    echoesAct1Entity.setMaster(master);
                    echoesAct1Entity.setMasterUUID(master.getUniqueID());
                    world.addEntity(echoesAct1Entity);
                }
            });

            followMaster();
            setRotationYawHead(master.rotationYawHead);
            setRotation(master.rotationYaw, master.rotationPitch);

            if (master.swingProgressInt == 0 && !attackRush)
                attackTick = 0;
        }
    }
}
