package io.github.halffocused.diamond_is_uncraftable.entity.stand;

import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.attack.MadeInHeavenPunchEntity;
import io.github.halffocused.diamond_is_uncraftable.event.EventD4CTeleportProcessor;
import io.github.halffocused.diamond_is_uncraftable.init.DimensionInit;
import io.github.halffocused.diamond_is_uncraftable.init.EntityInit;
import io.github.halffocused.diamond_is_uncraftable.util.Util;
import io.github.halffocused.diamond_is_uncraftable.util.movesets.HoveringMoveHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

@SuppressWarnings("ConstantConditions")
public class MadeInHeavenEntity extends AbstractStandEntity {
    public MadeInHeavenEntity(EntityType<? extends AbstractStandEntity> type, World world) {
        super(type, world);
    }

    public HoveringMoveHandler getController(){
        return null;
    }

    public void teleport() {
        if (getMaster() == null) return;
        Stand.getLazyOptional(master).ifPresent(props -> {
            if (props.getCooldown() == 0) {
                Vec3d position = master.getLookVec().mul(70, 1, 70).add(master.getPositionVec());
                for (double i = position.getY() - 0.5; world.getBlockState(new BlockPos(position.getX(), i, position.getZ())).isSolid(); i++)
                    position = position.add(0, 0.5, 0);
                master.setPositionAndUpdate(position.getX(), position.getY(), position.getZ());
                world.playSound(null, master.getPosition(), SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.HOSTILE, 1, 1);
                if (props.getTimeLeft() > -1400)
                    props.setCooldown(80);
            }
        });
    }

    public void dodgeAttacks() {
        if (getMaster() == null) return;
        Stand.getLazyOptional(master).ifPresent(stand -> {
            if (stand.getCooldown() == 0 && stand.getInvulnerableTicks() == 0)
                stand.setInvulnerableTicks(200);
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (getMaster() != null) {
            Stand.getLazyOptional(master).ifPresent(props -> {
                props.setAbilityActive(props.getStandOn() || props.getCooldown() == 0);

                if (props.getAbilityActive() && props.getCooldown() > 0)
                    props.setCooldown(props.getCooldown() - 1);

                if (props.getAct() == 1) {
                    remove();
                    CMoonEntity cMoon = new CMoonEntity(EntityInit.CMOON.get(), world);
                    Vec3d position = master.getLookVec().mul(0.5, 1, 0.5).add(master.getPositionVec()).add(0, 0.5, 0);
                    cMoon.setLocationAndAngles(position.getX(), position.getY(), position.getZ(), master.rotationYaw, master.rotationPitch);
                    cMoon.setMaster(master);
                    cMoon.setMasterUUID(master.getUniqueID());
                    world.addEntity(cMoon);
                }

                if (props.getTimeLeft() > -2600)
                    props.setTimeLeft(props.getTimeLeft() - 1);

                if (props.getTimeLeft() == -1400)
                    master.sendStatusMessage(new StringTextComponent("\"Heaven\" has begun!"), true);

                if (props.getTimeLeft() < -1400) {
                    world.setDayTime(world.getDayTime() + 50);
                    master.addPotionEffect(new EffectInstance(Effects.SPEED, 40, 39));
                    LightningBoltEntity lightning = new LightningBoltEntity(world, getPosX() + rand.nextInt(50), getPosY(), getPosZ() + rand.nextInt(50), false);
                    lightning.setSilent(true);
                    if (!world.isRemote) {
                        ((ServerWorld) world).addLightningBolt(lightning);
                        world.addEntity(lightning);
                    }
                    world.getGameRules().write().putInt(GameRules.RANDOM_TICK_SPEED.getName(), world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED) + 5);
                }

                if (props.getTimeLeft() < -1800) {
                    world.setDayTime(world.getDayTime() + 80);
                    master.addPotionEffect(new EffectInstance(Effects.SPEED, 40, 99));
                }

                if (props.getTimeLeft() < -2200) {
                    master.addPotionEffect(new EffectInstance(Effects.SPEED, 40, 255));
                    master.addPotionEffect(new EffectInstance(Effects.LEVITATION, 40, 2));
                    if (!world.isRemote)
                        world.createExplosion(this, getPosX() + rand.nextInt(100), getPosY() - fallDistance, getPosZ() + rand.nextInt(100), 4.0f, Explosion.Mode.DESTROY);
                    world.setDayTime(world.getDayTime() + 500);
                }

                if (props.getTimeLeft() <= -2600) {
                    world.getPlayers().forEach(entity -> Stand.getLazyOptional(entity).ifPresent(prps -> {
                        if (prps.getStandID() != Util.StandID.GER) {
                            entity.inventory.clear();
                            entity.getInventoryEnderChest().clear();
                            EventD4CTeleportProcessor.madeInHeaven.add(entity);
                            entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 40, 99));
                            entity.fallDistance = 0;
                            entity.setSpawnDimenion(DimensionType.byName(DimensionInit.MADE_IN_HEAVEN_DIMENSION_TYPE));
                            prps.removeStand(false);
                            entity.setInvulnerable(false);
                        }
                    }));
                }
                master.addPotionEffect(new EffectInstance(Effects.SPEED, 40, 19));
                master.setHealth(20);
                master.getFoodStats().addStats(20, 20);

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
                            MadeInHeavenPunchEntity madeInHeaven1 = new MadeInHeavenPunchEntity(world, this, master);
                            madeInHeaven1.randomizePositions();
                            madeInHeaven1.shoot(master, master.rotationPitch, master.rotationYaw, props.getTimeLeft() > -1400 ? 10 : 100, 0.1f);
                            world.addEntity(madeInHeaven1);
                            MadeInHeavenPunchEntity madeInHeaven2 = new MadeInHeavenPunchEntity(world, this, master);
                            madeInHeaven2.randomizePositions();
                            madeInHeaven2.shoot(master, master.rotationPitch, master.rotationYaw, props.getTimeLeft() > -1400 ? 10 : 100, 0.1f);
                            world.addEntity(madeInHeaven2);
                        }
                    if (attackTicker >= 80) {
                        attackRush = false;
                        attackTicker = 0;
                    }
                }
            });
        }
    }
}
