package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "create_cosmere_compat", existingFileHelper); // Put your exact Mod ID here
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // You can generate block tags here later!
        // tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.YOUR_BLOCK.get());
    }
}