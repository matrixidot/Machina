package io.github.matrixidot.machina.datagen;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.blocks.power.stirling_generator.StirlingGenerator;
import io.github.matrixidot.machina.blocks.processing.ore_crusher.OreCrusher;
import io.github.matrixidot.machina.init.BlocksInit;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModTranslations extends LanguageProvider {
    public ModTranslations(PackOutput output, String locale) {
        super(output, Machina.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(BlocksInit.STIRLING_GENERATOR.block(), "Stirling Generator");
        add(StirlingGenerator.SCREEN, "Stirling Generator");
        add(BlocksInit.ORE_CRUSHER.block(), "Ore Crusher");
        add(OreCrusher.SCREEN, "Ore Crusher");



        add("tab.machina_machines", "Machina: Machines");
    }
}
