package net.pitan76.speedypath.item;

import net.pitan76.mcpitanlib.api.util.CompatActionResult;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnBlockEvent;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.v2.CompatItem;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvents;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.entity.ItemEntityUtil;
import net.pitan76.speedypath.block.CustomPathBlock;

public class WrenchForPath extends CompatItem  {

    public WrenchForPath(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public CompatActionResult onRightClickOnBlock(ItemUseOnBlockEvent e) {
        BlockState state = e.getBlockState();
        World world = e.world;
        BlockPos pos = e.getBlockPos();
        Block block = state.getBlock();

        if (e.isClient()) return e.success();

        if (!(block instanceof CustomPathBlock || block instanceof DirtPathBlock))
            return super.onRightClickOnBlock(e);

        ItemEntity itemEntity = ItemEntityUtil.create(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ItemStackUtil.create(block));
        WorldUtil.removeBlock(world, pos, false);

        ItemEntityUtil.setToDefaultPickupDelay(itemEntity);
        WorldUtil.spawnEntity(world, itemEntity);

        WorldUtil.playSound(world, null, pos, CompatSoundEvents.ENTITY_SHEEP_SHEAR, CompatSoundCategory.BLOCKS, 1f, 1.25f);

        return e.success();
    }
}
