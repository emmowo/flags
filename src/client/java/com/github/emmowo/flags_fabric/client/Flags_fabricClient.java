package com.github.emmowo.flags_fabric.client;

import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.generator.BasicOBJParser;
import com.github.emmowo.flags_fabric.client.render.FlagModelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;

public class Flags_fabricClient implements ClientModInitializer {

    public static final Identifier FLAG = Identifier.of("flags","obj/flag_test.obj");

    static boolean has_initialized = false;

    public static BasicOBJParser.OBJModel flag;


    @Override
    public void onInitializeClient() {

        HandledScreens.register(Flags_fabric.SELECTOR_SCREEN_TYPE,FlagSelectScreen::new);

        SpecialModelTypes.ID_MAPPER.put(Flags_fabric.HELD_FLAG_ID, FlagModelRenderer.Unbaked.CODEC);

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {

            if(!has_initialized){ // allows resources to init a bit later so we can load the obj.

                has_initialized = true;
                BufferedReader r;

                try {
                    r = MinecraftClient.getInstance().getResourceManager().openAsReader(FLAG);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                BasicOBJParser parser = new BasicOBJParser();

                flag = parser.parse(r);



            };

        });



    }



}
