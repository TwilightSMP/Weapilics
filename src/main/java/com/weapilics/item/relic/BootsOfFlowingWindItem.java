package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class BootsOfFlowingWindItem extends RelicItem {
	public static final float SPRINT_SPEED_MULTIPLIER = 1.3f; 
	public static final float AIR_CONTROL_MULTIPLIER = 1.4f; 
	public static final int SLOW_FALLING_DURATION = 5; 

	public BootsOfFlowingWindItem(Settings settings) {
		super(settings);
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
			
			double currentSpeed = player.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
			if (currentSpeed > 0) {
				player.setMovementSpeed((float) (currentSpeed * SPRINT_SPEED_MULTIPLIER));
			}
		}
	}

	private void applyAirControlBonus(PlayerEntity player) {
		
		
		if (player.getVelocity().length() > 0) {
			var velocity = player.getVelocity();
			
			player.setVelocity(
				velocity.x * AIR_CONTROL_MULTIPLIER,
				velocity.y, 
				velocity.z * AIR_CONTROL_MULTIPLIER
			);
		}
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
