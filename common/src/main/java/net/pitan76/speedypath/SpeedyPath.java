package net.pitan76.speedypath;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.command.CommandRegistry;
import net.pitan76.mcpitanlib.api.registry.v2.CompatRegistryV2;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.speedypath.block.Blocks;
import net.pitan76.speedypath.command.SpeedyPathCommand;
import net.pitan76.speedypath.item.ItemGroups;
import net.pitan76.speedypath.item.Items;

public class SpeedyPath extends CommonModInitializer {
    public static final String MOD_ID = "speedypath";
    public static final String MOD_NAME = "Speedy Path";

    public static SpeedyPath INSTANCE;
    public static CompatRegistryV2 registry;

    @Override
    public void init() {
        INSTANCE = this;
        registry = super.registry;

        Config.init();
        CommandRegistry.register("speedypath", new SpeedyPathCommand());

        if (Config.addSpeedPathBlocks) {
            ItemGroups.init();
            Blocks.init();
            Items.init();
        }

    }

    // ----
    /**
     * @param path The path of the id
     * @return The id
     */
    public static CompatIdentifier _id(String path) {
        return CompatIdentifier.of(MOD_ID, path);
    }

    @Override
    public String getId() {
        return MOD_ID;
    }

    @Override
    public String getName() {
        return MOD_NAME;
    }
}