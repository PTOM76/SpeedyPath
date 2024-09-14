package net.pitan76.speedypath.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnBlockEvent;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.ExtendItem;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.entity.ItemEntityUtil;
import net.pitan76.speedypath.block.CustomPathBlock;

public class WrenchForPath extends ExtendItem {

    public WrenchForPath(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public ActionResult onRightClickOnBlock(ItemUseOnBlockEvent e) {
        BlockState state = e.getBlockState();
        World world = e.world;
        BlockPos pos = e.getBlockPos();
        Block block = state.getBlock();

        if (e.isClient()) return ActionResult.SUCCESS;

        if (!(block instanceof CustomPathBlock || block instanceof DirtPathBlock))
            return super.onRightClickOnBlock(e);

        ItemEntity itemEntity = ItemEntityUtil.create(world, pos.getX(), pos.getY(), pos.getZ(), ItemStackUtil.create(block));
        world.removeBlock(pos, false);

        ItemEntityUtil.setToDefaultPickupDelay(itemEntity);
        WorldUtil.spawnEntity(world, itemEntity);

        return ActionResult.SUCCESS;
    }
}
