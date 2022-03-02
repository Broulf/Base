package org.broulf.base.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import org.broulf.base.init.ModBlocks;

import java.util.Queue;

public class AdvancedLavaSponge extends Block {

    // Basic Information
    public static final int MAX_DEPTH = 16;
    public static final int MAX_COUNT = 256;

    // Super
    public AdvancedLavaSponge(BlockBehaviour.Properties properties) {
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

    // Sponge Stuff
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean bool) {
        if (!state1.is(state.getBlock())) {
            this.tryAbsorbLava(level, pos);
        }
    }
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean bool) {
        this.tryAbsorbLava(level, pos);
        super.neighborChanged(state, level, pos, block, pos1, bool);
    }
    protected void tryAbsorbLava(Level level, BlockPos pos) {
        if (this.removeLavaBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, ModBlocks.ADVANCED_LAVA_WET_SPONGE.get().defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.LAVA.defaultBlockState()));
        }
    }
    private boolean removeLavaBreadthFirstSearch(Level level, BlockPos blockPos) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(blockPos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = queue.poll();
            BlockPos blockpos = tuple.getA();
            int j = tuple.getB();

            for(Direction direction : Direction.values()) {
                BlockPos blockpos1 = blockpos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos1);
                FluidState fluidstate = level.getFluidState(blockpos1);
                Material material = blockstate.getMaterial();
                if (fluidstate.is(FluidTags.LAVA)) {
                    if (blockstate.getBlock() instanceof BucketPickup && !((BucketPickup)blockstate.getBlock()).pickupBlock(level, blockpos1, blockstate).isEmpty()) {
                        ++i;
                        if (j < 16) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    } else if (blockstate.getBlock() instanceof LiquidBlock) {
                        level.setBlock(blockpos1, Blocks.AIR.defaultBlockState(), 3);
                        ++i;
                        if (j < 16) {
                            queue.add(new Tuple<>(blockpos1, j + 1));
                        }
                    }
                }
            }
            if (i > 256) {
                break;
            }
        }
        return i > 0;
    }
}
