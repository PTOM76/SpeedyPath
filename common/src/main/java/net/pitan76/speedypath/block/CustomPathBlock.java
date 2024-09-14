package net.pitan76.speedypath.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.shape.VoxelShape;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.event.block.CanPathfindThroughArgs;
import net.pitan76.mcpitanlib.api.event.block.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;

public class CustomPathBlock extends ExtendBlock {
    public static final CompatMapCodec<CustomPathBlock> CODEC = CompatMapCodec.createCodecOfExtendBlock(CustomPathBlock::new);
    public static final VoxelShape SHAPE = VoxelShapeUtil.blockCuboid(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    @Override
    public CompatMapCodec<? extends Block> getCompatCodec() {
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
    public VoxelShape getOutlineShape(OutlineShapeEvent event) {
        return SHAPE;
    }

    @Override
    public boolean canPathfindThrough(CanPathfindThroughArgs args) {
        return false;
    }
}
