package com.github.emmowo.flags_fabric.client.mixin;

import com.github.emmowo.flags_fabric.Flags_fabric;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    // not as nice as the non-legacy solution

    @Inject(method = "getArmPose(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/util/Arm;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", at = @At("HEAD"), cancellable = true)
    private static void getArmPose(PlayerEntityRenderState state, Arm arm, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (!state.handSwinging && state.getMainHandStack().isOf(Flags_fabric.HELD_FLAG) || (!state.handSwinging && state.getMainHandStack().isOf(Flags_fabric.block.asItem()))) {
            cir.setReturnValue(BipedEntityModel.ArmPose.TOOT_HORN);
        }

    }

}
