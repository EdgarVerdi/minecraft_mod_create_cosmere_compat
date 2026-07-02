package me.verdi.create_cosmere_compat;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import static me.verdi.create_cosmere_compat.CreateCosmereCompatMod.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    //My items:
    public static final RegistryObject<Item> CRUSHED_CADMIUM = ITEMS.register(
            "crushed_raw_cadmium", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRUSHED_CHROMIUM = ITEMS.register(
            "crushed_raw_chromium", () -> new Item(new Item.Properties()));


    // 3. A helper method to register this list to the game
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
