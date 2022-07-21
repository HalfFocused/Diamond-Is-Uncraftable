package io.github.halffocused.diamond_is_uncraftable.capability;

import io.github.halffocused.diamond_is_uncraftable.DiamondIsUncraftable;
import io.github.halffocused.diamond_is_uncraftable.network.message.server.SSyncTimestopCapabilityPacket;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.halffocused.diamond_is_uncraftable.util.Util.Null;

@SuppressWarnings("unused")
public class Timestop implements ITimestop, ICapabilitySerializable<INBT> {
    @CapabilityInject(ITimestop.class)
    public static final Capability<ITimestop> TIMESTOP = Null();
    private Entity entity;
    private double posX;
    private double posY;
    private double posZ;
    private double motionX;
    private double motionY;
    private double motionZ;
    private float rotationYaw;
    private float rotationPitch;
    private float rotationYawHead;
    private float fallDistance;
    private int fuse;
    private int fire;
    private int age;
    private Map<String, Float> damage = new ConcurrentHashMap<>();
    private LazyOptional<ITimestop> holder = LazyOptional.of(() -> new Timestop(entity));

    public Timestop(@Nonnull Entity entity) {
        this.entity = entity;
    }

    public static ITimestop getCapabilityFromEntity(Entity entity) {
        return entity.getCapability(TIMESTOP).orElse(new Timestop(entity));
    }

    public static LazyOptional<ITimestop> getLazyOptional(Entity entity) {
        return entity.getCapability(TIMESTOP);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(ITimestop.class, new Capability.IStorage<ITimestop>() {
            @Override
            public INBT writeNBT(Capability<ITimestop> capability, ITimestop instance, Direction side) {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putDouble("posX", instance.getPosX());
                nbt.putDouble("posY", instance.getPosY());
                nbt.putDouble("posZ", instance.getPosZ());
                nbt.putDouble("motionX", instance.getMotionX());
                nbt.putDouble("motionY", instance.getMotionY());
                nbt.putDouble("motionZ", instance.getMotionZ());
                nbt.putFloat("rotationYaw", instance.getRotationYaw());
                nbt.putFloat("rotationPitch", instance.getRotationPitch());
                nbt.putFloat("rotationYawHead", instance.getRotationYawHead());
                nbt.putFloat("fallDistance", instance.getFallDistance());
                nbt.putInt("fuse", instance.getFuse());
                nbt.putInt("fire", instance.getFire());
                nbt.putInt("age", instance.getAge());
                ListNBT listNBT = new ListNBT();
                instance.getDamage().forEach((source, amount) -> {
                    CompoundNBT compoundNBT = new CompoundNBT();
                    compoundNBT.putString("source", source);
                    compoundNBT.putFloat("amount", amount);
                    listNBT.add(compoundNBT);
                });
                nbt.put("damage", listNBT);
                return nbt;
            }

            @Override
            public void readNBT(Capability<ITimestop> capability, ITimestop instance, Direction side, INBT nbt) {
                CompoundNBT compoundNBT = (CompoundNBT) nbt;
                instance.putPosX(compoundNBT.getDouble("posX"));
                instance.putPosY(compoundNBT.getDouble("posY"));
                instance.putPosZ(compoundNBT.getDouble("posZ"));
                instance.putMotionX(compoundNBT.getDouble("motionX"));
                instance.putMotionY(compoundNBT.getDouble("motionY"));
                instance.putMotionZ(compoundNBT.getDouble("motionZ"));
                instance.putRotationYaw(compoundNBT.getFloat("rotationYaw"));
                instance.putRotationPitch(compoundNBT.getFloat("rotationPitch"));
                instance.putRotationYawHead(compoundNBT.getFloat("rotationYawHead"));
                instance.putFallDistance(compoundNBT.getInt("fallDistance"));
                instance.putFuse(compoundNBT.getInt("fuse"));
                instance.putFire(compoundNBT.getInt("fire"));
                instance.putAge(compoundNBT.getInt("age"));
                compoundNBT.getList("damage", Constants.NBT.TAG_COMPOUND).forEach(compound -> {
                    if (compound instanceof CompoundNBT && ((CompoundNBT) compound).contains("source") && ((CompoundNBT) compound).contains("amount"))
                        instance.getDamage().put(((CompoundNBT) compound).getString("source"), ((CompoundNBT) compound).getFloat("amount") * 0.5f);
                });
            }
        }, () -> new Timestop(Null()));
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getPosZ() {
        return posZ;
    }

    @Override
    public void setPosition(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        onDataUpdated();
    }

    @Override
    public double getMotionX() {
        return motionX;
    }

    @Override
    public double getMotionY() {
        return motionY;
    }

    @Override
    public double getMotionZ() {
        return motionZ;
    }

    @Override
    public void setMotion(double motionX, double motionY, double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        onDataUpdated();
    }

    @Override
    public float getRotationYaw() {
        return rotationYaw;
    }

    @Override
    public float getRotationPitch() {
        return rotationPitch;
    }

    @Override
    public float getRotationYawHead() {
        return rotationYawHead;
    }

    @Override
    public void setRotation(float rotationYaw, float rotationPitch, float rotationYawHead) {
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.rotationYawHead = rotationYawHead;
        onDataUpdated();
    }

    @Override
    public float getFallDistance() {
        return fallDistance;
    }

    @Override
    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
        onDataUpdated();
    }

    @Override
    public int getFuse() {
        return fuse;
    }

    @Override
    public void setFuse(int fuse) {
        this.fuse = fuse;
        onDataUpdated();
    }

    @Override
    public int getFire() {
        return fire;
    }

    @Override
    public void setFire(int fire) {
        this.fire = fire;
        onDataUpdated();
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
        onDataUpdated();
    }

    @Override
    public Map<String, Float> getDamage() {
        return damage;
    }

    @Override
    public void setDamage(Map<String, Float> damage) {
        this.damage = damage;
        onDataUpdated();
    }

    @Override
    public void putPosX(double posX) {
        this.posX = posX;
    }

    @Override
    public void putPosY(double posY) {
        this.posY = posY;
    }

    @Override
    public void putPosZ(double posZ) {
        this.posZ = posZ;
    }

    @Override
    public void putMotionX(double motionX) {
        this.motionX = motionX;
    }

    @Override
    public void putMotionY(double motionY) {
        this.motionY = motionY;
    }

    @Override
    public void putMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }

    @Override
    public void putRotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
    }

    @Override
    public void putRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }

    @Override
    public void putRotationYawHead(float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }

    @Override
    public void putFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    @Override
    public void putFuse(int fuse) {
        this.fuse = fuse;
    }

    @Override
    public void putFire(int fire) {
        this.fire = fire;
    }

    @Override
    public void putAge(int age) {
        this.age = age;
    }

    @Override
    public void putDamage(Map<String, Float> damage) {
        this.damage = damage;
    }

    @Override
    public boolean isEmpty() {
        return posX == 0 &&
                posY == 0 &&
                posZ == 0 &&
                motionX == 0 &&
                motionY == 0 &&
                motionZ == 0 &&
                rotationYaw == 0 &&
                rotationPitch == 0 &&
                rotationYawHead == 0 &&
                fallDistance == 0 &&
                fuse == 0 &&
                fire == 0;
    }

    @Override
    public void onDataUpdated() {
        if (entity != null)
            if (!entity.world.isRemote)
                DiamondIsUncraftable.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SSyncTimestopCapabilityPacket(this));
    }

    @Override
    public void clear() {
        this.posX = 0;
        this.posY = 0;
        this.posZ = 0;
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.rotationYaw = 0;
        this.rotationPitch = 0;
        this.rotationYawHead = 0;
        this.fallDistance = 0;
        this.fuse = 0;
        this.fire = 0;
        this.damage = new ConcurrentHashMap<>();
        onDataUpdated();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return capability == TIMESTOP ? holder.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return TIMESTOP.getStorage().writeNBT(TIMESTOP, holder.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        TIMESTOP.getStorage().readNBT(TIMESTOP, holder.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty!")), null, nbt);
    }
}
