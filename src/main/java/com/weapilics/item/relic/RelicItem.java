package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;


public abstract class RelicItem extends Item {
	public RelicItem(Settings settings) {
		super(settings);
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
	public void inventoryTick(ItemStack stack, ServerWorld world, net.minecraft.entity.Entity entity, EquipmentSlot slot) {
		if (!(entity instanceof PlayerEntity player)) {
			return;
		}

		
		onTick(world, player, stack, slot);

		
		onMove(world, player, stack);

		
		onSneak(world, player, stack, player.isSneaking());
	}
}
