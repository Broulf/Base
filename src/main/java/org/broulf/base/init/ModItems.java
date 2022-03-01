package org.broulf.base.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.broulf.base.BaseMod;
import org.broulf.base.items.AdvancedItem;
import org.broulf.base.items.BaseItem;
import org.broulf.base.items.HelpLost;
import org.broulf.base.items.NetherPortalTransporter;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BaseMod.MOD_ID);

    public static final RegistryObject<Item> BASE_ITEM = ITEMS.register("base_item",
            () -> new BaseItem(new Item.Properties()));

    public static final RegistryObject<Item> ADVANCED_ITEM = ITEMS.register("advanced_item",
            () -> new AdvancedItem(new Item.Properties()));

    public static final RegistryObject<Item> NETHER_PORTAL_TRANSPORTER_ITEM = ITEMS.register("nether_portal_transporter_item",
            () -> new NetherPortalTransporter(new Item.Properties()));

    public static final RegistryObject<Item> LOST_ITEM = ITEMS.register("lost_item",
            () -> new HelpLost(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
