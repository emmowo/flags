package com.github.emmowo.flags_fabric.client.generator;

import com.github.emmowo.flags_fabric.Flags_fabric;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FlagCraftingGen extends FabricRecipeProvider {
    public FlagCraftingGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {



        return new RecipeGenerator(wrapperLookup,recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                createShapeless(RecipeCategory.DECORATIONS, Flags_fabric.HELD_FLAG).input(Items.STRING).input(Items.WHITE_WOOL).input(Items.STICK).criterion(hasItem(Items.WHITE_WOOL),conditionsFromItem(Items.WHITE_WOOL)).offerTo(exporter);



            }
        };
    }

    @Override
    public String getName() {
        return "FlagsRecipeProvider";
    }
}
