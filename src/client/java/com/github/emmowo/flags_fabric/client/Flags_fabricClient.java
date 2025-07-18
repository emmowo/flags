package com.github.emmowo.flags_fabric.client;

import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.config.FlagsConfig;
import com.github.emmowo.flags_fabric.client.generator.BasicOBJParser;
import com.github.emmowo.flags_fabric.client.render.FlagBlockEntityRenderer;
import com.github.emmowo.flags_fabric.client.render.FlagModelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;

import java.io.*;

public class Flags_fabricClient implements ClientModInitializer {

    public static final Identifier FLAG_HANDHELD_OBJ = Identifier.of("flags","obj/flag_test.obj");

    public static final Identifier FLAG_PLACED_OBJ = Identifier.of("flags","obj/flag_placed.obj");


    static boolean has_initialized = false;

    public static BasicOBJParser.OBJModel flag;

    public static BasicOBJParser.OBJModel flag_placed;

    public static File CONFIG = FabricLoader.getInstance().getConfigDir().resolve("flags_config.txt").toFile();



    @Override
    public void onInitializeClient() {


        HandledScreens.register(Flags_fabric.SELECTOR_SCREEN_TYPE,FlagSelectScreen::new);

        SpecialModelTypes.ID_MAPPER.put(Flags_fabric.HELD_FLAG_ID, FlagModelRenderer.Unbaked.CODEC);

        if(!CONFIG.exists()){
            try {
                CONFIG.createNewFile();
                FlagsConfig.writeConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            FlagsConfig.readConfig();
        }

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {

            if(!has_initialized){ // allows resources to init a bit later so we can load the obj.

                has_initialized = true;
                BufferedReader r;

                try {
                    r = MinecraftClient.getInstance().getResourceManager().openAsReader(FLAG_HANDHELD_OBJ);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                BasicOBJParser parser = new BasicOBJParser();

                flag = parser.parse(r);


                BufferedReader r2;

                try {
                    r.close();

                    r2 = MinecraftClient.getInstance().getResourceManager().openAsReader(FLAG_PLACED_OBJ);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                BasicOBJParser parser2 = new BasicOBJParser();

                flag_placed = parser2.parse(r2);



            };

        });


        BlockEntityRendererFactories.register(Flags_fabric.FLAG_BLOCK_ENT, FlagBlockEntityRenderer::new);


    }



}
