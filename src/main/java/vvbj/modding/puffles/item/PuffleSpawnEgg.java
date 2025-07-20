package vvbj.modding.puffles.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import vvbj.modding.puffles.entity.EntityRegistry;
import vvbj.modding.puffles.entity.PuffleEntity;
import vvbj.modding.puffles.entity.PuffleVariant;

import java.util.Objects;
import java.util.Optional;

public class PuffleSpawnEgg extends Item {

    private final PuffleVariant variant;

    public PuffleSpawnEgg(PuffleVariant variant) {
        super(new Settings());
        this.variant = variant;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.SPAWNER)) {
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                if (blockEntity instanceof MobSpawnerBlockEntity) {
                    MobSpawnerBlockEntity mobSpawnerBlockEntity = (MobSpawnerBlockEntity)blockEntity;

                    mobSpawnerBlockEntity.setEntityType(EntityRegistry.PUFFLE, world.getRandom());
                    blockEntity.markDirty();
                    world.updateListeners(blockPos, blockState, blockState, 3);
                    world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                    itemStack.decrement(1);
                    return ActionResult.CONSUME;
                }
            }

            BlockPos blockPos2;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.offset(direction);
            }
            PuffleEntity entity = EntityRegistry.PUFFLE.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP);
            if (entity != null) {
                entity.setVariant(variant);
                itemStack.decrement(1);
                world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }

            return ActionResult.CONSUME;
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        } else {
            BlockHitResult blockHitResult2 = blockHitResult;
            BlockPos blockPos = blockHitResult2.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
                return TypedActionResult.pass(itemStack);
            } else if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos, blockHitResult2.getSide(), itemStack)) {
                PuffleEntity entity = EntityRegistry.PUFFLE.spawnFromItemStack((ServerWorld)world, itemStack, user, blockPos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) {
                    return TypedActionResult.pass(itemStack);
                } else {
                    if (!user.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    entity.setVariant(variant);

                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    world.emitGameEvent(user, GameEvent.ENTITY_PLACE, entity.getPos());
                    return TypedActionResult.consume(itemStack);
                }
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.getWorld().isClient) {
            if (entity instanceof PuffleEntity puffle) {
                spawnBaby(user, puffle, EntityRegistry.PUFFLE, (ServerWorld) user.getWorld(), puffle.getPos(), stack);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public boolean isOfSameEntityType(@Nullable NbtCompound nbt, EntityType<?> type) {
        return Objects.equals(EntityRegistry.PUFFLE, type);
    }

    public FeatureSet getRequiredFeatures() {
        return EntityRegistry.PUFFLE.getRequiredFeatures();
    }

    public Optional<MobEntity> spawnBaby(PlayerEntity user, PuffleEntity entity, EntityType<? extends MobEntity> entityType, ServerWorld world, Vec3d pos, ItemStack stack) {
        if (!this.isOfSameEntityType(stack.getNbt(), entityType)) {
            return Optional.empty();
        } else {
            MobEntity child = entity.createChild(world, entity);
            if(child instanceof PuffleEntity childPuffle) {
                childPuffle.setBaby(true);
                if (!childPuffle.isBaby()) {
                    return Optional.empty();
                } else {
                    childPuffle.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
                    world.spawnEntityAndPassengers(childPuffle);
                    if (stack.hasCustomName()) {
                        childPuffle.setCustomName(stack.getName());
                    }

                    if (!user.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }

                    return Optional.of(childPuffle);
                }
            }
            return Optional.empty();
        }
    }
}
