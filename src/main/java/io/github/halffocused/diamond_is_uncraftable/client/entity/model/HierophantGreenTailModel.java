package io.github.halffocused.diamond_is_uncraftable.client.entity.model;

import io.github.halffocused.diamond_is_uncraftable.entity.stand.attack.HierophantGreenTailEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HierophantGreenTailModel extends AbstractStandAttackModel<HierophantGreenTailEntity> {
    private final ModelRenderer Tail;

    public HierophantGreenTailModel() {
        textureWidth = 64;
        textureHeight = 32;

        Tail = new ModelRenderer(this);
        Tail.setRotationPoint(0.0F, 24.0F, 0.0F);
        Tail.setTextureOffset(0, 0).addBox(-1.5F, -22.0F, -7.5F, 3.0F, 2.0F, 15.0F, 0.0F, false);
    }

    @Override
    protected ModelRenderer getAttackModel() {
        return Tail;
    }
}