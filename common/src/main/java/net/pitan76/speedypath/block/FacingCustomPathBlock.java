package net.pitan76.speedypath.block;

import net.pitan76.mcpitanlib.api.block.args.v2.PlacementStateArgs;
import net.pitan76.mcpitanlib.api.block.v2.CompatBlock;
import net.pitan76.mcpitanlib.api.state.property.CompatProperties;
import net.pitan76.mcpitanlib.api.state.property.DirectionProperty;
import net.pitan76.mcpitanlib.core.serialization.codecs.CompatBlockMapCodecUtil;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.util.math.Direction;

public class FacingCustomPathBlock extends CustomPathBlock {
    public static final CompatMapCodec<FacingCustomPathBlock> CODEC = CompatBlockMapCodecUtil.createCodec(FacingCustomPathBlock::new);

    public static DirectionProperty FACING = CompatProperties.HORIZONTAL_FACING;

    @Override
    public CompatMapCodec<? extends CompatBlock> getCompatCodec() {
        return CODEC;
    }

    public FacingCustomPathBlock(CompatibleBlockSettings settings) {
        super(settings);
        setDefaultState(getDefaultMidohraState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(PlacementStateArgs args) {
        BlockState state = super.getPlacementState(args);
        if (state == null)
            state = getDefaultMidohraState();

        return state.with(FACING, args.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void appendProperties(AppendPropertiesArgs args) {
        super.appendProperties(args);
        args.addProperty(FACING);
    }
}
