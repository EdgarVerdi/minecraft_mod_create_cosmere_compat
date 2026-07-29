package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

// Tells Forge this class listens for Mod startup events
@Mod.EventBusSubscriber(modid = "create_cosmere_compat", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerators {

    public static final String CONTAINS_METAL_FOLDER_PATH = "src/main/resources/verdi_data/contains_metal";

    public static void printAllItemsOfMod(String mod_id){
        List<Item> modItems = ForgeRegistries.ITEMS.getEntries().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(mod_id))
                .map(Map.Entry::getValue)
                .toList();

        for(Item item : modItems) {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            assert id != null;
            System.out.println(id.getPath());
        }
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        TagFileReader.TaggedObjectsByType tObT = TagFileReader.readFromFile(CONTAINS_METAL_FOLDER_PATH);

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

        //Recipes provider
        generator.addProvider(event.includeServer(), new ModRecipeGenerator(packOutput));

        printAllItemsOfMod("tfmg");
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