package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class PendantOfSurgingTideItem extends RelicItem {
	public static final float SWIM_SPEED_MULTIPLIER = 2.0f;
	public static final int WATER_BREATHING_DURATION = 5; 

	public PendantOfSurgingTideItem(Settings settings) {
		super(settings);
	}
	public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
		
		player.addStatusEffect(new StatusEffectInstance(
			StatusEffects.WATER_BREATHING,
			WATER_BREATHING_DURATION,
			0,
			false, 
			false  
		));

		
		if (player.isTouchingWater()) {
			applySwimSpeedBonus(player);
		}
	}
	public void onMove(ServerWorld world, PlayerEntity player, ItemStack stack) {
		
		if (player.isSprinting() && player.isTouchingWater()) {
			var velocity = player.getVelocity();
			
			if (velocity.y < 0) {
				player.setVelocity(velocity.x, 0.05, velocity.z); 
			}
		}
	}

	private void applySwimSpeedBonus(PlayerEntity player) {
		
		double currentSpeed = player.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
		if (currentSpeed > 0) {
			player.setMovementSpeed((float) (currentSpeed * SWIM_SPEED_MULTIPLIER));
		}
	}
}
