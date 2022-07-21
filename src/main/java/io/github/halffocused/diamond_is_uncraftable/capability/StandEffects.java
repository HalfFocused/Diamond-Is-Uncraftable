package io.github.halffocused.diamond_is_uncraftable.capability;

import io.github.halffocused.diamond_is_uncraftable.DiamondIsUncraftable;
import io.github.halffocused.diamond_is_uncraftable.network.message.server.SSyncStandEffectsCapabilityPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
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
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.halffocused.diamond_is_uncraftable.util.Util.Null;

public class StandEffects implements ICapabilitySerializable<INBT> {
    @CapabilityInject(StandEffects.class)
    public static final Capability<StandEffects> STAND_EFFECTS = Null();
    private final Entity entity;
    private boolean aging;
    private Vec3d motion = Vec3d.ZERO;
    private boolean bomb;
    private UUID standUser = UUID.fromString("c9362041-f5e8-447c-80a8-9db27a2646bb");
    private boolean rotating;
    private byte soundEffect;
    private boolean threeFreeze;
    private double timeNearFlames;
    private BlockPos bitesTheDustPos = BlockPos.ZERO;
    private long timeOfDeath = -1;
    private Map<ChunkPos, Map<BlockPos, BlockState>> destroyedBlocks = new ConcurrentHashMap<>();
    private boolean shouldBeRemoved;
    private int hitstun;
    private Map<ChunkPos, ArrayBlockingQueue<BlockPos>> alteredTileEntities = new ConcurrentHashMap<>();
    private LazyOptional<StandEffects> holder = LazyOptional.of(() -> new StandEffects(getEntity()));

    public StandEffects(Entity entity) {
        this.entity = entity;
    }

    public static LazyOptional<StandEffects> getLazyOptional(Entity entity) {
        return entity.getCapability(STAND_EFFECTS);
    }

    public static StandEffects getCapabilityFromEntity(Entity entity) {
        return entity.getCapability(STAND_EFFECTS).orElse(new StandEffects(entity));
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(StandEffects.class, new Capability.IStorage<StandEffects>() {
            @Override
            public INBT writeNBT(Capability<StandEffects> capability, StandEffects instance, Direction side) {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putBoolean("aging", instance.aging);
                nbt.putDouble("motionX", instance.motion.getX());
                nbt.putDouble("motionY", instance.motion.getY());
                nbt.putDouble("motionZ", instance.motion.getZ());
                nbt.putBoolean("bomb", instance.bomb);
                nbt.putUniqueId("standUser", instance.standUser);
                nbt.putBoolean("rotating", instance.rotating);
                nbt.putByte("soundEffect", instance.soundEffect);
                nbt.putBoolean("threeFreeze", instance.threeFreeze);
                nbt.putDouble("timeNearFlames", instance.timeNearFlames);
                nbt.putDouble("bitesTheDustX", instance.bitesTheDustPos.getX());
                nbt.putDouble("bitesTheDustY", instance.bitesTheDustPos.getY());
                nbt.putDouble("bitesTheDustZ", instance.bitesTheDustPos.getZ());
                nbt.putInt("hitstun", instance.hitstun);
                nbt.putLong("timeOfDeath", instance.timeOfDeath);
                nbt.putBoolean("shouldBeRemoved", instance.shouldBeRemoved);
                ListNBT destroyedBlocks = new ListNBT();
                instance.destroyedBlocks.forEach((pos, list) -> {
                    CompoundNBT compoundNBT = new CompoundNBT();
                    compoundNBT.putInt("chunkPosX", pos.x);
                    compoundNBT.putInt("chunkPosZ", pos.z);
                    ListNBT listNBT = new ListNBT();
                    list.forEach((blockPos, blockState) -> {
                        CompoundNBT compound = new CompoundNBT();
                        compound.putDouble("blockPosX", blockPos.getX());
                        compound.putDouble("blockPosY", blockPos.getY());
                        compound.putDouble("blockPosZ", blockPos.getZ());
                        compound.putInt("blockState", Block.getStateId(blockState));
                        listNBT.add(compound);
                    });
                    compoundNBT.put("blockPosList", listNBT);
                    destroyedBlocks.add(compoundNBT);
                });
                nbt.put("destroyedBlocks", destroyedBlocks);
                ListNBT alteredTileEntities = new ListNBT();
                instance.alteredTileEntities.forEach((pos, blockPosList) -> {
                    CompoundNBT compoundNBT = new CompoundNBT();
                    compoundNBT.putInt("chunkPosX", pos.x);
                    compoundNBT.putInt("chunkPosZ", pos.z);
                    ListNBT listNBT = new ListNBT();
                    blockPosList.forEach(blockPos -> {
                        CompoundNBT compound = new CompoundNBT();
                        compound.putDouble("blockPosX", blockPos.getX());
                        compound.putDouble("blockPosY", blockPos.getY());
                        compound.putDouble("blockPosZ", blockPos.getZ());
                        listNBT.add(compound);
                    });
                    compoundNBT.put("blockPosList", listNBT);
                    alteredTileEntities.add(compoundNBT);
                });
                nbt.put("alteredTileEntities", alteredTileEntities);
                return nbt;
            }

            @Override
            public void readNBT(Capability<StandEffects> capability, StandEffects instance, Direction side, INBT nbt) {
                CompoundNBT compoundNBT = (CompoundNBT) nbt;
                instance.aging = compoundNBT.getBoolean("aging");
                instance.motion = new Vec3d(compoundNBT.getDouble("motionX"), compoundNBT.getDouble("motionY"), compoundNBT.getDouble("motionZ"));
                instance.bomb = compoundNBT.getBoolean("bomb");
                instance.standUser = compoundNBT.getUniqueId("standUser");
                instance.rotating = compoundNBT.getBoolean("rotating");
                instance.soundEffect = compoundNBT.getByte("soundEffect");
                instance.threeFreeze = compoundNBT.getBoolean("threeFreeze");
                instance.timeNearFlames = compoundNBT.getDouble("timeNearFlames");
                instance.hitstun = compoundNBT.getInt("hitstun");
                instance.bitesTheDustPos = new BlockPos(compoundNBT.getDouble("bitesTheDustX"), compoundNBT.getDouble("bitesTheDustY"), compoundNBT.getDouble("bitesTheDustZ"));
                instance.timeOfDeath = compoundNBT.getLong("timeOfDeath");
                instance.shouldBeRemoved = compoundNBT.getBoolean("shouldBeRemoved");
                compoundNBT.getList("destroyedBlocks", Constants.NBT.TAG_COMPOUND).forEach(compound -> {
                    if (compound instanceof CompoundNBT && ((CompoundNBT) compound).contains("chunkPosX")) {
                        Map<BlockPos, BlockState> map = new ConcurrentHashMap<>();
                        ((CompoundNBT) compound).getList("blockPosList", Constants.NBT.TAG_COMPOUND).forEach(inbt -> {
                            if (inbt instanceof CompoundNBT && ((CompoundNBT) inbt).contains("blockPosX"))
                                map.put(new BlockPos(((CompoundNBT) compound).getDouble("blockPosX"), ((CompoundNBT) compound).getDouble("blockPosY"), ((CompoundNBT) compound).getDouble("blockPosZ")), Block.getStateById(compoundNBT.getInt("blockState")));
                        });
                        instance.destroyedBlocks.put(new ChunkPos(((CompoundNBT) compound).getInt("chunkPosX"), ((CompoundNBT) compound).getInt("chunkPosX")), map);
                    }
                });
                compoundNBT.getList("alteredTileEntities", Constants.NBT.TAG_COMPOUND).forEach(compound -> {
                    if (compound instanceof CompoundNBT && ((CompoundNBT) compound).contains("chunkPosX")) {
                        ArrayBlockingQueue<BlockPos> blockPosList = new ArrayBlockingQueue<>(1000);
                        ((CompoundNBT) compound).getList("blockPosList", Constants.NBT.TAG_COMPOUND).forEach(inbt -> {
                            if (inbt instanceof CompoundNBT && ((CompoundNBT) inbt).contains("blockPosX"))
                                blockPosList.add(new BlockPos(((CompoundNBT) compound).getDouble("blockPosX"), ((CompoundNBT) compound).getDouble("blockPosY"), ((CompoundNBT) compound).getDouble("blockPosZ")));
                        });
                        instance.alteredTileEntities.put(new ChunkPos(((CompoundNBT) compound).getInt("chunkPosX"), ((CompoundNBT) compound).getInt("chunkPosX")), blockPosList);
                    }
                });
            }
        }, () -> new StandEffects(Null()));
    }

    public boolean isShouldBeRemoved() {
        return shouldBeRemoved;
    }

    public void setShouldBeRemoved(boolean shouldBeRemoved) {
        this.shouldBeRemoved = shouldBeRemoved;
        onDataUpdated();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == STAND_EFFECTS ? holder.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return STAND_EFFECTS.getStorage().writeNBT(STAND_EFFECTS, holder.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        STAND_EFFECTS.getStorage().readNBT(STAND_EFFECTS, holder.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty!")), null, nbt);
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isAging() {
        return aging;
    }

    public void setAging(boolean aging) {
        this.aging = aging;
        onDataUpdated();
    }

    public Vec3d getMotion() {
        return motion;
    }

    public void setMotion(Vec3d motion) {
        this.motion = motion;
        onDataUpdated();
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public UUID getStandUser() {
        return standUser;
    }

    public void setStandUser(UUID standUser) {
        this.standUser = standUser;
        onDataUpdated();
    }



    public boolean isRotating() {
        return rotating;
    }

    public void setRotating(boolean rotating) {
        this.rotating = rotating;
        onDataUpdated();
    }

    public byte getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(byte soundEffect) {
        this.soundEffect = soundEffect;
        onDataUpdated();
    }

    public boolean isThreeFreeze() {
        return threeFreeze;
    }

    public void setThreeFreeze(boolean threeFreeze) {
        this.threeFreeze = threeFreeze;
        onDataUpdated();
    }

    public double getTimeNearFlames() {
        return timeNearFlames;
    }

    public void setTimeNearFlames(double timeNearFlames) {
        this.timeNearFlames = timeNearFlames;
        onDataUpdated();
    }

    public BlockPos getBitesTheDustPos() {
        return bitesTheDustPos;
    }

    public void setBitesTheDustPos(BlockPos bitesTheDustPos) {
        this.bitesTheDustPos = bitesTheDustPos;
        onDataUpdated();
    }

    public long getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setTimeOfDeath(long timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
        onDataUpdated();
    }

    public Map<ChunkPos, Map<BlockPos, BlockState>> getDestroyedBlocks() {
        return destroyedBlocks;
    }

    public void putDestroyedBlock(@Nonnull ChunkPos pos, @Nonnull BlockPos blockPos, @Nonnull BlockState state) {
        if (!destroyedBlocks.containsKey(pos)) {
            Map<BlockPos, BlockState> map = new ConcurrentHashMap<>();
            map.put(blockPos, state);
            destroyedBlocks.put(pos, map);
        } else
            destroyedBlocks.get(pos).put(blockPos, state);
        onDataUpdated();
    }

    public void removeDestroyedBlock(@Nonnull ChunkPos pos, @Nonnull BlockPos blockPos) {
        destroyedBlocks.get(pos).remove(blockPos);
        onDataUpdated();
    }

    public Map<ChunkPos, ArrayBlockingQueue<BlockPos>> getAlteredTileEntities() {
        return alteredTileEntities;
    }

    public void putAlteredTileEntity(@Nonnull ChunkPos pos, @Nonnull BlockPos blockPos) {
        if (!alteredTileEntities.containsKey(pos)) {
            ArrayBlockingQueue<BlockPos> list = new ArrayBlockingQueue<>(1000);
            list.add(blockPos);
            alteredTileEntities.put(pos, list);
        } else
            alteredTileEntities.get(pos).add(blockPos);
        onDataUpdated();
    }

    public void onDataUpdated() {
        if (!entity.world.isRemote)
            DiamondIsUncraftable.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new SSyncStandEffectsCapabilityPacket(this));
    }
}
