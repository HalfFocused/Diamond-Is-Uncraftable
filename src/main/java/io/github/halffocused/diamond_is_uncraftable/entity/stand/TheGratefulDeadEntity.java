package io.github.halffocused.diamond_is_uncraftable.entity.stand;

import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.capability.StandEffects;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.attack.TheGratefulDeadPunchEntity;
import io.github.halffocused.diamond_is_uncraftable.init.EffectInit;
import io.github.halffocused.diamond_is_uncraftable.util.movesets.HoveringMoveHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class TheGratefulDeadEntity extends AbstractStandEntity {
    private int cropTicks;

    public TheGratefulDeadEntity(EntityType<? extends AbstractStandEntity> type, World world) {
        super(type, world);
    }

    public HoveringMoveHandler getController(){
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (getMaster() != null) {
            Stand.getLazyOptional(master).ifPresent(props -> {
                props.setAbilityActive(props.getTimeLeft() > 601 && props.getCooldown() == 0 && props.getAbility());
                ability = props.getTimeLeft() > 600 && props.getAbility();
                if (ability)
                    props.setTimeLeft(props.getTimeLeft() - 1);
                if (props.getAbilityActive()) {
                    cropTicks++;
                    if (cropTicks % 20 == 0)
                        BlockPos.getAllInBox(getPosition().add(10, 10, 10), getPosition().add(-10, -10, -10))
                                .filter(pos -> world.getBlockState(pos).getBlock() instanceof CropsBlock || world.getBlockState(pos).getBlock() instanceof StemBlock)
                                .forEach(pos -> {
                                    BlockState state = world.getBlockState(pos);
                                    if (state.getBlock() instanceof CropsBlock) {
                                        if (state.get(((CropsBlock) state.getBlock()).getAgeProperty()) < ((CropsBlock) state.getBlock()).getMaxAge())
                                            world.setBlockState(pos, ((CropsBlock) state.getBlock()).withAge(state.get(((CropsBlock) state.getBlock()).getAgeProperty()) + 1), 2);
                                    } else if (state.get(StemBlock.AGE) < 7)
                                        world.setBlockState(pos, state.with(StemBlock.AGE, state.get(StemBlock.AGE) + 1), 2);
                                });
                    for (int i = 0; i < 10; i++)
                        world.addOptionalParticle(
                                ParticleTypes.DRAGON_BREATH,
                                getPosX() + (world.rand.nextBoolean() ? rand.nextDouble() : -rand.nextDouble()),
                                getPosY() + world.rand.nextDouble(),
                                getPosZ() + (world.rand.nextBoolean() ? rand.nextDouble() : -rand.nextDouble()),
                                0, 0.3 + (rand.nextBoolean() ? 0.1 : -0.1), 0);
                } else
                    cropTicks = 0;

                if (!props.getAbilityActive() && !world.isRemote)
                    getServer().getWorld(dimension).getEntities()
                            .filter(entity -> !entity.equals(this) && !entity.equals(master))
                            .filter(entity -> entity instanceof LivingEntity)
                            .forEach(entity -> StandEffects.getLazyOptional(entity).ifPresent(props2 -> props2.setAging(false)));

                if (props.getTimeLeft() == 601)
                    props.setCooldown(200);
            });

            if (ability && !world.isRemote)
                getServer().getWorld(dimension).getEntities()
                        .filter(entity -> !entity.equals(this) && !entity.equals(master))
                        .filter(entity -> entity instanceof LivingEntity)
                        .filter(entity -> entity.getDistance(this) < 20)
                        .forEach(entity -> {
                            StandEffects.getLazyOptional(entity).ifPresent(props -> props.setAging(true));
                            ((LivingEntity) entity).addPotionEffect(new EffectInstance(EffectInit.AGING.get(), 100, 0));
                        });

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
                        TheGratefulDeadPunchEntity theGratefulDead1 = new TheGratefulDeadPunchEntity(world, this, master);
                        theGratefulDead1.randomizePositions();
                        theGratefulDead1.shoot(master, master.rotationPitch, master.rotationYaw, 2, 0.25f);
                        world.addEntity(theGratefulDead1);
                        TheGratefulDeadPunchEntity theGratefulDead2 = new TheGratefulDeadPunchEntity(world, this, master);
                        theGratefulDead2.randomizePositions();
                        theGratefulDead2.shoot(master, master.rotationPitch, master.rotationYaw, 2, 0.25f);
                        world.addEntity(theGratefulDead2);
                    }
                if (attackTicker >= 80) {
                    attackRush = false;
                    attackTicker = 0;
                }
            }
        }
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if (!world.isRemote)
            getServer().getWorld(dimension).getEntities()
                    .filter(entity -> !entity.equals(this) && !entity.equals(master))
                    .filter(entity -> entity instanceof LivingEntity)
                    .forEach(entity -> StandEffects.getLazyOptional(entity).ifPresent(props -> props.setAging(false)));
    }
}
