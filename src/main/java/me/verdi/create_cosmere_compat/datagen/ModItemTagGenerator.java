package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends net.minecraft.data.tags.ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, "create_cosmere_compat", existingFileHelper); // Put your exact Mod ID here
    }

    @Override
    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    protected void addTags(HolderLookup.Provider pProvider) {
        // 1. Define the Tag Key you want to generate
        TagKey<Item> containsMetal = ItemTags.create(new ResourceLocation("cosmere", "contains_metal"));
        //TODO write my own parser

        // 2. Generate the Tag!
        this.tag(containsMetal)
                .replace(false)
                // Add explicit single items
                //.addOptional(new ResourceLocation("cosmere", "iron_ingot"))
                //.addOptional(new ResourceLocation("cosmere", "steel_ingot"))
                .addOptional(new ResourceLocation("create", "smart_chute"));
                // Or pull in massive lists using Forge tags!
                //.addOptionalTag(new ResourceLocation("forge", "ingots/tin"))
                //.addOptionalTag(new ResourceLocation("forge", "ingots/lead"));
    }
}