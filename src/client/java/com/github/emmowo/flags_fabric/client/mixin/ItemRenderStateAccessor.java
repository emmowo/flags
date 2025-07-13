package com.github.emmowo.flags_fabric.client.mixin;

import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderState.class)
public interface ItemRenderStateAccessor {

    @Accessor
    ItemRenderState.LayerRenderState[] getLayers();

    @Accessor
    int getLayerCount();


    @Mixin(ItemRenderState.LayerRenderState.class)
    interface LayerRenderStateAccessor{

        @Accessor
        SpecialModelRenderer<Object> getSpecialModelType();

        @Accessor
        Object getData();

    }

}
