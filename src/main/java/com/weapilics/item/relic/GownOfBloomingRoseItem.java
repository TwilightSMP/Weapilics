package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item.Settings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class GownOfBloomingRoseItem extends RelicArmorItem {
	public static final int REGEN_DURATION_AFTER_EAT = 40; 
	public static final int REGEN_AMPLIFIER = 0; 
	public static final int SNEAK_REGEN_DURATION = 5; 

	public GownOfBloomingRoseItem(Settings settings) {
		super(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, settings);
	}
	public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
		
		if (player.isSneaking()) {
			player.addStatusEffect(new StatusEffectInstance(
				StatusEffects.REGENERATION,
				SNEAK_REGEN_DURATION,
				REGEN_AMPLIFIER,
				false, 
				false  
			));
		}
	}
	public void onEat(ServerWorld world, PlayerEntity player, ItemStack stack) {
		
		player.addStatusEffect(new StatusEffectInstance(
			StatusEffects.REGENERATION,
			REGEN_DURATION_AFTER_EAT,
			REGEN_AMPLIFIER,
			false, 
			false  
		));
	}
}
