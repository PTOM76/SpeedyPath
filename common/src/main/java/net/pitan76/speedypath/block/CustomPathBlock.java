package net.pitan76.speedypath.block;

import net.pitan76.mcpitanlib.core.serialization.codecs.CompatBlockMapCodecUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.v2.CompatBlock;
import net.pitan76.mcpitanlib.api.event.block.CanPathfindThroughArgs;
import net.pitan76.mcpitanlib.api.event.block.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;

public class CustomPathBlock extends CompatBlock  {
    public static final CompatMapCodec<CustomPathBlock> CODEC = CompatBlockMapCodecUtil.createCodec(CustomPathBlock::new);
    public static final VoxelShape SHAPE = VoxelShapeUtil.blockCuboid(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    @Override
    public CompatMapCodec<? extends CompatBlock> getCompatCodec() {
        return CODEC;
    }

    public CustomPathBlock(CompatibleBlockSettings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return SHAPE;
    }

    @Override
    public boolean canPathfindThrough(CanPathfindThroughArgs args) {
        return false;
    }
}
