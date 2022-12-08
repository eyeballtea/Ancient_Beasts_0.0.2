package net.eyeballtea.miniman.entities;
import net.eyeballtea.miniman.MiniMan;
import net.eyeballtea.miniman.entities.variant.LittleVariant;
import net.eyeballtea.miniman.init.EntityInit;
import net.eyeballtea.miniman.init.ItemInit;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Markings;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.UUID;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.variant;


public class LittleGuy extends TamableAnimal {
    public LittleGuy(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getOwnerUUID() != null) {
            tag.putUUID("Owner", this.getOwnerUUID());
        }
        tag.putInt("Variant", this.getTypeVariant());
        tag.putBoolean("Sitting", this.isOrderedToSit());
    }

    //baby mini manticores
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        LittleGuy lilguy = EntityInit.LITTLEGUY.get().create(level);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            lilguy.setOwnerUUID(uuid);
            lilguy.setTame(true);
        }
        LittleVariant variant = Util.getRandom(LittleVariant.values(), this.random);
        lilguy.setVariant(variant);
        return lilguy;
    }
    @Override
    public boolean isFood(ItemStack itemStack){
        Item item = itemStack.getItem();
        return item.isEdible() && itemStack.getFoodProperties(this).isMeat();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 0.0));
        //nw
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        //nwends
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(11, new MeleeAttackGoal(this, 1.0D, true));
    }

    public static AttributeSupplier.Builder getExampleAttributes() {
        return Mob.createMobAttributes().add(ForgeMod.ENTITY_GRAVITY.get(), 1.5f).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    public static boolean checkLittleGuySpawnRules(EntityType<LittleGuy> entityType_, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !levelAccessor.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor sLA, DifficultyInstance difInst, MobSpawnType spType, @Nullable SpawnGroupData spgrdata, @Nullable CompoundTag cptag) {
        LittleVariant variant = Util.getRandom(LittleVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(sLA, difInst, spType, spgrdata, cptag);
    }

    //tamable entity code
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        Item tameItem = Items.BLAZE_POWDER;

        //fix breeding
        if(isFood(itemstack)){
            return super.mobInteract(player, hand);
        }

        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(tameItem) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal(10);
                    this.gameEvent(GameEvent.EAT, this);
                    return InteractionResult.SUCCESS;
                }

                if (!(item instanceof DyeItem)) {
                    InteractionResult interactionresult = super.mobInteract(player, hand);
                    if (!interactionresult.consumesAction() && this.isOwnedBy(player)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        return InteractionResult.SUCCESS;
                    }
                    return interactionresult;
                }
            } else if (itemstack.is(tameItem)) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }
                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(player, hand);
        }
    }

    //poisons targeted entities, will not attack tamable entities
    @Override
    public boolean doHurtTarget(Entity player) {
        if (super.doHurtTarget(player)) {
            if ((player instanceof LivingEntity)) {
                ((LivingEntity) player).addEffect(new MobEffectInstance(MobEffects.POISON, 7 * 20, 0), this);
            }
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity thisone){
        if(target instanceof TamableAnimal){ //ADD: || thisone.isBaby() if you do not want babies to attack
            return false;
        }
        else{
            return true;
        }
    }
    //leash fix attempt
    @Override
    public @NotNull Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(0.5F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.4F));
    }

    //variant code
    private static final EntityDataAccessor<Integer> LITTLE_TYPE_VARIANT = SynchedEntityData.defineId(LittleGuy.class, EntityDataSerializers.INT);
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LITTLE_TYPE_VARIANT, 0);
    }
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setTypeVariant(tag.getInt("Variant"));
    }
    private void setTypeVariant(int p_30737_) {
        this.entityData.set(LITTLE_TYPE_VARIANT, p_30737_);
    }

    private int getTypeVariant() {
        return this.entityData.get(LITTLE_TYPE_VARIANT);
    }

    private void setVariant(LittleVariant variant){
        this.setTypeVariant(variant.getId() & 255);
    }

    public LittleVariant getVariant() {
        return LittleVariant.byId(this.getTypeVariant() & 255);
    }

    //shed exoskeleton when age up
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(ItemInit.EXOSKELETON_BIT.get(), 1);
            this.spawnAtLocation(ItemInit.EXOSKELETON_BIT.get(), 1);
            this.spawnAtLocation(ItemInit.EXOSKELETON_BIT.get(), 1);
        }
    }
    //fire res
    public boolean isOnFire(){
        return false;
    }
    public void lavaHurt() {
        this.heal(0.2F);
        this.fireImmune();
    }
    //change attributes when tamed
    @Override
    public void setTame(boolean tame) {
        super.setTame(tame);
        if (tame) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
            this.setHealth(30.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }
    @Override
    //sit up when attacked by other player, mob
    public boolean hurt(DamageSource dSource, float dAmt) {
        if (this.isInvulnerableTo(dSource)) {
            return false;
        } else {
            Entity entity = dSource.getEntity();
            if (!this.level.isClientSide) {
                this.setOrderedToSit(false);
            }
            return super.hurt(dSource, dAmt);
        }
    }

}