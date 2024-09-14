package net.pitan76.speedypath.item;

import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;

import static net.pitan76.speedypath.SpeedyPath._id;
import static net.pitan76.speedypath.SpeedyPath.registry;

public class ItemGroups {
    public static CreativeTabBuilder SPEEDY_PATH = CreativeTabBuilder.create(_id("speedy_path"))
            .setIcon(() -> ItemStackUtil.create(Items.STONE_PATH.get()));

    public static void init() {
        registry.registerItemGroup(SPEEDY_PATH);
    }
}
