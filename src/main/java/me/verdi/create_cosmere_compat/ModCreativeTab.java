package me.verdi.create_cosmere_compat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModCreativeTab {
    public static final String MOD_ID = CreateCosmereCompatMod.MOD_ID;

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final List<String> CREATE_CRUSHED_ORES = List.of("silver", "tin", "lead", "aluminum", "nickel");

    @SuppressWarnings({"removal"}) // Shuts IntelliJ up!
    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("create_cosmere_compat_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.CRUSHED_CADMIUM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                // Add my crushed ores
                ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));

                // Add Create's missing crushed ores since it may not have displayed them themselves
                CREATE_CRUSHED_ORES.forEach(name -> {
                    ResourceLocation item_id = new ResourceLocation("create", "crushed_raw_" + name);
                    Item item = ForgeRegistries.ITEMS.getValue(item_id);
                    if (item != null && item != Items.AIR)
                        output.accept(item);
                });
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
