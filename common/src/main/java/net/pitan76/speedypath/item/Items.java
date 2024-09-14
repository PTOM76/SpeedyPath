package net.pitan76.speedypath.item;

import net.minecraft.item.Item;
import net.pitan76.speedypath.block.Blocks;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.ItemUtil;

import static net.pitan76.speedypath.SpeedyPath._id;
import static net.pitan76.speedypath.SpeedyPath.registry;

public class Items {

    public static final CompatibleItemSettings STANDARD_ITEM_SETTINGS = CompatibleItemSettings.of()
            .addGroup(ItemGroups.SPEEDY_PATH);

    public static RegistryResult<Item> WRENCH_FOR_PATH;
    public static RegistryResult<Item> STONE_PATH;
    public static RegistryResult<Item> BRICK_PATH;

    public static void init() {
        WRENCH_FOR_PATH = registry.registerItem(_id("wrench_for_path"), () -> new WrenchForPath(CompatibleItemSettings.of()
                .addGroup(ItemGroups.SPEEDY_PATH)
                .maxCount(1)
        ));

        STONE_PATH = registry.registerItem(_id("stone_path"), () -> ItemUtil.ofBlock(Blocks.STONE_PATH.getOrNull(), STANDARD_ITEM_SETTINGS));
        BRICK_PATH = registry.registerItem(_id("brick_path"), () -> ItemUtil.ofBlock(Blocks.BRICK_PATH.getOrNull(), STANDARD_ITEM_SETTINGS));
    }
}
