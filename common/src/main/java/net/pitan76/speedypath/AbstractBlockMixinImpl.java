package net.pitan76.speedypath;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.entity.effect.CompatStatusEffectInstance;
import net.pitan76.mcpitanlib.api.util.BlockStateUtil;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.api.util.StatusEffectUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;

import java.util.Map;

public class AbstractBlockMixinImpl {
    public static void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (WorldUtil.isClient(world)) return;
        if (WorldUtil.getTime(world) % 4 != 0) return;

        if (Config.isEmptyPath) return;
        if (!entity.isLiving()) return;
        if (entity.isSneaking()) return;

        LivingEntity livingEntity = (LivingEntity) entity;

        for (Map.Entry<Block, Map<String, Integer>> entry : Config.pathBlocks.entrySet()) {
            Block block = entry.getKey();

            if (!BlockStateUtil.getBlock(state).equals(block)) continue;

            Map<String, Integer> effects = entry.getValue();
            for (Map.Entry<String, Integer> effect : effects.entrySet()) {
                String effectId = effect.getKey().toLowerCase();

                int level = effect.getValue();

                CompatStatusEffectInstance statusEffect = new CompatStatusEffectInstance(StatusEffectUtil.getStatusEffect(CompatIdentifier.of(effectId)), 5, level, false, false);
                livingEntity.addStatusEffect(statusEffect.getInstance());
            }

            return;
        }
    }
}
