package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static me.verdi.create_cosmere_compat.CreateCosmereCompatMod.MOD_ID;

public class ModEntityTagGenerator extends EntityTypeTagsProvider {

    public List<Tuple<String, List<String>>> containsMetalTaggedEntities;

    public ModEntityTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
                                 @Nullable ExistingFileHelper existingFileHelper,
                                 List<Tuple<String, List<String>>> containsMetalTaggedEntities
    ) {
        super(pOutput, pProvider, MOD_ID, existingFileHelper);
        this.containsMetalTaggedEntities = containsMetalTaggedEntities;
    }

    @Override
    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        TagKey<EntityType<?>> containsMetal = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("cosmere", "contains_metal"));
        var tag = this.tag(containsMetal).replace(false);
        ModDataGenerators.addTags(tag, containsMetalTaggedEntities);
    }
}