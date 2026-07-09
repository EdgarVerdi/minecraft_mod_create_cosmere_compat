package me.verdi.create_cosmere_compat;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import static me.verdi.create_cosmere_compat.CreateCosmereCompatMod.MOD_ID;
import static me.verdi.create_cosmere_compat.ItemGroups.MISSING_CRUSHED_ORES;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("create_cosmere_compat_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.CRUSHED_CADMIUM.get().getDefaultInstance())
            .title(net.minecraft.network.chat.Component.translatable("creativetab.create_cosmere_compat_tab"))
            .displayItems((parameters, output) -> {
                // Add my crushed ores
                ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                // Add Create's missing crushed ores since it may not have displayed them themselves
                MISSING_CRUSHED_ORES.forEach(output::accept); // This technically contains the two above
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
