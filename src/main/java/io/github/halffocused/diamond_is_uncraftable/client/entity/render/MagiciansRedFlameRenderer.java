package io.github.halffocused.diamond_is_uncraftable.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.client.entity.model.MagiciansRedFlameModel;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.AbstractStandEntity;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.attack.MagiciansRedFlameEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

public class MagiciansRedFlameRenderer extends StandAttackRenderer<MagiciansRedFlameEntity> {
    public MagiciansRedFlameRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(@Nonnull MagiciansRedFlameEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (!(Minecraft.getInstance().renderViewEntity instanceof PlayerEntity) && !(Minecraft.getInstance().renderViewEntity instanceof AbstractStandEntity))
            return;
        MagiciansRedFlameModel flame = new MagiciansRedFlameModel();
        if (entityIn.isExplosive()) {
            flame.Flames.showModel = false;
            flame.Flames2.showModel = true;
        } else {
            flame.Flames.showModel = true;
            flame.Flames2.showModel = false;
        }
        if (Minecraft.getInstance().renderViewEntity instanceof AbstractStandEntity)
            render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, flame);
        else Stand.getLazyOptional((PlayerEntity) Minecraft.getInstance().renderViewEntity).ifPresent(props -> {
            if (props.getStandID() != 0)
                super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, flame);
        });
    }
}

