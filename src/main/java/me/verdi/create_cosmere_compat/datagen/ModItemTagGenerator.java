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

public class ModItemTagGenerator extends net.minecraft.data.tags.ItemTagsProvider {

    public List<Tuple<String, List<String>>> containsMetalTaggedItems;

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                               CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper,
                               List<Tuple<String, List<String>>> containsMetalTaggedItems
    ) {
        super(pOutput, pLookupProvider, pBlockTags, "create_cosmere_compat", existingFileHelper);
        this.containsMetalTaggedItems = containsMetalTaggedItems;
    }

    @Override
    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        TagKey<Item> containsMetal = ItemTags.create(new ResourceLocation("cosmere", "contains_metal"));
        var tag = this.tag(containsMetal).replace(false);
        for(Tuple<String, List<String>> tup : containsMetalTaggedItems){
            String modId = tup.getA();
            for (String object_id : tup.getB())
                tag.addOptional(new ResourceLocation(modId, object_id));
        }
    }
}