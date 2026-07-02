package me.verdi.create_cosmere_compat.datagen;

import net.minecraft.util.Tuple;
import java.nio.file.Paths;
import java.util.*;

public class TagFileReader {
    public static final String ITEMS_CAT = "items";
    public static final String BLOCKS_CAT = "blocks";
    public static final String ITEM_N_BLOCKS_CAT = "block_and_items";
    public static final String ENTITY_CAT = "entity";
    public static final Set<String> CATEGORIES = Set.of(ITEMS_CAT, BLOCKS_CAT, ITEM_N_BLOCKS_CAT, ENTITY_CAT);
    public static final String ID_CUSTOM_TAG_COPPER_STAGES = "copper_stages";
    public static final List<String> COPPER_STAGES = List.of(
            "exposed", "weathered", "oxidized", "waxed", "waxed_exposed", "waxed_weathered", "waxed_oxidized");
    public static final String ID_CUSTOM_TAG_COLORED = "colored";
    public static final List<String> COLORS = List.of(
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan",
            "purple", "blue", "brown", "green", "red", "black");

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

    static List<String> custom_id_generation(List<String> objects){
        List<String> new_objects = new ArrayList<>();
        for (String object : objects){
            if (!object.startsWith("[")) {
                new_objects.add(object);
                continue; //No custom generation
            }
            // Custom Generation
            int closingIndex = object.indexOf("]");
            String real_object = object.substring(closingIndex+1);
            switch (object.substring(1, closingIndex)) {
                case ID_CUSTOM_TAG_COPPER_STAGES:
                    new_objects.add(real_object);
                    for (String attachment : COPPER_STAGES)
                        new_objects.add(attachment + "_" + real_object);
                    break;
                case ID_CUSTOM_TAG_COLORED:
                    for (String color : COLORS)
                        new_objects.add(String.format(real_object, color));
                    break;
            }
        }
        return new_objects;
    }

    static TaggedObjectsByType readFromFile(String path){
        TaggedObjectsByType ret = new TaggedObjectsByType(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Scanner in;
        try {
            in = new Scanner(Paths.get("../"+path));
            in.useDelimiter(":");
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

            if (!CATEGORIES.contains(word)){
                ret.add(modId, items, blocks, entities);
                items = new ArrayList<>();
                blocks = new ArrayList<>();
                entities = new ArrayList<>();
                modId = word;
                continue;
            }
            if (!in.hasNext()) break;

            List<String> objects = List.of(in.next().trim().split("\\s+"));
            objects = custom_id_generation(objects);
            switch (word){
                case ITEMS_CAT:         items.addAll(objects);                              break;
                case BLOCKS_CAT:        blocks.addAll(objects);                             break;
                case ENTITY_CAT:        entities.addAll(objects);                           break;
                case ITEM_N_BLOCKS_CAT: items.addAll(objects);  blocks.addAll(objects);     break;
            }
        }
        ret.add(modId, items, blocks, entities);
        return ret;
    }
}
