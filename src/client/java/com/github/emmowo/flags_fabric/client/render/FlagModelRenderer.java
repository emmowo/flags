package com.github.emmowo.flags_fabric.client.render;

import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.Flags_fabricClient;
import com.github.emmowo.flags_fabric.client.generator.BasicOBJParser;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.TriState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;

public class FlagModelRenderer implements SpecialModelRenderer<Pair<String,Integer>> {


    // creates a unique wind effect per-flag to fake realism.
    protected float seed;


    @Override
    public void render(Pair<String,Integer> data, ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {

        matrices.push();

        var truedata = data.getLeft();

        seed = data.getRight();


        if(truedata == null){
            truedata = "clear";
        }

        var entry = matrices.peek();

        VertexConsumer consumer;



        var flagmodel = Flags_fabricClient.flag;
        for(BasicOBJParser.OBJSubObject o : flagmodel.objects){
            if(!Objects.equals(o.name, "flag")){ // WARNING: animated part must be named "flag". All parts of the model are presumed to be quads.

                var t = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS,VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);


                var layer = FlagsRenderType.FLAG_STATIC_LAYER.apply(FlagsRenderType.TESTING_TEXTURE);

                //consumer = vertexConsumers.getBuffer(layer); // lazy fix since the lesbian flag has white for the pole colour

                for(var f: o.faces){
                    /*
                    consumer.vertex(entry,f.verticies.get(0)).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,f.verticies.get(1)).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,f.verticies.get(2)).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,f.verticies.get(3)).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);
                     */

                    t.vertex(entry,f.verticies.get(0)).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    t.vertex(entry,f.verticies.get(1)).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    t.vertex(entry,f.verticies.get(2)).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    t.vertex(entry,f.verticies.get(3)).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);


                }

                layer.draw(t.end());


            }else {


                var t = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS,VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);

                var layer = FlagsRenderType.FLAG_STATIC_LAYER.apply(new RenderPhase.Texture(Identifier.of(Flags_fabric.NAMESPACE,"textures/" + truedata + ".png"), TriState.FALSE, true));

                //consumer = vertexConsumers.getBuffer(layer);

                //allow sampling of noise in our flag shader

                time = System.currentTimeMillis(); // keep time relatively the same between faces


                for(var f: o.faces){

/*
                    consumer.vertex(entry,nudgeVector(f.verticies.get(0))).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(1))).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(2))).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(3))).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);
 */
                    t.vertex(entry,nudgeVector(f.verticies.get(0))).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    t.vertex(entry,nudgeVector(f.verticies.get(1))).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    t.vertex(entry,nudgeVector(f.verticies.get(2))).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    t.vertex(entry,nudgeVector(f.verticies.get(3))).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);


                }

                layer.draw(t.end());
            }
        }

        matrices.pop();

    }

    @Override
    public @Nullable Pair<String,Integer> getData(ItemStack stack) {
        if(!stack.get(DataComponentTypes.LORE).lines().isEmpty()) {
            return new Pair<>(stack.get(DataComponentTypes.LORE).lines().getFirst().getString(),stack.hashCode()); // ensure more-unique values
        }else {
            return new Pair<>("clear",0);
        }
    }

    double time = System.currentTimeMillis();

    Vector3f nudgeVector(Vector3f i){





        Vector3f v = new Vector3f(i); // prevent reference stuff happening



        v.x += (float) (Math.sin(((time + this.seed) * 0.001) + v.x + Math.cos(v.z + v.x + ((this.seed + time) * 0.001))) * 0.15 * v.z * (v.z * (MinecraftClient.getInstance().world.isRaining() ? 2.0 : 1.0)  ));
        v.y += (float) (Math.sin(((time + this.seed) * 0.001) + v.y + Math.cos(v.z + v.y + ((this.seed + time) * 0.001))) * 0.1 * v.z);
        //v.z += (float) Math.sin(time + v.z);

        return v;

    }


    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final FlagModelRenderer.Unbaked INSTANCE = new FlagModelRenderer.Unbaked();
        public static final MapCodec<FlagModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<FlagModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new FlagModelRenderer();
        }
    }

}
