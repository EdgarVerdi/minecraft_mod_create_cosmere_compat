package me.verdi.create_cosmere_compat;

import me.verdi.create_cosmere_compat.CreateCosmereCompatMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    // 1. Create the register for items
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateCosmereCompatMod.MOD_ID);

    // 2. Add your specific items here
    public static final RegistryObject<Item> CRUSHED_TIN = ITEMS.register(
            "crushed_tin", () -> new Item(new Item.Properties()));

    // You can copy/paste the block above to add more items, like "crushed_atium", etc.

    // 3. A helper method to register this list to the game
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
