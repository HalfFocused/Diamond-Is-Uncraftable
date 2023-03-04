package io.github.halffocused.diamond_is_uncraftable.entity.stand.attack;

import io.github.halffocused.diamond_is_uncraftable.entity.stand.AbstractStandEntity;
import io.github.halffocused.diamond_is_uncraftable.event.custom.StandAttackEvent;
import io.github.halffocused.diamond_is_uncraftable.init.SoundInit;
import io.github.halffocused.diamond_is_uncraftable.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * This class is a mess, I can barely read it, especially the {@link #tick()} method.
 */
@SuppressWarnings("ALL")
public abstract class AbstractStandAttackEntity extends DamagingProjectileEntity implements IEntityAdditionalSpawnData {
    public double xTile, yTile, zTile, arrowShake, ticksInAir;
    public AbstractStandEntity shootingStand;
    public PlayerEntity standMaster;
    protected boolean inGround;

    public AbstractStandAttackEntity(EntityType<? extends DamagingProjectileEntity> type, World worldIn) {
        super(type, worldIn);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        setNoGravity(true);
    }

    public AbstractStandAttackEntity(EntityType<? extends DamagingProjectileEntity> type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        setPosition(x, y, z);
    }

    public AbstractStandAttackEntity(EntityType<? extends DamagingProjectileEntity> type, World worldIn, AbstractStandEntity shooter, PlayerEntity player) {
        this(type, worldIn, shooter.getPosX(), shooter.getPosY() + 0.5, shooter.getPosZ());
        shootingStand = shooter;
        standMaster = player;
        movePunchInFrontOfStand(shooter);
    }

    protected abstract void onEntityHit(EntityRayTraceResult result);

    protected abstract void onBlockHit(BlockRayTraceResult result);

    public abstract ResourceLocation getEntityTexture();

    /**
     * Defines the range of a Stand attack, {@link Override} to change, default is 1.
     */
    protected int getRange() {
        return 1;
    }

    protected boolean shouldMatchMaster() {
        return true;
    }

    protected boolean shouldBeDestroyedByBlocks(BlockState state) {
        return true;
    }

    private BlockPos getBlockPosFromNBT() {
        return new BlockPos(xTile, yTile, zTile);
    }

    public void shoot(Entity shooter, float pitch, float yaw, float velocity, float inaccuracy) {
        float x = -MathHelper.sin(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));
        float y = -MathHelper.sin(pitch * ((float) Math.PI / 180));
        float z = MathHelper.cos(yaw * ((float) Math.PI / 180)) * MathHelper.cos(pitch * ((float) Math.PI / 180));
        shoot(x, y, z, velocity, inaccuracy);
        setMotion(getMotion().add(shooter.getMotion().getX(), shooter.isOnGround() ? 0 : shooter.getMotion().getY(), shooter.getMotion().getZ()));
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vector3d vec3d = (new Vector3d(x, y, z)).normalize().add(rand.nextGaussian() * (double) 0.0075 * (double) inaccuracy, rand.nextGaussian() * (double) 0.0075 * (double) inaccuracy, rand.nextGaussian() * (double) 0.0075 * (double) inaccuracy).scale(velocity);
        setMotion(vec3d);
        float f = MathHelper.sqrt(horizontalMag(vec3d));
        rotationYaw = (float) (MathHelper.atan2(vec3d.getX(), vec3d.getZ()) * (double) (180 / (float) Math.PI));
        rotationPitch = (float) (MathHelper.atan2(vec3d.getY(), f) * (double) (180 / (float) Math.PI));
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
    }

    public void randomizePositions() {
        if (shootingStand == null) return;
        Vector3d random = new Vector3d(rand.nextDouble() * 2 - 1, rand.nextDouble() * 2 - 1, rand.nextDouble() * 2 - 1);
        Vector3d position = getPositionVec().add(random).add(standMaster.getLookVec().mul(1.5, 0.2, 1.5));
        setPosition(position.getX(), position.getY(), position.getZ());
        setRotation(rotationYaw, rotationPitch);
    }

    public void movePunchInFrontOfStand(AbstractStandEntity stand) {
        if (stand.getMaster() == null) return;
        Vector3d position = getPositionVec().add(stand.getMaster().getLookVec().mul(0.5, 0.1, 0.5));
        setPosition(position.getX(), position.getY(), position.getZ());
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        setMotion(x, y, z);
        if (prevRotationPitch == 0 && prevRotationYaw == 0) {
            rotationPitch = (float) (MathHelper.atan2(y, (double) MathHelper.sqrt(x * x + z * z)) * (double) (180 / (float) Math.PI));
            rotationYaw = (float) (MathHelper.atan2(x, z) * (double) (180 / (float) Math.PI));
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            setLocationAndAngles(getPosX(), getPosY(), getPosZ(), rotationYaw, rotationPitch);
            inGround = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (standMaster != null && shouldMatchMaster())
            setRotation(standMaster.rotationYaw, standMaster.rotationPitch);
        if (shootingStand == null && !world.isRemote)
            remove();
        BlockPos blockPos = new BlockPos(xTile, yTile, zTile);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getMaterial() != Material.AIR && !blockState.getCollisionShape(world, blockPos).isEmpty())
            if (blockState.getCollisionShape(world, blockPos).getBoundingBox().offset(blockPos).contains(getPositionVec()))
                inGround = true;
        if (arrowShake > 0)
            arrowShake--;

        if (!inGround) {
            ticksInAir++;
            if (ticksInAir > getRange() && !world.isRemote)
                remove();
            BlockRayTraceResult raytraceresult = world.rayTraceBlocks(new RayTraceContext(getPositionVec(), getPositionVec().add(getMotion()), BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, this));
            EntityRayTraceResult entityRayTraceResult = null;
            EntityRayTraceResult result = rayTraceEntities(getPositionVec(), getPositionVec().add(getMotion()));
            if (result != null) {
                entityRayTraceResult = result;
                if (raytraceresult != null && entityRayTraceResult.getEntity() instanceof AbstractStandAttackEntity) {
                    AbstractStandAttackEntity punch = (AbstractStandAttackEntity) entityRayTraceResult.getEntity();
                    if (punch.shootingStand == shootingStand)
                        raytraceresult = null;
                }
                if (raytraceresult != null && entityRayTraceResult.getEntity() instanceof PlayerEntity) {
                    PlayerEntity playerEntity = (PlayerEntity) entityRayTraceResult.getEntity();
                    if (playerEntity.equals(standMaster))
                        raytraceresult = null;
                    else
                        entityRayTraceResult = new EntityRayTraceResult(playerEntity);
                }
                if (entityRayTraceResult != null && entityRayTraceResult.getEntity() instanceof AbstractStandEntity)
                    if (entityRayTraceResult.getEntity() == shootingStand)
                        entityRayTraceResult = null;
            }
            if (entityRayTraceResult != null && !ForgeEventFactory.onProjectileImpact(this, entityRayTraceResult)) {
                if (entityRayTraceResult.getEntity() instanceof PlayerEntity && entityRayTraceResult.getEntity().equals(standMaster))
                    return;
                onHit(entityRayTraceResult);
            }
            if (raytraceresult != null && !ForgeEventFactory.onProjectileImpact(this, raytraceresult))
                onHit(raytraceresult);
            setPosition(getPosX() + getMotion().getX(), getPosY() + getMotion().getY(), getPosZ() + getMotion().getZ());
            float f1 = 0.99f;
            if (isInWater())
                for (int i = 0; i < 4; i++)
                    world.addParticle(ParticleTypes.BUBBLE, getPosX() - getMotion().getX() * 0.25, getPosY() - getMotion().getY() * 0.25, getPosZ() - getMotion().getZ() * 0.25, getMotion().getX(), getMotion().getY(), getMotion().getZ());
            if (isWet())
                extinguish();

            setMotion(getMotion().mul(f1, f1, f1));
            setPosition(getPosX(), getPosY(), getPosZ());
            doBlockCollisions();
        }
    }

    protected void onHit(RayTraceResult result) {
        if (result.getType() != RayTraceResult.Type.MISS)
            if (MinecraftForge.EVENT_BUS.post(new StandAttackEvent(
                    this,
                    result,
                    result.getType() == RayTraceResult.Type.ENTITY ? ((EntityRayTraceResult) result).getEntity() : null,
                    result.getType()
            ))) return;
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (!entity.equals(standMaster) && !entity.equals(this)) {
                if (isBurning() && !(entity instanceof EndermanEntity))
                    entity.setFire(5);
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (!world.isRemote) {
                        world.addParticle(ParticleTypes.EXPLOSION, getPosX(), getPosY(), getPosZ(), 1, 0, 0);
                        if (!world.isRemote) {
                            if (MinecraftForge.EVENT_BUS.post(new StandAttackEvent.EntityHit(this, result, entity)) && shouldRemoveOnHitEntity()) {
                                remove();
                                return;
                            }
                            onEntityHit((EntityRayTraceResult) result);
                        }
                    }
                    if (standMaster != null && !livingEntity.equals(standMaster) && livingEntity instanceof PlayerEntity && standMaster instanceof ServerPlayerEntity)
                        ((ServerPlayerEntity) standMaster).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.HIT_PLAYER_ARROW, 0));
                }
                if (!(entity instanceof EndermanEntity) && shouldRemoveOnHitEntity())
                    remove();
            }
        } else if (result.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockRayTraceResult) result).getPos();
            BlockState state = world.getBlockState(blockPos);
            if (state == null)
                state = Blocks.AIR.getDefaultState();
            xTile = blockPos.getX();
            yTile = blockPos.getY();
            zTile = blockPos.getZ();
            if(world.getBlockState(blockPos).isSolid()) {
            if (shouldBeDestroyedByBlocks(state))
                setMotion((float) (result.getHitVec().getX() - getPosX()), (float) (result.getHitVec().getY() - getPosY()), (float) (result.getHitVec().getZ() - getPosZ()));
                float f2 = MathHelper.sqrt(getMotion().getX() * getMotion().getX() + getMotion().getY() * getMotion().getY() + getMotion().getZ() * getMotion().getZ());
                setPosition(getPosX() - getMotion().getX() / f2 * 0.05000000074505806, getPosY() - getMotion().getY() / f2 * 0.05000000074505806, getPosZ() - getMotion().getZ() / f2 * 0.05000000074505806);
                inGround = true;
                arrowShake = 7;
                if (!world.isRemote) {
                    if (MinecraftForge.EVENT_BUS.post(new StandAttackEvent.BlockHit(this, result, null))) {
                        remove();
                        return;
                    }
                    if (MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(world, blockPos, state, standMaster))) {
                        remove();
                        return;
                    }
                    onBlockHit((BlockRayTraceResult) result);
                    if (state.getMaterial() != Material.AIR) {
                        state.getBlock().onProjectileCollision(world, state, (BlockRayTraceResult) result, this);
                        if (shouldBeDestroyedByBlocks(state))
                            remove();
                    }
                }
            }
        }
    }

    @Override
    public void move(MoverType type, Vector3d pos) {
        super.move(type, pos);
        if (inGround) {
            xTile = MathHelper.floor(getPosX());
            yTile = MathHelper.floor(getPosY());
            zTile = MathHelper.floor(getPosZ());
        }
    }

    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return ProjectileHelper.rayTraceEntities(world, this, startVec, endVec, getBoundingBox().expand(getMotion()).grow(1), (entity) ->
                !entity.isSpectator() && entity.isAlive() && entity.canBeCollidedWith() && (!entity.equals(shootingStand) || ticksInAir > getRange()) && !entity.equals(standMaster));
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (!world.isRemote && inGround && arrowShake <= 0 && !entityIn.equals(standMaster)) remove();
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        if (!entityIn.equals(shootingStand) && !entityIn.equals(standMaster))
            super.applyEntityCollision(entityIn);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(shootingStand == null ? -1 : shootingStand.getEntityId());
        buffer.writeInt(standMaster == null ? -1 : standMaster.getEntityId());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        Entity stand = world.getEntityByID(additionalData.readInt());
        Entity master = world.getEntityByID(additionalData.readInt());
        if (stand instanceof AbstractStandEntity)
            shootingStand = (AbstractStandEntity) stand;
        if (master instanceof PlayerEntity)
            standMaster = (PlayerEntity) master;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        xTile = compound.getDouble("xTile");
        yTile = compound.getDouble("yTile");
        zTile = compound.getDouble("zTile");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putDouble("xTile", xTile);
        compound.putDouble("yTile", yTile);
        compound.putDouble("zTile", zTile);
    }

    @Override
    protected void registerData() {
    }

    protected boolean shouldRemoveOnHitEntity(){
        return true;
    }

    protected SoundEvent getHitEntitySound() {
        return SoundInit.PUNCH_1.get();
    }

    protected boolean isFireballFiery() {
        return false;
    }

    protected float getMotionFactor() {
        return 1F;
    }
}