package com.github.emmowo.flags_fabric.client.mixin;

import com.github.emmowo.flags_fabric.client.FlagsGlobals;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<S extends BipedEntityRenderState, M extends EntityModel<S>> extends FeatureRenderer<S, M> {

    public ElytraFeatureRendererMixin(FeatureRendererContext<S, M> context) {
        super(context);
    }

    private static boolean doOverride;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",at = @At("HEAD"))
    private void prideOverride(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S bipedEntityRenderState, float f, float g, CallbackInfo ci){

        var tmp = FlagsGlobals.shouldOverrideForStateElytra(bipedEntityRenderState);

        doOverride = tmp;

    }


    @Redirect(method = "getTexture",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;elytraTexture()Lnet/minecraft/util/Identifier;"))
    private static Identifier prideOverride(SkinTextures instance){

        if(doOverride){
            return FlagsGlobals.PRIDE_CAPE;
        }else {
            return instance.capeTexture();
        }
    }

    @Redirect(method = "getTexture",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;capeTexture()Lnet/minecraft/util/Identifier;"))
    private static Identifier prideOverride2(SkinTextures instance){

        if(doOverride){
            return FlagsGlobals.PRIDE_CAPE;
        }else {
            return instance.capeTexture();
        }
    }


}
