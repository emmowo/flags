package com.github.emmowo.flags_fabric.client.mixin;

import com.github.emmowo.flags_fabric.client.FlagsGlobals;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeFeatureRenderer.class)
public class CapeFeatureRendererMixin {

    @Unique
    private static boolean doOverride = false;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",at = @At("HEAD"))
    public void prideOverrideCapeAppearance(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerEntityRenderState playerEntityRenderState, float f, float g, CallbackInfo ci){
        var tmp = FlagsGlobals.shouldOverrideForState(playerEntityRenderState);
        if(tmp) {
            playerEntityRenderState.capeVisible = true;
            doOverride = tmp;
        }
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;capeTexture()Lnet/minecraft/util/Identifier;"))
    public Identifier prideOverride(SkinTextures instance){

        if(doOverride){
            return FlagsGlobals.PRIDE_CAPE;
        }else {
            return instance.capeTexture();
        }
    }

}
