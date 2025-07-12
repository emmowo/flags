package com.github.emmowo.flags_fabric.client.render;

import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.Flags_fabricClient;
import com.github.emmowo.flags_fabric.client.generator.BasicOBJParser;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.TriState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyFlagModelRenderer{

    public static LegacyFlagModelRenderer INSTANCE = new LegacyFlagModelRenderer();

    ModelTransformationMode currentMode;

    // creates a unique wind effect per-flag to fake realism.
    protected float seed;

    public void render(Pair<String,Integer> data, ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {


        currentMode = modelTransformationMode;

        matrices.push();

        var truedata = data.getLeft();

        seed = data.getRight();


        if(truedata == null){
            truedata = "clear";
        }

        var entry = matrices.peek();

        //VertexConsumer consumer;



        var flagmodel = Flags_fabricClient.flag;
        for(BasicOBJParser.OBJSubObject o : flagmodel.objects){
            if(!Objects.equals(o.name, "flag")){ // WARNING: animated part must be named "flag". All parts of the model are presumed to be quads.

                //var t = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS,VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);


                RenderLayer layer =  determineLayer("test",modelTransformationMode); //FlagsRenderType.FLAG_STATIC_LAYER.apply(FlagsRenderType.TESTING_TEXTURE);

                var t = vertexConsumers.getBuffer(layer); // lazy fix since the lesbian flag has white for the pole colour

                for(var f: o.faces){
                    /*
                    consumer.vertex(entry,f.verticies.get(0)).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,f.verticies.get(1)).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,f.verticies.get(2)).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,f.verticies.get(3)).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);
                     */

                    t.vertex(entry,f.verticies.get(0)).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry,f.verticies.get(1)).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry,f.verticies.get(2)).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry,f.verticies.get(3)).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);


                }

                //layer.draw(t.end());


            }else {


                //var t = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS,VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);

                RenderLayer layer = determineLayer(truedata,modelTransformationMode); //FlagsRenderType.FLAG_STATIC_LAYER.apply(new RenderPhase.Texture(Identifier.of(Flags_fabric.NAMESPACE,"textures/" + truedata + ".png"), TriState.FALSE, true));

                var t = vertexConsumers.getBuffer(layer);

                //allow sampling of noise in our flag shader

                time = System.currentTimeMillis(); // keep time relatively the same between faces


                for(var f: o.faces){

/*
                    consumer.vertex(entry,nudgeVector(f.verticies.get(0))).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(1))).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(2))).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(3))).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);
 */
                    t.vertex(entry,nudgeVector(f.verticies.get(0))).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry,nudgeVector(f.verticies.get(1))).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry,nudgeVector(f.verticies.get(2))).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry,nudgeVector(f.verticies.get(3))).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);


                }

               // layer.draw(t.end());
            }
        }

        matrices.pop();

    }

    public RenderLayer determineLayer(String textureID, ModelTransformationMode ctx){

        if(ctx == ModelTransformationMode.GUI){
            return FlagsRenderType.FLAG_FALLBACK.apply(new RenderPhase.Texture(Identifier.of(Flags_fabric.NAMESPACE,"textures/" + textureID + ".png"), TriState.FALSE, true));
        }else {
            return FlagsRenderType.FLAG_STATIC_LAYER.apply(new RenderPhase.Texture(Identifier.of(Flags_fabric.NAMESPACE,"textures/" + textureID + ".png"), TriState.FALSE, true));
        }

    }

    public @Nullable Pair<String,Integer> getData(ItemStack stack,ModelTransformationMode mode) {
        if(!stack.get(DataComponentTypes.LORE).lines().isEmpty()) {

            var code = stack.get(DataComponentTypes.DAMAGE);

            if(code == null){
                stack.set(DataComponentTypes.DAMAGE, ThreadLocalRandom.current().nextInt(300,5000));
                code = stack.get(DataComponentTypes.DAMAGE);
            }

            if(mode == ModelTransformationMode.GROUND){
                code = 5; // I feel like nobody will notice this anyways.
            }


            return new Pair<>(stack.get(DataComponentTypes.LORE).lines().getFirst().getString(),code); // ensure more-unique values
        }else {
            return new Pair<>("clear",0);
        }
    }

    double time = System.currentTimeMillis();

    public Vector3f nudgeVector(Vector3f i){





        Vector3f v = new Vector3f(i); // prevent reference stuff happening



        v.x += (float) (Math.sin(((time + this.seed) * 0.001) + v.x + Math.cos(v.z + v.x + ((this.seed + time) * 0.001))) * 0.15 * v.z * v.z * (v.z * (MinecraftClient.getInstance().world.isRaining() ? 1.2 : 1.0)  ));
        v.y += (float) (Math.sin(((time + this.seed) * 0.001) + v.y + Math.cos(v.z + v.y + ((this.seed + time) * 0.001))) * 0.075 * v.z * v.z);

        //v.z += (float) Math.sin(time + v.z);

        return v;

    }



}
