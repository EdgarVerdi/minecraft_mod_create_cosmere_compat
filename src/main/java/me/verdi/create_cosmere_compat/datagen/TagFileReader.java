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

    static TaggedObjectsByType readFromFile(String path){
        TaggedObjectsByType ret = new TaggedObjectsByType(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Scanner in;
        try {
            in = new Scanner(Paths.get(path));
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
            switch (word){
                case ITEMS_CAT: items.addAll(objects);
                case BLOCKS_CAT: blocks.addAll(objects);
                case ENTITY_CAT: entities.addAll(objects);
                case ITEM_N_BLOCKS_CAT: items.addAll(objects);  blocks.addAll(objects);
            }
        }
        ret.add(modId, items, blocks, entities);
        return ret;
    }
}
