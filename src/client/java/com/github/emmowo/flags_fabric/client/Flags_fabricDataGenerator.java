package com.github.emmowo.flags_fabric.client;

import com.github.emmowo.flags_fabric.client.generator.FlagCraftingGen;
import com.github.emmowo.flags_fabric.client.generator.FlagGenerators;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class Flags_fabricDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(FlagGenerators::new);
        pack.addProvider(FlagCraftingGen::new);

    }



}
