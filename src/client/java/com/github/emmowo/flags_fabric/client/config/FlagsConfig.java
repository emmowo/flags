package com.github.emmowo.flags_fabric.client.config;

import com.github.emmowo.flags_fabric.client.FlagsGlobals;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.RecordComponent;

import static com.github.emmowo.flags_fabric.client.Flags_fabricClient.CONFIG;

public class FlagsConfig{


    // UNUSED for now outside of initial creation
    public static void writeConfig(){
        try {
            FileWriter writer_holder = new FileWriter(CONFIG);

            BufferedWriter writer = new BufferedWriter(writer_holder);

            writer.write("config_version=" + FabricLoader.getInstance().getModContainer("flags_fabric").get().getMetadata().getVersion() + "\n \n");

            writer.write("# `pride_override` allows you to override capes to be the cut Progress Pride flag. Available settings: PLAYERS_WITHOUT_CAPES, PLAYERS_WITH_CAPES, ALL_PLAYERS, DISABLED \n");


            writer.write("pride_override=" + FlagsGlobals.PRIDE_OVERRIDE_STATE + "\n");

            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void readConfig(){
        try {
            FileReader reader = new FileReader(CONFIG);
            BufferedReader reader1 = new BufferedReader(reader);

            var line = reader1.readLine();

            while (line != null) {

                // ignoring config version for now

                if(line.startsWith("pride_override")){
                    FlagsGlobals.PRIDE_OVERRIDE_STATE = line.split("=")[1];
                }


                line = reader1.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public record Config(String fileCfgVersion, FlagsGlobals.PrideOverrideState state) {

    }


}
