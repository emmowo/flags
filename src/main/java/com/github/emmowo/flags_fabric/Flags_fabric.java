package com.github.emmowo.flags_fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.*;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class Flags_fabric implements ModInitializer {

    public static final String NAMESPACE = "flags";

    public static final Identifier HELD_FLAG_ID = Identifier.of(NAMESPACE,"flag_held");
    public static final Identifier HELD_FLAG_BLOCK_ID = Identifier.of(NAMESPACE,"flag_held");


    // cannot be burned, mainly as a weak anti-griefing measure.
    // should I add a coloured rarity based on pride?
    public static Item  HELD_FLAG = register(HELD_FLAG_ID.getPath(),FlagItem::new,new Item.Settings().fireproof().maxCount(1));//flags that are held by hand or worn by the player. Smaller than most of the other flags.

    //current implementation is nonfunctional
    //public static Block block = register("hflag2",Block::new,AbstractBlock.Settings.create(),true);



    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NAMESPACE, path));
        return Items.register(registryKey, factory, settings);
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

        private static RegistryKey<Block> keyOfBlock(String name) {
            return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(NAMESPACE, name));
        }

        private static RegistryKey<Item> keyOfItem(String name) {
            return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NAMESPACE, name));
        }


    public record UpdateLorePacketC2S(LoreComponent component) implements CustomPayload {
        public static final Identifier LORE_UPDATE_ID = Identifier.of(NAMESPACE,"lore_update");
        public static CustomPayload.Id<UpdateLorePacketC2S> ID = new CustomPayload.Id<>(LORE_UPDATE_ID);
        public static final PacketCodec<RegistryByteBuf, UpdateLorePacketC2S> CODEC = PacketCodec.tuple(LoreComponent.PACKET_CODEC, UpdateLorePacketC2S::component, UpdateLorePacketC2S::new);


        @Override
        public Id<UpdateLorePacketC2S> getId() {
            return ID;
        }
    }


    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> itemGroup.add(Flags_fabric.HELD_FLAG));

        PayloadTypeRegistry.playC2S().register(UpdateLorePacketC2S.ID,UpdateLorePacketC2S.CODEC);


        ServerPlayNetworking.registerGlobalReceiver(UpdateLorePacketC2S.ID, (payload,context) ->{
            context.player().getInventory().getSelectedStack().set(DataComponentTypes.LORE,payload.component);
        });

    }

    public static ScreenHandlerType SELECTOR_SCREEN_TYPE = register("flagselect", FlagSelectScreenHandler::new);


    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

}
