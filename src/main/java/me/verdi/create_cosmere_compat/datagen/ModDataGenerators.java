package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

// Tells Forge this class listens for Mod startup events
@Mod.EventBusSubscriber(modid = "create_cosmere_compat", bus = Mod.EventBusSubscriber.Bus.MOD) // Put your Mod ID here
public class ModDataGenerators {

    public static final String CONTAINS_METAL_TXT_PATH = "src/main/resources/verdi_data/contains_metal.txt";

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        TagFileReader.TaggedObjectsByType tObT = TagFileReader.readFromFile(CONTAINS_METAL_TXT_PATH);

        // 1. Create the Block Tag Provider
        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper, tObT.blocks()));

        // 2. Create the Item Tag Provider
        generator.addProvider(event.includeServer(),
                new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(),
                        existingFileHelper, tObT.items()));


    }
}