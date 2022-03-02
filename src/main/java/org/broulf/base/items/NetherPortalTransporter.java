package org.broulf.base.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.broulf.base.init.BaseCreativeTab;
import org.broulf.base.init.Procedures;

public class NetherPortalTransporter extends Item {

    public NetherPortalTransporter(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> rightClickInAir = super.use(level, player, hand);
        ItemStack itemstack = rightClickInAir.getObject();

        Procedures.changeDimension(level, player, level);
        return rightClickInAir;
    }
}
