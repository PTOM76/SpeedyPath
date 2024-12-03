package net.pitan76.speedypath.item;

import net.minecraft.item.Item;
import net.pitan76.mcpitanlib.api.item.v2.ItemSettingsBuilder;
import net.pitan76.speedypath.block.Blocks;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.item.ItemUtil;

import static net.pitan76.speedypath.SpeedyPath._id;
import static net.pitan76.speedypath.SpeedyPath.registry;

public class Items {

    public static final ItemSettingsBuilder STANDARD_ITEM_SETTINGS = new ItemSettingsBuilder(_id("wrench_for_path"))
            .addGroup(ItemGroups.SPEEDY_PATH);

    public static RegistryResult<Item> WRENCH_FOR_PATH;
    public static RegistryResult<Item> STONE_PATH;
    public static RegistryResult<Item> BRICK_PATH;

    public static void init() {
        WRENCH_FOR_PATH = registry.registerItem(_id("wrench_for_path"), () -> new WrenchForPath(CompatibleItemSettings.of(_id("wrench_for_path"))
                .addGroup(ItemGroups.SPEEDY_PATH)
                .maxCount(1)
        ));

        STONE_PATH = registry.registerItem(_id("stone_path"), () -> ItemUtil.create(Blocks.STONE_PATH.getOrNull(), STANDARD_ITEM_SETTINGS.build(_id("stone_path"))));
        BRICK_PATH = registry.registerItem(_id("brick_path"), () -> ItemUtil.create(Blocks.BRICK_PATH.getOrNull(), STANDARD_ITEM_SETTINGS.build(_id("brick_path"))));
    }
}
