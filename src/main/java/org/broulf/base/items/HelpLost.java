package org.broulf.base.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.util.ITeleporter;
import org.broulf.base.init.BaseCreativeTab;
import org.broulf.base.init.ModItems;
import org.broulf.base.init.Procedures;

import java.util.function.Function;

public class HelpLost extends Item {

    public HelpLost(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> rightClickInAir = super.use(level, player, hand);
        ItemStack itemstack = rightClickInAir.getObject();

        Procedures.helpAmLost(level, player, level);
        return rightClickInAir;
    }
}
