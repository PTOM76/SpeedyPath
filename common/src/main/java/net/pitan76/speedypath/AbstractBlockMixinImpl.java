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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

public class AbstractBlockMixinImpl {
    public static void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (Config.pathBlocks.isEmpty()) return;
        if (!entity.isLiving()) return;
        if (entity.isSneaking()) return;

        LivingEntity livingEntity = (LivingEntity) entity;

        for (Map.Entry<Block, Map<String, Integer>> entry : Config.pathBlocks.entrySet()) {
            Block block = entry.getKey();

            if (!BlockStateUtil.getBlock(state).equals(block)) continue;

            Map<String, Integer> effects = entry.getValue();
            for (Map.Entry<String, Integer> effect : effects.entrySet()) {
                String effectId = effect.getKey().toLowerCase();

                int level;
                try {
                    level = effect.getValue();
                } catch (ClassCastException e) {
                    level = 1;
                }

                CompatStatusEffectInstance statusEffect = new CompatStatusEffectInstance(StatusEffectUtil.getStatusEffect(CompatIdentifier.of(effectId)), 5, level, false, false);
                livingEntity.addStatusEffect(statusEffect.getInstance());
            }

            return;
        }
    }
}
