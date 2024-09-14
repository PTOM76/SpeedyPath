package net.pitan76.speedypath.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.pitan76.speedypath.SpeedyPath;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SpeedyPath.MOD_ID)
public class SpeedyPathForge {
    public SpeedyPathForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EventBuses.registerModEventBus(SpeedyPath.MOD_ID, modEventBus);
        new SpeedyPath();
    }
}