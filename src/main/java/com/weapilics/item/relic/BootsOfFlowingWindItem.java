package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item.Settings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
// ArmorMaterials removed — use RelicArmorItem constructor with EquipmentSlot
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class BootsOfFlowingWindItem extends RelicArmorItem {
	public static final int SLOW_FALLING_DURATION = 5; 

	public BootsOfFlowingWindItem(Settings settings) {
		super(EquipmentSlot.FEET, settings);
	}
	public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
		applySprintSpeedBonus(player);

		handleSlowFallingWhileSneaking(player);
	}
	public void onMove(ServerWorld world, PlayerEntity player, ItemStack stack) {
		if (!player.isOnGround()) {
			applyAirControlBonus(player);
		}
	}

	private void applySprintSpeedBonus(PlayerEntity player) {
		if (player.isSprinting()) {
			player.addStatusEffect(new StatusEffectInstance(
				StatusEffects.SPEED,
				10,
				0,
				false,
				false
			));
		}
	}

	private void applyAirControlBonus(PlayerEntity player) {
		// Give a small speed buff while airborne to improve horizontal control
		player.addStatusEffect(new StatusEffectInstance(
			StatusEffects.SPEED,
			10,
			0,
			false,
			false
		));
	}

	private void handleSlowFallingWhileSneaking(PlayerEntity player) {
		boolean isSneaking = player.isSneaking();
		boolean isInAir = !player.isOnGround();

		if (isSneaking && isInAir) {
			
			player.addStatusEffect(new StatusEffectInstance(
				StatusEffects.SLOW_FALLING,
				SLOW_FALLING_DURATION,
				0,
				false, 
				false  
			));
		} else if (!isSneaking || !isInAir) {
			
			player.removeStatusEffect(StatusEffects.SLOW_FALLING);
		}
	}
}
