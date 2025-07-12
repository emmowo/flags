package com.github.emmowo.flags_fabric.client.generator;

import com.github.emmowo.flags_fabric.Flags_fabric;
import com.github.emmowo.flags_fabric.client.render.FlagModelRenderer;
import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.data.*;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlagGenerators extends FabricModelProvider {
    public FlagGenerators(FabricDataOutput output) {
        super(output);
    }


    public static final Logger MODEL_GENERATOR_LOGGER = LoggerFactory.getLogger("[Flags Datagen]");

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        SpecialModelTypes.ID_MAPPER.put(Flags_fabric.HELD_FLAG_ID, FlagModelRenderer.Unbaked.CODEC);

        blockStateModelGenerator.registerSpecialItemModel(Flags_fabric.block,FlagModelRenderer.Unbaked.INSTANCE);

        //blockStateModelGenerator.registerAxisRotated(Flags_fabric.block,);



        var a = BlockStateModelGenerator.createWeightedVariant(ModelIds.getItemModelId(Flags_fabric.HELD_FLAG));

        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(Flags_fabric.block,a));


    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        ItemModel.Unbaked test = ItemModels.special(Flags_fabric.HELD_FLAG_ID,FlagModelRenderer.Unbaked.INSTANCE);

        //itemModelGenerator.register(Flags_fabric.HELD_FLAG,Models.GENERATED);

        itemModelGenerator.output.accept(Flags_fabric.HELD_FLAG,test);

        //SpecialModelTypes.ID_MAPPER.put(Flags_fabric.HELD_FLAG_ID)

        if(FabricLoader.getInstance().isDevelopmentEnvironment()){
            // put warning here once I figure out flag gui problems
        }




    }
}
