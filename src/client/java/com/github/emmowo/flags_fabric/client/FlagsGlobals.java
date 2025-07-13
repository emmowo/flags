package com.github.emmowo.flags_fabric.client;

import net.minecraft.util.Identifier;

import java.util.concurrent.ThreadLocalRandom;

public class FlagsGlobals {

    enum PrideOverrideState{
        PLAYERS_WITHOUT_CAPES,
        PLAYERS_WITH_CAPES,
        ALL_PLAYERS,
        MIGRATOR_CAPES,
        MIGRATOR_AND_PLAYERS_WITHOUT_CAPES,
    }

    public static boolean PRIDE_OVERRIDE = true;

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
