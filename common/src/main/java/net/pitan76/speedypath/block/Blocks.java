package net.pitan76.speedypath.block;

import net.minecraft.block.Block;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.speedypath.SpeedyPath._id;
import static net.pitan76.speedypath.SpeedyPath.registry;

public class Blocks {

    public static RegistryResult<Block> STONE_PATH;
    public static RegistryResult<Block> BRICK_PATH;

    public static void init() {
        STONE_PATH = registry.registerBlock(_id("stone_path"), () -> new CustomPathBlock(CompatibleBlockSettings.of(CompatibleMaterial.STONE).strength(1.5F, 6.0F)));
        BRICK_PATH = registry.registerBlock(_id("brick_path"), () -> new CustomPathBlock(CompatibleBlockSettings.of(CompatibleMaterial.STONE).strength(1.5F, 6.0F)));
    }
}
