package com.github.emmowo.flags_fabric.client.mixin;

import com.github.emmowo.flags_fabric.client.FlagsGlobals;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {

    @Redirect(method = "getTexture",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;elytraTexture()Lnet/minecraft/util/Identifier;"))
    private static Identifier prideOverride(SkinTextures instance){

        if(FlagsGlobals.PRIDE_OVERRIDE){
            return FlagsGlobals.PRIDE_CAPE;
        }else {
            return instance.elytraTexture();
        }
    }

    @Redirect(method = "getTexture",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;capeTexture()Lnet/minecraft/util/Identifier;"))
    private static Identifier prideOverride2(SkinTextures instance){

        if(FlagsGlobals.PRIDE_OVERRIDE){
            return FlagsGlobals.PRIDE_CAPE;
        }else {
            return instance.capeTexture();
        }
    }

}
