package com.github.emmowo.flags_fabric;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlagItem extends Item {
    public FlagItem(Settings settings) {

        super(settings);
        settings.component(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("clear, small"))));

    }

    @Nullable
    protected NamedScreenHandlerFactory createScreenHandlerFactory( World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new FlagSelectScreenHandler(syncId, inventory, pos), Text.of("a")
        );
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if(!world.isClient){


            ServerPlayerEntity p2 = (ServerPlayerEntity) user;


            if(hand == Hand.OFF_HAND){
                user.sendMessage(Text.of("Flags cannot be set from the offhand!"),true);
                return ActionResult.FAIL;
            }

            p2.openHandledScreen(createScreenHandlerFactory(world,user.getBlockPos()));

        }

        return ActionResult.PASS;

    }
}
