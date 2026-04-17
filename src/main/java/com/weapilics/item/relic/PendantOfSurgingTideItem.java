package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterials;


public class PendantOfSurgingTideItem extends RelicArmorItem {
	public static final int WATER_BREATHING_DURATION = 5; 

	public PendantOfSurgingTideItem(Settings settings) {
		super(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, settings);
	}
	public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
		player.addStatusEffect(new StatusEffectInstance(
			StatusEffects.WATER_BREATHING,
			WATER_BREATHING_DURATION,
			0,
			false,
			false
		));

		// Apply a swim-speed effect while in water instead of changing raw movement speed
		if (player.isTouchingWater()) {
			player.addStatusEffect(new StatusEffectInstance(
				StatusEffects.SPEED,
				10,
				0,
				false,
				false
			));
		}
	}
	public void onMove(ServerWorld world, PlayerEntity player, ItemStack stack) {
		// No direct velocity adjustments; effects applied in onTick handle swim behavior
	}

	// swim speed handled with a Speed effect in onTick
}
