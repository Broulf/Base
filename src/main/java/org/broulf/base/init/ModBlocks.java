package org.broulf.base.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.broulf.base.BaseMod;
import org.broulf.base.blocks.*;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BaseMod.MOD_ID);

    public static final RegistryObject<Block> BASE_BLOCK = registerBlock("base_block",
            () -> new BaseBlock(BlockBehaviour.Properties.of(Material.STONE)),
            BaseCreativeTab.BASE_TAB);
    public static final RegistryObject<Block> ADVANCED_SPONGE = registerBlock("advanced_sponge",
            () -> new AdvancedSponge(BlockBehaviour.Properties.of(Material.SPONGE).sound(SoundType.GRASS)),
            BaseCreativeTab.BASE_TAB);
    public static final RegistryObject<Block> ADVANCED_WET_SPONGE = registerBlock("advanced_wet_sponge",
            () -> new AdvancedWetSponge(BlockBehaviour.Properties.of(Material.SPONGE).sound(SoundType.GRASS)),
            BaseCreativeTab.BASE_TAB);
    public static final RegistryObject<Block> LAVA_SPONGE = registerBlock("lava_sponge",
            () -> new LavaSponge(BlockBehaviour.Properties.of(Material.SPONGE).sound(SoundType.GRASS)),
            BaseCreativeTab.BASE_TAB);
    public static final RegistryObject<Block> LAVA_WET_SPONGE = registerBlock("lava_wet_sponge",
            () -> new LavaWetSponge(BlockBehaviour.Properties.of(Material.SPONGE).sound(SoundType.GRASS)),
            BaseCreativeTab.BASE_TAB);
    public static final RegistryObject<Block> ADVANCED_LAVA_SPONGE = registerBlock("advanced_lava_sponge",
            () -> new AdvancedLavaSponge(BlockBehaviour.Properties.of(Material.SPONGE).sound(SoundType.GRASS)),
            BaseCreativeTab.BASE_TAB);
    public static final RegistryObject<Block> ADVANCED_LAVA_WET_SPONGE = registerBlock("advanced_lava_wet_sponge",
            () -> new AdvancedLavaWetSponge(BlockBehaviour.Properties.of(Material.SPONGE).sound(SoundType.GRASS)),
            BaseCreativeTab.BASE_TAB);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
