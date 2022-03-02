package org.broulf.base.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.broulf.base.init.ModBlocks;

import java.util.Random;
import net.minecraft.world.level.block.TntBlock;

public class LavaWetSponge extends Block {

    // Super
    public LavaWetSponge(Properties properties) {
        super(properties);
    }

    // Bouncy
    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos pos, Entity entity, float num) {
        super.fallOn(level, blockState, pos, entity, num * 0.5F);
    }
    @Override
    public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(blockGetter, entity);
        } else {
            this.bounceUp(entity);
        }
    }
    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vec3.x, -vec3.y * (double)0.66F * d0, vec3.z);
        }
    }

    // Wet Sponge Stuff
    public void onPlace(BlockState blockState, Level level, BlockPos pos, BlockState stateBlock, boolean bool) {
        if (level.getBiome(pos).coldEnoughToSnow(pos)) {
            level.setBlock(pos, ModBlocks.LAVA_SPONGE.get().defaultBlockState(), 2);
            level.levelEvent(2009, pos, 0);
            level.playSound((Player)null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }
    public void animateTick(BlockState blockState, Level level, BlockPos pos, Random rand) {
        Direction direction = Direction.getRandom(rand);
        if (direction != Direction.UP) {
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockState.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {
                double d0 = (double)pos.getX();
                double d1 = (double)pos.getY();
                double d2 = (double)pos.getZ();
                if (direction == Direction.DOWN) {
                    d1 -= 0.05D;
                    d0 += rand.nextDouble();
                    d2 += rand.nextDouble();
                } else {
                    d1 += rand.nextDouble() * 0.8D;
                    if (direction.getAxis() == Direction.Axis.X) {
                        d2 += rand.nextDouble();
                        if (direction == Direction.EAST) {
                            ++d0;
                        } else {
                            d0 += 0.05D;
                        }
                    } else {
                        d0 += rand.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++d2;
                        } else {
                            d2 += 0.05D;
                        }
                    }
                }
                level.addParticle(ParticleTypes.DRIPPING_LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
