package org.broulf.base.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.broulf.base.init.BaseCreativeTab;
import org.broulf.base.init.Procedures;

import static net.minecraft.world.effect.MobEffect.*;

public class AdvancedItem extends Item {
    // Item properties
    public AdvancedItem(Properties properties) {
        super(properties
                .durability(128)
                .tab(BaseCreativeTab.BASE_TAB));
    }

    // Right click in air event
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> rightClickInAir = super.use(world, entity, hand);
        ItemStack itemstack = rightClickInAir.getObject();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        Procedures.lightningStrike(world, x, y, z, entity);
        Procedures.explosionLarge(world, x, y, z);
        return rightClickInAir;
    }
    // Hit enemy event
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity entity, LivingEntity sourceEntity) {
        boolean hurtEntity = super.hurtEnemy(itemStack, entity, sourceEntity);

        Procedures.lightningStrike(entity.level, entity.getX(), entity.getY(), entity.getZ(), entity);
        Procedures.explosionLarge(entity.level, entity.getX(), entity.getY(), entity.getZ());
        return hurtEntity;
    }
}
