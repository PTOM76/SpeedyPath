package net.pitan76.speedypath.item;

import net.minecraft.block.Block;
import net.pitan76.mcpitanlib.api.util.CompatActionResult;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.entity.ItemEntity;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnBlockEvent;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.v2.CompatItem;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvents;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.entity.ItemEntityUtil;
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.world.World;
import net.pitan76.speedypath.block.CustomPathBlock;

public class WrenchForPath extends CompatItem  {

    public WrenchForPath(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public CompatActionResult onRightClickOnBlock(ItemUseOnBlockEvent e) {
        BlockState state = e.getMidohraState();
        World world = e.getMidohraWorld();
        BlockPos pos = e.getMidohraPos();
        Block block = state.getBlock().get();

        if (e.isClient()) return e.success();

        if (!(block instanceof CustomPathBlock || block instanceof DirtPathBlock))
            return super.onRightClickOnBlock(e);

        ItemEntity itemEntity = ItemEntityUtil.create(world.getRaw(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ItemStackUtil.create(block));
        ItemEntityUtil.setToDefaultPickupDelay(itemEntity);
        world.removeBlock(pos, false);
        world.spawnEntity(itemEntity);

        world.playSound(null, pos, CompatSoundEvents.ENTITY_SHEEP_SHEAR, CompatSoundCategory.BLOCKS, 1f, 1.25f);

        return e.success();
    }
}
