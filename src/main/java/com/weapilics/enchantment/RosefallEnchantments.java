package com.weapilics.enchantment;

import com.weapilics.item.WeapilicsItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public final class RosefallEnchantments {
	public static final int DENSITY_LEVEL = 5;
	public static final int BREACH_LEVEL = 4;
	public static final int UNBREAKING_LEVEL = 3;
	public static final int MENDING_LEVEL = 1;
	public static final int FIRE_ASPECT_LEVEL = 2;
	public static final int WIND_BURST_LEVEL = 1;

	private static final Text ROSEFALL_DISPLAY_NAME = Text.literal("Rosefall").setStyle(Style.EMPTY.withItalic(false));

	private RosefallEnchantments() {
	}

	public static boolean isRosefall(ItemStack stack) {
		return !stack.isEmpty() && stack.isOf(WeapilicsItems.ROSEFALL);
	}

	public static void ensureRosefallEnchantments(ItemStack stack) {
		if (!isRosefall(stack)) {
			return;
		}

		setRosefallName(stack);
	}

	public static void ensureRosefallEnchantments(ItemStack stack, DynamicRegistryManager registryManager) {
		if (!isRosefall(stack)) {
			return;
		}

		Registry<Enchantment> enchantmentRegistry = registryManager.getOrThrow(RegistryKeys.ENCHANTMENT);
		RegistryEntry<Enchantment> density = enchantmentRegistry.getOrThrow(Enchantments.DENSITY);
		RegistryEntry<Enchantment> breach = enchantmentRegistry.getOrThrow(Enchantments.BREACH);
		RegistryEntry<Enchantment> unbreaking = enchantmentRegistry.getOrThrow(Enchantments.UNBREAKING);
		RegistryEntry<Enchantment> mending = enchantmentRegistry.getOrThrow(Enchantments.MENDING);
		RegistryEntry<Enchantment> fireAspect = enchantmentRegistry.getOrThrow(Enchantments.FIRE_ASPECT);
		RegistryEntry<Enchantment> windBurst = enchantmentRegistry.getOrThrow(Enchantments.WIND_BURST);

		EnchantmentHelper.apply(stack, builder -> {
			builder.remove(entry -> true);
			builder.set(density, DENSITY_LEVEL);
			builder.set(breach, BREACH_LEVEL);
			builder.set(unbreaking, UNBREAKING_LEVEL);
			builder.set(mending, MENDING_LEVEL);
			builder.set(fireAspect, FIRE_ASPECT_LEVEL);
			builder.set(windBurst, WIND_BURST_LEVEL);
		});

		setRosefallName(stack);
	}

	private static void setRosefallName(ItemStack stack) {
		stack.set(DataComponentTypes.CUSTOM_NAME, ROSEFALL_DISPLAY_NAME);
	}
}
