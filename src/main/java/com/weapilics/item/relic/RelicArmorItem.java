package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.damage.DamageSource;

public abstract class RelicArmorItem extends ArmorItem {
    public RelicArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
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
