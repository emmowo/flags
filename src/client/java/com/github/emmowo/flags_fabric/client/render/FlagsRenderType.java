package com.github.emmowo.flags_fabric.client.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;

import java.util.function.Function;

import static net.minecraft.client.gl.RenderPipelines.*;

public class FlagsRenderType {


    public static final RenderPhase.Texture TESTING_TEXTURE = new RenderPhase.Texture(Identifier.of("flags","textures/test.png"),true);


    //TODO: re-implement noise sampling for wind AND entity motion later. Old impl didn't look good or work well at all.
    //public static final Identifier NOISE_ID = Identifier.of("flags","textures/wind_noise.png");


    //public static final RenderPhase.Texture WIND_NOISE = new RenderPhase.Texture(NOISE_ID, TriState.FALSE,false);






    /** core shaders are so painful to work with to the point that I literally just resorted to using the CPU.
     @Deprecated
    public static final RenderPipeline FLAG_ANIMATED = register(RenderPipeline.builder(FLAG_SNIPPET).withVertexShader(Identifier.of(Flags_fabric.NAMESPACE,"core/flag")).withUniform("pos_mat", UniformType.MATRIX4X4).withUniform("norm_mat",UniformType.MATRIX4X4).withSampler("Sampler3").withLocation("pipeline/flags").withCull(false).build());

    @Deprecated
    public static final Function<RenderPhase.Texture,RenderLayer> FLAG_SHADER_ANIMATION = (texture ->  RenderLayer.of(
            "flags",
            4194304,
            true,
            false,
            FLAG_ANIMATED,
            RenderLayer.MultiPhaseParameters.builder().lightmap(RenderLayer.ENABLE_LIGHTMAP).texture(texture).overlay(RenderPhase.DISABLE_OVERLAY_COLOR).build(true)
    ));

     */

    // avoid using overlay while still having the better entity shader lighting.

    /** OLD METHOD, FASTER BUT BREAKS IRIS  */
    /** public static final RenderPipeline FLAG_STATIC_SHADERS = register(RenderPipeline.builder(FLAG_SNIPPET).withVertexShader("core/entity").withLocation("pipeline/entity_solid").withCull(false).build());



    public static final Function<RenderPhase.Texture,RenderLayer> FLAG_STATIC_LAYER = (texture ->  RenderLayer.of(
            "flags",
            1536,
            true,
            false,
            FLAG_STATIC_SHADERS,
            RenderLayer.MultiPhaseParameters.builder().lightmap(RenderLayer.ENABLE_LIGHTMAP).texture(texture).overlay(RenderPhase.DISABLE_OVERLAY_COLOR).build(true)
    ));

     */



    public static final Function<RenderPhase.Texture,RenderLayer> FLAG_STATIC_LAYER = (texture ->  RenderLayer.of(
            "flags",
            1536,
            true,
            false,
            ENTITY_CUTOUT_NO_CULL,
            RenderLayer.MultiPhaseParameters.builder().lightmap(RenderLayer.ENABLE_LIGHTMAP).overlay(RenderPhase.ENABLE_OVERLAY_COLOR).texture(texture).build(false)
    ));

    public static final Function<RenderPhase.Texture,RenderLayer> FLAG_FALLBACK = (texture ->  RenderLayer.of(
            "flags",
            1536,
            true,
            false,
            ENTITY_CUTOUT_NO_CULL,
            RenderLayer.MultiPhaseParameters.builder().lightmap(RenderLayer.ENABLE_LIGHTMAP).overlay(RenderPhase.ENABLE_OVERLAY_COLOR).texture(texture).build(false)
    ));


}
