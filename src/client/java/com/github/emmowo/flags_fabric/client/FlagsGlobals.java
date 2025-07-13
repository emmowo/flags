package com.github.emmowo.flags_fabric.client;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;

import java.util.concurrent.ThreadLocalRandom;

public class FlagsGlobals {

    public static enum PrideOverrideState{
        PLAYERS_WITHOUT_CAPES,
        PLAYERS_WITH_CAPES,
        ALL_PLAYERS,
        DISABLED,
        //MIGRATOR_CAPES,
        //MIGRATOR_AND_PLAYERS_WITHOUT_CAPES,
    }


    public static String PRIDE_OVERRIDE_STATE = PrideOverrideState.DISABLED.name();

    public static boolean shouldOverrideForState(PlayerEntityRenderState state){

        if(PRIDE_OVERRIDE_STATE.equals(PrideOverrideState.ALL_PLAYERS.name())){
            return true;
        }

        if(!state.capeVisible && (PRIDE_OVERRIDE_STATE.equals(PrideOverrideState.PLAYERS_WITHOUT_CAPES.name())/* || PRIDE_OVERRIDE_STATE.equals(PrideOverrideState.MIGRATOR_AND_PLAYERS_WITHOUT_CAPES)*/)){
            return true;
        }

        if(PRIDE_OVERRIDE_STATE.equals(PrideOverrideState.PLAYERS_WITH_CAPES.name()) && state.capeVisible){
            return true;
        }

        return false;

    }

    public static boolean shouldOverrideForStateElytra(BipedEntityRenderState state){

        if(PRIDE_OVERRIDE_STATE.equals(PrideOverrideState.ALL_PLAYERS.name())){
            return true;
        }

        if(PRIDE_OVERRIDE_STATE.equals(PrideOverrideState.PLAYERS_WITH_CAPES.name())){
            return true;
        }

        return false;

    }

    public static final Identifier PRIDE_CAPE = Identifier.of("flags","textures/cape/progresspride.png");


    private static long lastGenTime = 0;

    private static int lastFlagRandomVal = 0;

    public static String placeholderFlagGrabber(){

        if(System.currentTimeMillis() - lastGenTime > 3500){
            lastFlagRandomVal = ThreadLocalRandom.current().nextInt(5);
            lastGenTime = System.currentTimeMillis();
        }


        return switch (lastFlagRandomVal){
            case 1 -> "lesbian_flag";
            case 2 -> "gay_flag";
            case 3 -> "bi_flag";
            case 4 -> "trans_flag";
            default -> "pan_flag";
        };
    }

}
