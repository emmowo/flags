package com.github.emmowo.flags_fabric;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;


//NOTE: will apply to both block and held flags
public class FlagSelectScreenHandler extends ScreenHandler {
    protected FlagSelectScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos itemLocation) {
        super(Flags_fabric.SELECTOR_SCREEN_TYPE, syncId);
        this.location = itemLocation;
    }

    public BlockPos location;

    protected FlagSelectScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Flags_fabric.SELECTOR_SCREEN_TYPE, syncId);

        this.context = ScreenHandlerContext.EMPTY;
    }

    public ScreenHandlerContext context;

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true; // don't care about movement
    }

    public void forceOverrideLore(PlayerInventory invRef, ItemStack item){



    }

}
