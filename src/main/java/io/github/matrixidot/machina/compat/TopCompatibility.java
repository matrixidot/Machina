package io.github.matrixidot.machina.compat;

import io.github.matrixidot.machina.Machina;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TopCompatibility {
    public static void register() {
         if (!ModList.get().isLoaded("theoneprobe")) return;
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe probe;

        @Nullable
        @Override
        public Void apply(ITheOneProbe iTheOneProbe) {
            probe = iTheOneProbe;
            Machina.LOGGER.info("Enabled support for The One Probe");
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public ResourceLocation getID() {
                    return ResourceLocation.parse("machina:default");
                }

                @Override
                public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {

                }
            });
            return null;
        }
    }
}
