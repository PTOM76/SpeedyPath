package net.pitan76.speedypath.item;

import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;

import static net.pitan76.speedypath.SpeedyPath._id;
import static net.pitan76.speedypath.SpeedyPath.registry;

public class ItemGroups {

    public static CreativeTabBuilder EXAMPLE_ITEMS = CreativeTabBuilder.create(_id("example_items"))
            .setIcon(() -> ItemStackUtil.create(Items.EXAMPLE_ITEM.get()));

    public static void init() {
        registry.registerItemGroup(EXAMPLE_ITEMS);
    }
}
