package com.github.emmowo.flags_fabric;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class FlagBlockEntity extends BlockEntity {
    public FlagBlockEntity( BlockPos pos, BlockState state) {
        super(Flags_fabric.FLAG_BLOCK_ENT, pos, state);
    }
}
