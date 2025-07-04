package com.github.emmowo.flags_fabric;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FlagBlock extends BlockWithEntity {
    public FlagBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    public static BlockPos LAST_KNOWN_POS;


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FlagBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FlagBlockEntity(pos,state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    protected NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new FlagSelectScreenHandler(syncId, inventory,/*, ScreenHandlerContext.create(world, pos)*/pos), Text.of("a")
        );
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if(world.isClient){
            LAST_KNOWN_POS = pos;
            return ActionResult.PASS;
        }

        player.openHandledScreen(createScreenHandlerFactory(world,pos));


        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }


}
