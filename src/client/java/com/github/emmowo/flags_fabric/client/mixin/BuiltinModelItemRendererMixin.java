package com.github.emmowo.flags_fabric.client.mixin;

import com.github.emmowo.flags_fabric.Flags_fabric;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static com.github.emmowo.flags_fabric.client.render.LegacyFlagModelRenderer.INSTANCE;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {




    // yikes...
    @Inject(method = "render", at = @At("HEAD"))
    public void legacyRenderInjector(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci){
        if(stack.isOf(Flags_fabric.HELD_FLAG) || stack.isOf(Flags_fabric.block.asItem())){
            INSTANCE.render(INSTANCE.getData(stack,mode),mode,matrices,vertexConsumers,light,overlay, stack.hasGlint());
        }
    }


}
