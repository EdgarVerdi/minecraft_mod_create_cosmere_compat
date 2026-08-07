package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.util.Tuple;
import java.io.File;
import java.util.*;

public class TagFileReader {
    public static final String ITEMS_CAT = "items";
    public static final String BLOCKS_CAT = "blocks";
    public static final String ITEM_N_BLOCKS_CAT = "block_and_items";
    public static final String ENTITY_CAT = "entities";
    public static final Set<String> CATEGORIES = Set.of(ITEMS_CAT, BLOCKS_CAT, ITEM_N_BLOCKS_CAT, ENTITY_CAT);
    public static final String ID_CUSTOM_TAG_COPPER_STAGES = "copper_stages";
    public static final List<String> COPPER_STAGES = List.of(
            "exposed", "weathered", "oxidized", "waxed", "waxed_exposed", "waxed_weathered", "waxed_oxidized");
    public static final String ID_CUSTOM_TAG_COLORED = "colored";
    public static final List<String> COLORS = List.of(
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan",
            "purple", "blue", "brown", "green", "red", "black");
    public static final String ID_CUSTOM_TAG_ARMOR = "armor";
    public static final List<String> ARMOR_PIECES = List.of("helmet", "chestplate", "leggings", "boots");
    public static final String ID_CUSTOM_TAG_TOOLS = "tools";
    public static final List<String> TOOLS  = List.of("sword", "pickaxe", "axe", "shovel", "hoe");
    public static final String ID_CUTS = "cuts";
    public static final List<String> CUTS  = List.of("slab", "stairs");
    public static final String ID_CUTS_PLUS = "cuts+";
    public static final List<String> CUTS_PLUS  = List.of("slab", "stairs", "wall");

    public record TaggedObjectsByType(
            List<Tuple<String, List<String>>> items,
            List<Tuple<String, List<String>>> blocks,
            List<Tuple<String, List<String>>> entities
    ){
        public void add(String modId, List<String> items, List<String> blocks, List<String> entities){
            if (!items.isEmpty())
                this.items.add(new Tuple<>(modId, items));
            if (!blocks.isEmpty())
                this.blocks.add(new Tuple<>(modId, blocks));
            if (!entities.isEmpty())
                this.entities.add(new Tuple<>(modId, entities));
        }
    }

    static void custom_id_generation(String real_object, String change_id, List<String> return_objects){
        switch (change_id) {
            case ID_CUSTOM_TAG_COPPER_STAGES:
                return_objects.add(real_object);
                for (String attachment : COPPER_STAGES)
                    return_objects.add(attachment + "_" + real_object);
                break;
            case ID_CUSTOM_TAG_COLORED:
                for (String color : COLORS)
                    return_objects.add(String.format(real_object, color));
                break;
            case ID_CUSTOM_TAG_ARMOR:
                for (String armor_piece : ARMOR_PIECES)
                    return_objects.add(real_object+"_"+armor_piece);
                break;
            case ID_CUSTOM_TAG_TOOLS:
                for (String tool : TOOLS)
                    return_objects.add(real_object+"_"+tool);
                break;
            case ID_CUTS:
                return_objects.add(real_object);
                for (String cut : CUTS)
                    return_objects.add(real_object+"_"+cut);
                break;
            case ID_CUTS_PLUS:
                return_objects.add(real_object);
                for (String cut : CUTS_PLUS)
                    return_objects.add(real_object+"_"+cut);
                break;
        }
    }

    static List<String> custom_id_generation(List<String> objects){
        List<String> new_objects = new ArrayList<>();
        for (String object : objects){
            if (!object.startsWith("[")) {
                new_objects.add(object);
                continue; //No custom generation
            }
            // Custom Generation
            int closingIndex = object.indexOf("]");
            List<String> real_objects = List.of(object.substring(closingIndex+1));
            String[] change_ids =  object.substring(1, closingIndex).split(",");
            for (int i = change_ids.length-1; i>0; i--) {
                List<String> ret_objects = new ArrayList<>();
                for (String real_object : real_objects)
                    custom_id_generation(real_object, change_ids[i], ret_objects);
                real_objects = ret_objects;
            }
            for (String real_object : real_objects)
                custom_id_generation(real_object, change_ids[0], new_objects);
        }
        return new_objects;
    }

    static List<File> listFilesForFolder(String folder_path) {
        List<File> ret = new ArrayList<>();
        listFilesForFolder(new File("../"+folder_path), ret);
        return ret;
    }

    static void listFilesForFolder(final File folder, List<File> filesConsumer) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory())
                listFilesForFolder(fileEntry, filesConsumer);
            else
                filesConsumer.add(fileEntry);
        }
    }

    static TaggedObjectsByType readFromFile(String folder_path){
        TaggedObjectsByType ret = new TaggedObjectsByType(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Scanner in;
        for (File path : listFilesForFolder(folder_path)) {
            try {
                in = new Scanner(path);
                in.useDelimiter("[<>]");
            } catch (Exception e) {
                System.out.println("Could not read text file!");
                return ret;
            }

            String modId = "minecraft";
            List<String> items = new ArrayList<>();
            List<String> blocks = new ArrayList<>();
            List<String> entities = new ArrayList<>();
            while (in.hasNext()) {
                String word = in.next().trim();
                if (word.isEmpty()) continue;

                if (!CATEGORIES.contains(word)) {
                    ret.add(modId, items, blocks, entities);
                    items = new ArrayList<>();
                    blocks = new ArrayList<>();
                    entities = new ArrayList<>();
                    modId = word;
                    continue;
                }
                if (!in.hasNext()) break;

                List<String> objects = List.of(in.next().replaceFirst("#.*", "").trim().split("\\s+"));
                objects = custom_id_generation(objects);
                switch (word) {
                    case ITEMS_CAT:
                        items.addAll(objects);
                        break;
                    case BLOCKS_CAT:
                        blocks.addAll(objects);
                        break;
                    case ENTITY_CAT:
                        entities.addAll(objects);
                        break;
                    case ITEM_N_BLOCKS_CAT:
                        items.addAll(objects);
                        blocks.addAll(objects);
                        break;
                }
            }
            ret.add(modId, items, blocks, entities);
        }
        return ret;
    }
}
