package me.verdi.create_cosmere_compat.datagen;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import me.verdi.create_cosmere_compat.ItemGroups;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import static me.verdi.create_cosmere_compat.CreateCosmereCompatMod.MOD_ID;

// Notice we implement IConditionBuilder here for future-proofing!
public class ModRecipeGenerator extends RecipeProvider implements IConditionBuilder {

    public ModRecipeGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        buildCrushingOreBlockRecipes(pWriter);
    }


    @SuppressWarnings({"removal"})
    protected void buildCrushingOreBlockRecipes(Consumer<FinishedRecipe> pWriter) {
        ItemStack xp_nugget = AllItems.EXP_NUGGET.asStack(2);
        ItemStack cobble = new ItemStack(Items.COBBLESTONE, 1);
        ItemStack deepslate = new ItemStack(Items.DEEPSLATE, 1);
        for (int i = 0; i < ItemGroups.COSMERE_ORE_NAMES.size(); i++) {
            ItemStack crushed_raw_ore = new ItemStack(ItemGroups.CRUSHED_ORES.get(i), 1);
            ItemStack crushed_raw_ore2 = new ItemStack(ItemGroups.CRUSHED_ORES.get(i), 2);

            new ProcessingRecipeBuilder<>(
                    CrushingRecipe::new,
                    new ResourceLocation(MOD_ID, ItemGroups.ORE_BLOCK_NAMES.get(i)))
                    .require(Ingredient.of(ItemGroups.ORE_BLOCKS.get(i)))
                    .output(crushed_raw_ore)
                    .output(0.75f, crushed_raw_ore)
                    .output(0.75f, xp_nugget)
                    .output(0.125f, cobble)
                    .duration(250)
                    .build(pWriter);

            new ProcessingRecipeBuilder<>(
                    CrushingRecipe::new,
                    new ResourceLocation(MOD_ID, ItemGroups.DEEPSLATE_ORE_BLOCK_NAMES.get(i)))
                    .require(Ingredient.of(ItemGroups.DEEPSLATE_ORE_BLOCKS.get(i)))
                    .output(crushed_raw_ore2)
                    .output(0.75f, crushed_raw_ore)
                    .output(0.75f, xp_nugget)
                    .output(0.125f, deepslate)
                    .duration(350)
                    .build(pWriter);
        }
    }
}