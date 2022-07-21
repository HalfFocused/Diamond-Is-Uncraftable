package io.github.halffocused.diamond_is_uncraftable.client.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.client.entity.model.AbstractStandAttackModel;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.AbstractStandEntity;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.attack.AbstractStandAttackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class StandAttackRenderer<T extends AbstractStandAttackEntity> extends EntityRenderer<T> {
    AbstractStandAttackModel<T> model;

    public StandAttackRenderer(EntityRendererManager manager) {
        super(manager);
    }

    public StandAttackRenderer(EntityRendererManager manager, AbstractStandAttackModel<T> model) {
        super(manager);
        this.model = model;
    }


    protected void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EntityModel<T> model) {
        renderManager.textureManager.bindTexture(getEntityTexture(entityIn));
        matrixStackIn.push();
        matrixStackIn.translate((float) entityIn.getPosX(), (float) entityIn.getPosY(), (float) entityIn.getPosZ());
        matrixStackIn.scale(2, 2, 2);
        matrixStackIn.pop();
        model.setRotationAngles(entityIn, partialTicks, 0, partialTicks, entityIn.rotationYaw, entityIn.rotationPitch); //Rotates the punch to face away from the Stand user,
        model.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySmoothCutout(getEntityTexture(entityIn))), packedLightIn, 0, 0, 0, 0, 0);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (Minecraft.getInstance().renderViewEntity instanceof AbstractStandEntity)
            render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, model);
        else if (Minecraft.getInstance().renderViewEntity instanceof PlayerEntity)
            Stand.getLazyOptional((PlayerEntity) Minecraft.getInstance().renderViewEntity).ifPresent(props -> {
                if (props.getStandID() != 0)
                    render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, model);
            });
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return entity.getEntityTexture();
    }
}
