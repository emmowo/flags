package com.github.emmowo.flags_fabric.client.mixin;

import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.render.FlagModelRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.model.BlockStateManagers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin {

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    public void flagsItemFrameOverride(ItemFrameEntityRenderState itemFrameEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){

        var handle = ((ItemRenderStateAccessor) itemFrameEntityRenderState.itemRenderState);

        if( handle.getLayerCount() > 0){

            if(((ItemRenderStateAccessor.LayerRenderStateAccessor)handle.getLayers()[0]).getData() instanceof Pair<?,?> pair){

                if(pair.getLeft() instanceof String){
                    itemFrameEntityRenderState.invisible = true;
                }

            }

        }

    }

}
