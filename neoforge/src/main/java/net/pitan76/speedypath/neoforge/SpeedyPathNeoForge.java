package net.pitan76.speedypath.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.pitan76.speedypath.SpeedyPath;

@Mod(SpeedyPath.MOD_ID)
public class SpeedyPathNeoForge {
    public SpeedyPathNeoForge(ModContainer modContainer) {
        IEventBus eventBus = modContainer.getEventBus();

        new SpeedyPath();
    }
}