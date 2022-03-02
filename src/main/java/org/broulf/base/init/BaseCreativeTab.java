package org.broulf.base.init;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BaseCreativeTab {

    public static final CreativeModeTab BASE_TAB = new CreativeModeTab("base_tab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ADVANCED_ITEM.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> item) {
            item.add(ModItems.BASE_ITEM.get().getDefaultInstance());
            item.add(ModItems.ADVANCED_ITEM.get().getDefaultInstance());
            item.add(ModItems.LOST_ITEM.get().getDefaultInstance());
            item.add(ModItems.NETHER_PORTAL_TRANSPORTER_ITEM.get().getDefaultInstance());
            super.fillItemList(item);
        }
    };
}
