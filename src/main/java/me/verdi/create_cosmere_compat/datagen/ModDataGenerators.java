package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// Tells Forge this class listens for Mod startup events
@Mod.EventBusSubscriber(modid = "create_cosmere_compat", bus = Mod.EventBusSubscriber.Bus.MOD) // Put your Mod ID here
public class ModDataGenerators {

    public static final String CONTAINS_METAL_TXT_PATH = "src/main/resources/verdi_data/contains_metal.vmc";

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        TagFileReader.TaggedObjectsByType tObT = TagFileReader.readFromFile(CONTAINS_METAL_TXT_PATH);

        // Block Tag Provider
        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper, tObT.blocks()));

        // Item Tag Provider
        generator.addProvider(event.includeServer(),
                new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(),
                        existingFileHelper, tObT.items()));

        // Entity Tag Provider
        generator.addProvider(event.includeServer(),
                new ModEntityTagGenerator(packOutput, lookupProvider, existingFileHelper, tObT.entities()));

    }

    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    public static <T> void addTags(IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> tag, Iterable<Tuple<String, List<String>>> toTagByMod){
        for(Tuple<String, List<String>> tup : toTagByMod){
            String modId = tup.getA();
            for (String object_id : tup.getB())
                tag.addOptional(new ResourceLocation(modId, object_id));
        }
    }
}