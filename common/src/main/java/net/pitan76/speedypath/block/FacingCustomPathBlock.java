package net.pitan76.speedypath.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.PlacementStateArgs;
import net.pitan76.mcpitanlib.api.util.BlockStateUtil;
import net.pitan76.mcpitanlib.api.util.PropertyUtil;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;

public class FacingCustomPathBlock extends CustomPathBlock {
    public static final CompatMapCodec<FacingCustomPathBlock> CODEC = CompatMapCodec.createCodecOfExtendBlock(FacingCustomPathBlock::new);

    public static DirectionProperty FACING = PropertyUtil.horizontalFacing();

    @Override
    public CompatMapCodec<? extends Block> getCompatCodec() {
        return CODEC;
    }

    public FacingCustomPathBlock(CompatibleBlockSettings settings) {
        super(settings);
        setNewDefaultState(getNewDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(PlacementStateArgs args) {
        BlockState state = super.getPlacementState(args);
        if (state == null)
            state = getNewDefaultState();

        return BlockStateUtil.with(state, FACING, args.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void appendProperties(AppendPropertiesArgs args) {
        super.appendProperties(args);
        args.addProperty(FACING);
    }
}
