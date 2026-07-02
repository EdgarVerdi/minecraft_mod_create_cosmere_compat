package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static me.verdi.create_cosmere_compat.CreateCosmereCompatMod.MOD_ID;

public class ModItemTagGenerator extends net.minecraft.data.tags.ItemTagsProvider {

    public List<Tuple<String, List<String>>> containsMetalTaggedItems;

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                               CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper,
                               List<Tuple<String, List<String>>> containsMetalTaggedItems
    ) {
        super(pOutput, pLookupProvider, pBlockTags, MOD_ID, existingFileHelper);
        this.containsMetalTaggedItems = containsMetalTaggedItems;
    }

    @Override
    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        TagKey<Item> containsMetal = ItemTags.create(new ResourceLocation("cosmere", "contains_metal"));
        var tag = this.tag(containsMetal).replace(false);
        ModDataGenerators.addTags(tag, containsMetalTaggedItems);
    }
}