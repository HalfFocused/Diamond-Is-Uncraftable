package io.github.halffocused.diamond_is_uncraftable.client.entity.render;

import io.github.halffocused.diamond_is_uncraftable.client.entity.model.EchoesAct2Model;
import io.github.halffocused.diamond_is_uncraftable.entity.stand.EchoesAct2Entity;
import io.github.halffocused.diamond_is_uncraftable.util.Util;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class EchoesAct2Renderer extends AbstractStandRenderer<EchoesAct2Entity, EchoesAct2Model> {
    public EchoesAct2Renderer(EntityRendererManager manager) {
        super(manager, new EchoesAct2Model());
    }

    @Override
    public ResourceLocation getEntityTexture(EchoesAct2Entity entity) {
        return Util.ResourceLocations.ECHOES_ACT_2;
    }
}
