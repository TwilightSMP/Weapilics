package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Settings;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.damage.DamageSource;

public abstract class RelicArmorItem extends RelicItem {
    private final EquipmentSlot intendedSlot;

    public RelicArmorItem(EquipmentSlot slot, Settings settings) {
        super(settings);
        this.intendedSlot = slot;
    }

    public EquipmentSlot getIntendedSlot() {
        return intendedSlot;
    }
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return intendedSlot;
    }

    public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
    }

    public void onEquip(ServerWorld world, PlayerEntity player, ItemStack stack) {
    }

    public void onUnequip(ServerWorld world, PlayerEntity player, ItemStack stack) {
    }

    public boolean onDamageTaken(ServerWorld world, PlayerEntity player, ItemStack stack, DamageSource source, float amount) {
        return false;
    }

    public void onAttackDealt(ServerWorld world, PlayerEntity player, ItemStack stack, LivingEntity target, float damage) {
    }

    public void onEat(ServerWorld world, PlayerEntity player, ItemStack stack) {
    }

    public void onSneak(ServerWorld world, PlayerEntity player, ItemStack stack, boolean sneaking) {
    }

    public void onMove(ServerWorld world, PlayerEntity player, ItemStack stack) {
    }

    // Ticking is driven centrally by RelicManager; do not rely on a custom inventoryTick signature here.
}
