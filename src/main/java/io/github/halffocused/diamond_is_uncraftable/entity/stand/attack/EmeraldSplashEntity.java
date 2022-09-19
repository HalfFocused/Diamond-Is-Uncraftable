package io.github.halffocused.diamond_is_uncraftable.entity.stand.attack;

import io.github.halffocused.diamond_is_uncraftable.entity.stand.AbstractStandEntity;
import io.github.halffocused.diamond_is_uncraftable.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

import static io.github.halffocused.diamond_is_uncraftable.util.Util.Null;

public class EmeraldSplashEntity extends AbstractStandAttackEntity {
    public EmeraldSplashEntity(EntityType<? extends AbstractStandAttackEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EmeraldSplashEntity(World worldIn, AbstractStandEntity shooter, PlayerEntity player) {
        super(EntityInit.EMERALD_SPLASH.get(), worldIn, shooter, player);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        entity.attackEntityFrom(DamageSource.causeMobDamage(standMaster), 1.5f);
        entity.hurtResistantTime = 0;
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

    @Override
    protected void onBlockHit(BlockRayTraceResult result) {
        BlockPos pos = result.getPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlockHardness(world, pos) != -1 && state.getBlockHardness(world, pos) < 3) {
            world.removeBlock(pos, false);
            if (world.rand.nextBoolean())
                state.getBlock().harvestBlock(world, standMaster, pos, state, null, standMaster.getActiveItemStack());
        }
    }

    @Override
    public ResourceLocation getEntityTexture() {
        return Null(); //This renders as an emerald, so this is unused.
    }

    @Override
    protected int getRange() {
        return 3;
    }
}
