package me.verdi.create_cosmere_compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import static me.verdi.create_cosmere_compat.CreateCosmereCompatMod.MOD_ID;
import java.util.List;

@SuppressWarnings({"removal"})
public class ItemGroups {

    public static final List<String> COSMERE_ORE_NAMES = List.of("zinc", "silver", "tin", "lead", "aluminum", "nickel", "cadmium", "chromium");

    public static final List<Item> CRUSHED_ORES = COSMERE_ORE_NAMES.stream().map(name -> {
        String modId = name.equals("cadmium") | name.equals("chromium") ? MOD_ID : "create";
        ResourceLocation item_id = new ResourceLocation(modId, "crushed_raw_" + name);
        return ForgeRegistries.ITEMS.getValue(item_id);
    }).toList();

    public static final List<Item> MISSING_CRUSHED_ORES =  CRUSHED_ORES.subList(1, CRUSHED_ORES.size());


    public static final List<String> ORE_BLOCK_NAMES = COSMERE_ORE_NAMES.stream().map(name -> name + "_ore").toList();

    public static final List<Item> ORE_BLOCKS = ORE_BLOCK_NAMES.stream().map(name ->
            ForgeRegistries.ITEMS.getValue(new ResourceLocation("cosmere", name))).toList();


    public static final List<String> DEEPSLATE_ORE_BLOCK_NAMES = ORE_BLOCK_NAMES.stream().map(name -> "deepslate_" + name).toList();

    public static final List<Item> DEEPSLATE_ORE_BLOCKS = DEEPSLATE_ORE_BLOCK_NAMES.stream().map(name ->
            ForgeRegistries.ITEMS.getValue(new ResourceLocation("cosmere",  name))).toList();





}
