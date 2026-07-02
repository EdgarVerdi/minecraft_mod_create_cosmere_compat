package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public List<Tuple<String, List<String>>> containsMetalTaggedBlocks;

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper, List<Tuple<String, List<String>>> containsMetalTaggedBlocks) {
        super(output, lookupProvider, "create_cosmere_compat", existingFileHelper);
        this.containsMetalTaggedBlocks = containsMetalTaggedBlocks;
    }

    @Override
    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        TagKey<Block> containsMetal = BlockTags.create(new ResourceLocation("cosmere", "contains_metal"));
        var tag = this.tag(containsMetal).replace(false);
        for(Tuple<String, List<String>> tup : containsMetalTaggedBlocks){
            String modId = tup.getA();
            for (String object_id : tup.getB())
                tag.addOptional(new ResourceLocation(modId, object_id));
        }
    }
}