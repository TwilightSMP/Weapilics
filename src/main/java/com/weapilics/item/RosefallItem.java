package com.weapilics.item;

import com.weapilics.enchantment.RosefallEnchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.server.world.ServerWorld;

public class RosefallItem extends MaceItem {
	public RosefallItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack getDefaultStack() {
		ItemStack stack = super.getDefaultStack();
		RosefallEnchantments.ensureRosefallEnchantments(stack);
		return stack;
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, EquipmentSlot slot) {
		super.inventoryTick(stack, world, entity, slot);
		RosefallEnchantments.ensureRosefallEnchantments(stack, world.getRegistryManager());
	}
}
