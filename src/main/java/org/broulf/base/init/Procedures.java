package org.broulf.base.init;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.broulf.base.init.transporter.BTransporter;

public class Procedures {
    // Change Dimension Procedure
    public static void changeDimension(Level level, Player player, LevelAccessor world) {
        ResourceKey<Level> targetDimension = Level.OVERWORLD;
        int x = player.getBlockX();
        int y = player.getBlockY();
        int z = player.getBlockZ();
        BlockPos posBelow = player.getPosition().down();
        BlockState blockStateBelow = player.level.getBlockState(posBelow);
        Block below = blockStateBelow.getBlock();

        if(!level.isClientSide()) {
            if(!player.isCrouching()) {
                MinecraftServer server = level.getServer();
                if(server != null) {
                    if (!(player.level.dimension() == targetDimension)) {
                        ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
                        System.out.println("The Player is currently in the Nether!");
                        System.out.println("Attempting to Teleport the player to the Overworld!");
                        player.changeDimension(overWorld, new BTransporter((ServerLevel) server.getLevel(targetDimension)));
                        player.teleportTo(x * 8, y, z * 8);
                        if (below.equals(Blocks.AIR)) {
                            level.setBlock(Blocks.DIRT);
                        }
                    } else if (player.level.dimension() == targetDimension) {
                        ServerLevel netherWorld = level.getServer().getLevel(Level.NETHER);
                        System.out.println("The Player is currently in the Overworld!");
                        System.out.println("Attempting to Teleport the player to the Nether!");
                        player.changeDimension(netherWorld, new BTransporter((ServerLevel) server.getLevel(Level.NETHER)));
                        player.teleportTo(x / 8, y, z / 8);

                    }
                }
            }
        }
    }

    // Help Am Lost Dimension Change Procedure
    public static void helpAmLost(Level level, Player player, LevelAccessor world) {
        ResourceKey<Level> targetDimension = Level.OVERWORLD;
        int x = world.getLevelData().getXSpawn();
        int y = world.getLevelData().getYSpawn();
        int z = world.getLevelData().getZSpawn();

        if(!level.isClientSide()) {
            if(!player.isCrouching()) {
                MinecraftServer server = level.getServer();
                if(server != null) {
                    if(!(player.level.dimension() == targetDimension)) {
                        ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
                        System.out.println("The Player was lost, let's help them out.");
                        player.changeDimension(overWorld, new BTransporter((ServerLevel) server.getLevel(Level.OVERWORLD)));
                        player.teleportTo(x, y, z);
                    }
                }
            }
        }
    }

    // Lightning Strike Procedure
    public static void lightningStrike(LevelAccessor world, double x, double y, double z, Entity entity){
        ItemStack pickaxe = ItemStack.EMPTY;
        if(!(world.getDifficulty() == Difficulty.PEACEFUL)) {
            if(world instanceof ServerLevel level) {
                LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(level);
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) x, (int) y, (int) z)));
                entityToSpawn.setVisualOnly(false);
                level.addFreshEntity(entityToSpawn);
            }
            if(entity instanceof LivingEntity _entity) {
                _entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 40, 1, (false), (false)));
            }
            entity.clearFire();
        }
    }

    // Small Explosion Procedure
    public static void explosionSmall(LevelAccessor world, double x, double y, double z){
        ItemStack pickaxe = ItemStack.EMPTY;
        if(!(world.getDifficulty() == Difficulty.PEACEFUL)) {
            if(world instanceof Level level && !level.isClientSide()) {
                level.explode(null, x, y, z, 1, Explosion.BlockInteraction.BREAK);
            }
        }
    }

    // Large Explosion Procedure
    public static void explosionLarge(LevelAccessor world, double x, double y, double z){
        ItemStack pickaxe = ItemStack.EMPTY;
        if(!(world.getDifficulty() == Difficulty.PEACEFUL)) {
            if(world instanceof Level level && !level.isClientSide()) {
                level.explode(null, x, y, z, 8, Explosion.BlockInteraction.BREAK);
            }
        }
    }
}
