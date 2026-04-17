package com.weapilics.item;

import com.weapilics.enchantment.RosefallEnchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item.Settings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.world.World;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.server.world.ServerWorld;

public class RosefallItem extends MaceItem {
	public static final int REGEN_DURATION_TICKS = 20;
	public static final int REGEN_AMPLIFIER = 0;
	public static final float HIT_HEAL_AMOUNT = 4.0f;

	public RosefallItem(Settings settings) {
		super(settings);
	}
	public ItemStack getDefaultStack() {
		ItemStack stack = super.getDefaultStack();
		RosefallEnchantments.ensureRosefallEnchantments(stack);
		return stack;
	}
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (!(world instanceof ServerWorld serverWorld)) return;
		RosefallEnchantments.ensureRosefallEnchantments(stack, serverWorld.getRegistryManager());
	}
	public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHit(stack, target, attacker);
		attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, REGEN_DURATION_TICKS, REGEN_AMPLIFIER));
		attacker.heal(HIT_HEAL_AMOUNT);
	}
}
