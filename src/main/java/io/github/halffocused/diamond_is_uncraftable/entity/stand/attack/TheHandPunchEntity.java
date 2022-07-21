package io.github.halffocused.diamond_is_uncraftable.entity.stand.attack;

import io.github.halffocused.diamond_is_uncraftable.entity.stand.AbstractStandEntity;
import io.github.halffocused.diamond_is_uncraftable.init.EntityInit;
import io.github.halffocused.diamond_is_uncraftable.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class TheHandPunchEntity extends AbstractStandAttackEntity {
    public TheHandPunchEntity(EntityType<? extends AbstractStandAttackEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public TheHandPunchEntity(World worldIn, AbstractStandEntity shooter, PlayerEntity player) {
        super(EntityInit.THE_HAND_PUNCH.get(), worldIn, shooter, player);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        if (world.rand.nextBoolean())
            entity.remove();
        else {
            entity.attackEntityFrom(DamageSource.causeMobDamage(standMaster), 1);
            entity.hurtResistantTime = 0;
        }
    }

    @Override
    protected void onBlockHit(BlockRayTraceResult result) {
        world.removeBlock(result.getPos(), false);
    }

    @Override
    public ResourceLocation getEntityTexture() {
        return Util.ResourceLocations.THE_HAND_PUNCH;
    }
}
