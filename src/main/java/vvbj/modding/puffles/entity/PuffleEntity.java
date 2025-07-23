package vvbj.modding.puffles.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vvbj.modding.puffles.Sounds;
import vvbj.modding.puffles.item.ItemRegistry;

public class PuffleEntity extends TameableEntity {

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(PuffleEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public PuffleEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(0, new SitGoal(this));
        goalSelector.add(1, new AnimalMateGoal(this, 1.1));
        goalSelector.add(2, new FollowOwnerGoal(this, 1.7, 3, 3, false));
        goalSelector.add(3, new FollowParentGoal(this, 1));
        goalSelector.add(4, new WanderAroundFarGoal(this, 1));
        goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4));
        goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        PuffleEntity child = EntityRegistry.PUFFLE.create(world);
        if(child != null){
            if(this.isTamed()){
                child.setTamed(true);
                child.setOwnerUuid(getOwnerUuid());
            }

            child.setVariant(Util.getRandom(PuffleVariant.values(), this.random));
        }
        return child;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.PUFFLE_DEATH_SOUND;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    public PuffleVariant getVariant(){
        return PuffleVariant.byId(getVariantData() & 255);
    }

    private int getVariantData(){
        return dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(PuffleVariant variant){
        dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        setVariant(Util.getRandom(PuffleVariant.values(), this.random));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("variant", getVariant().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setVariant(PuffleVariant.byId(nbt.getInt("variant")));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if(stack.getItem() == ItemRegistry.O_BERRY){
            if(!isTamed()) {
                if (this.getWorld().isClient) {
                    return ActionResult.CONSUME;
                } else {
                    if (!player.isCreative())
                        stack.decrement(1);

                    this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1f, 1f);
                    if(this.random.nextInt(3) == 0) {
                        // Tame
                        setOwner(player);
                        navigation.recalculatePath();
                        setTarget(null);
                        getWorld().sendEntityStatus(this, (byte) 7);

                    }else
                        this.getWorld().sendEntityStatus(this, (byte)6);
                    return ActionResult.CONSUME;
                }
            }else{
                if(getOwner() != null && !getOwner().getUuid().equals(player.getUuid()))
                    return ActionResult.FAIL;
                if(getHealth() < getMaxHealth()) {
                    if (this.getWorld().isClient)
                        return ActionResult.CONSUME;
                    else {
                        // Feed I guess
                        if (!player.isCreative())
                            stack.decrement(1);
                        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1f, 1f);
                        heal(10f);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }else if(stack.getItem() == ItemRegistry.PUFFLE_BOX) {
            return ActionResult.PASS;
        }else{
            if(isTamed() && getOwner() != null && getOwner().getUuid().equals(player.getUuid())){
                if(getWorld().isClient)
                    return ActionResult.CONSUME;
                boolean sitting = !isSitting();
                setSitting(sitting);
                setInSittingPose(sitting);
                player.sendMessage(getName().copy().append(Text.literal(String.format(" is now %s", sitting ? "sitting":"following you"))), true);
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ItemRegistry.O_BERRY);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10.0);
        }
    }
}
