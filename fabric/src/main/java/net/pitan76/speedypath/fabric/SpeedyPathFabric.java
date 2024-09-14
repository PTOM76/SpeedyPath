package net.pitan76.speedypath.fabric;

import net.pitan76.speedypath.SpeedyPath;
import net.fabricmc.api.ModInitializer;

public class SpeedyPathFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new SpeedyPath();
    }
}