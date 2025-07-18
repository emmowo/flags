package com.github.emmowo.flags_fabric.client.render;

import com.github.emmowo.flags_fabric.FlagBlockEntity;
import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.Flags_fabricClient;
import com.github.emmowo.flags_fabric.client.FlagsGlobals;
import com.github.emmowo.flags_fabric.client.generator.BasicOBJParser;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

    public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        // do nothing
    }

    protected float seed;

    @Override
    public void render(FlagBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {


        // basically a copy of FlagModelRenderer

        matrices.push();

        var flagmodel = supplyFlagModel(entity.flagtype);




        //var truedata = data.getLeft();

        //seed = data.getRight();


        /*
        if(truedata == null){
            truedata = "clear";
        }

         */

        var dir = entity.getCachedState().get(Properties.HORIZONTAL_FACING);


        var p = entity.getPos();

        switch (dir){
            case NORTH -> {
                // do nothing, zero degrees of rotation from default.

                if(flagmodel == Flags_fabricClient.flag){ // push into a wall
                    matrices.translate(0,0,0.5);
                }

                break;
            }

            case EAST -> {

                matrices.translate(1.0F, 0.0, 0.0F);


                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270f),0,0,0); // I had to flip this.

                if(flagmodel == Flags_fabricClient.flag){
                    matrices.translate(0,0,0.5);
                }


                break;
            }

            case SOUTH -> {
                matrices.translate(1.0F, 0.0, 1.0F);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f),0,0,0);

                if(flagmodel == Flags_fabricClient.flag){
                    matrices.translate(0,0,0.5);
                }

                break;
            }

            case WEST -> {

                matrices.translate(0.0F, 0.0, 1.0F);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f),0,0,0);

                if(flagmodel == Flags_fabricClient.flag){
                    matrices.translate(0,0,0.5);
                }

                break;

            }

        }

        var entry = matrices.peek();


        //VertexConsumer consumer;


        light = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());

        for(BasicOBJParser.OBJSubObject o : flagmodel.objects){
            if(!Objects.equals(o.name, "flag")){ // WARNING: animated part must be named "flag". All parts of the model are presumed to be quads.

                //var t = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS,VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);



                RenderLayer layer =  determineLayer("test"); //FlagsRenderType.FLAG_STATIC_LAYER.apply(FlagsRenderType.TESTING_TEXTURE);

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

                RenderLayer layer = determineLayer(entity.flagtype); //FlagsRenderType.FLAG_STATIC_LAYER.apply(new RenderPhase.Texture(Identifier.of(Flags_fabric.NAMESPACE,"textures/" + truedata + ".png"), TriState.FALSE, true));

                var t = vertexConsumers.getBuffer(layer);

                //allow sampling of noise in our flag shader

                time = System.currentTimeMillis(); // keep time relatively the same between faces

                WindProvider provider;

                if(flagmodel == Flags_fabricClient.flag_placed){
                    provider = new WindProvider.FloorFlagWindProvider(seed,time);
                }else {
                    provider = new WindProvider.SmallFlagWindProvider(seed,time);
                }


                for(var f: o.faces){

/*
                    consumer.vertex(entry,nudgeVector(f.verticies.get(0))).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(1))).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(2))).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF);
                    consumer.vertex(entry,nudgeVector(f.verticies.get(3))).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF);
 */
                    t.vertex(entry, provider.transformVertex(f.verticies.get(0))).normal(entry,f.verticies_norm.get(0)).texture(f.vts.get(0).x,f.vts.get(0).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry, provider.transformVertex(f.verticies.get(1))).normal(entry,f.verticies_norm.get(1)).texture(f.vts.get(1).x,f.vts.get(1).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry, provider.transformVertex(f.verticies.get(2))).normal(entry,f.verticies_norm.get(2)).texture(f.vts.get(2).x,f.vts.get(2).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);
                    t.vertex(entry, provider.transformVertex(f.verticies.get(3))).normal(entry,f.verticies_norm.get(3)).texture(f.vts.get(3).x,f.vts.get(3).y).light(light).color(0xFFFFFFFF).overlay(OverlayTexture.DEFAULT_UV);


                }

                // layer.draw(t.end());
            }
        }

        matrices.pop();

    }

    public BasicOBJParser.OBJModel supplyFlagModel(String lore){

        var modelType = lore.split(",");

        var exp  = "";

        if(modelType.length < 2){
            exp = "small";
        }else {
            exp = modelType[1].trim();
        }

        return switch (exp) {
            case "small" -> Flags_fabricClient.flag;
            case "floor" -> Flags_fabricClient.flag_placed;
            default -> Flags_fabricClient.flag; // good for when a player downgrades a version
        };

    }

    public RenderLayer determineLayer(String textureID){

        var flag_name = textureID.split(",")[0];

        if(flag_name.equals("inherit")){
            flag_name = FlagsGlobals.placeholderFlagGrabber();
        }


        return FlagsRenderType.FLAG_STATIC_LAYER.apply(new RenderPhase.Texture(Identifier.of(Flags_fabric.NAMESPACE,"textures/" + flag_name + ".png"),true));


    }


    double time = System.currentTimeMillis();

    /*
    public Vector3f nudgeVector(Vector3f i){





        Vector3f v = new Vector3f(i); // prevent reference stuff happening



        v.x += (float) (Math.sin(((time + this.seed) * 0.001) + v.x + Math.cos(v.z + v.x + ((this.seed + time) * 0.001))) * 0.225 * v.z * v.z * (v.z * (MinecraftClient.getInstance().world.isRaining() ? 1.2 : 1.0)  ));
        v.y += (float) (Math.sin(((time + this.seed) * 0.002) + v.y + Math.cos(v.z + v.y + ((this.seed + time) * 0.001))) * 0.15 * v.z * ((v.z + 1) * 0.5) );
        //v.z += (float) Math.sin(time + v.z);

        return v;

    }
     */
}
