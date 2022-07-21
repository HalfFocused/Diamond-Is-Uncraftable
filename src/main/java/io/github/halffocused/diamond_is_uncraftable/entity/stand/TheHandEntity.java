package io.github.halffocused.diamond_is_uncraftable.entity.stand;

import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.attack.TheHandPunchEntity;
import io.github.halffocused.diamond_is_uncraftable.init.SoundInit;
import io.github.halffocused.diamond_is_uncraftable.util.movesets.HoveringMoveHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class TheHandEntity extends AbstractStandEntity {
    public TheHandEntity(EntityType<? extends AbstractStandEntity> type, World world) {
        super(type, world);
    }

    public void dragEntityToStand(final int entityID) { //Final because it should NEVER be changed, as it would completely break the method.
        Entity entity = world.getEntityByID(entityID);
        if (entity == null || getMaster() == null) return;
        Stand.getLazyOptional(getMaster()).ifPresent(props -> {
            if (props.getCooldown() <= 0) {
                float yaw = getMaster().rotationYaw;
                float pitch = getMaster().rotationPitch;
                double motionX = (-MathHelper.sin(yaw / 180 * (float) Math.PI) * MathHelper.cos(pitch / 180 * (float) Math.PI));
                double motionZ = (MathHelper.cos(yaw / 180 * (float) Math.PI) * MathHelper.cos(pitch / 180 * (float) Math.PI));
                double motionY = (-MathHelper.sin((pitch) / 180 * (float) Math.PI));
                double strength = entity.getDistance(getMaster()) / 4;
                if (!world.isRemote) {
                    entity.setMotion(-motionX * strength, -motionY * strength, -motionZ * strength);
                    world.playSound(null, getPosition(), SoundInit.THE_HAND_PULL.get(), SoundCategory.NEUTRAL, 1, 1);
                    props.setCooldown(100);
                }
            }
        });
    }

    public HoveringMoveHandler getController(){
        return null;
    }

    public void teleportMaster() {
        if (world.isRemote) return;
        if (getMaster() == null) return; //Probably not necessary, but I'll leave it here anyway.
        Stand.getLazyOptional(master).ifPresent(props -> {
            if (props.getCooldown() <= 0) {
                double distance = 12; //The distance the player will teleport, feel free to change this.
                Vec3d position = master.getLookVec().mul(distance, distance, distance).add(master.getPositionVec());
                for (double i = position.getY() - 0.5; world.getBlockState(new BlockPos(position.getX(), i, position.getZ())).isSolid(); i++)
                    position = position.add(0, 0.5, 0);
                master.setPositionAndUpdate(position.getX(), position.getY(), position.getZ());
                world.playSound(null, getPosition(), SoundInit.THE_HAND_TELEPORT.get(), SoundCategory.NEUTRAL, 1, 1);
                props.setCooldown(75);
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (getMaster() != null) {
            followMaster();
            setRotationYawHead(master.rotationYawHead);
            setRotation(master.rotationYaw, master.rotationPitch);

            if (master.swingProgressInt == 0 && !attackRush)
                attackTick = 0;
            if (attackRush) {
                master.setSprinting(false);
                attackTicker++;
                if (attackTicker >= 10)
                    if (!world.isRemote) {
                        master.setSprinting(false);
                        TheHandPunchEntity theHand1 = new TheHandPunchEntity(world, this, master);
                        theHand1.randomizePositions();
                        theHand1.shoot(master, master.rotationPitch, master.rotationYaw, 0.8f, 0.5f);
                        world.addEntity(theHand1);
                        TheHandPunchEntity theHand2 = new TheHandPunchEntity(world, this, master);
                        theHand2.randomizePositions();
                        theHand2.shoot(master, master.rotationPitch, master.rotationYaw, 0.8f, 0.5f);
                        world.addEntity(theHand2);
                    }
                if (attackTicker >= 80) {
                    attackRush = false;
                    attackTicker = 0;
                }
            }
        }
    }
}
