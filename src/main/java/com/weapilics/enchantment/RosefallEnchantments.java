package com.weapilics.enchantment;

import com.weapilics.item.WeapilicsItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public final class RosefallEnchantments {
	public static final int DENSITY_LEVEL = 5;
	public static final int BREACH_LEVEL = 4;
	public static final int UNBREAKING_LEVEL = 3;
	public static final int MENDING_LEVEL = 1;
	public static final int FIRE_ASPECT_LEVEL = 2;

	private static final Text ROSEFALL_DISPLAY_NAME = Text.literal("Rosefall").setStyle(Style.EMPTY.withItalic(false));
	private static final RegistryWrapper.Impl<Enchantment> ENCHANTMENT_LOOKUP = BuiltinRegistries.createWrapperLookup().getOrThrow(RegistryKeys.ENCHANTMENT);
	private static final RegistryEntry<Enchantment> DENSITY = ENCHANTMENT_LOOKUP.getOrThrow(Enchantments.DENSITY);
	private static final RegistryEntry<Enchantment> BREACH = ENCHANTMENT_LOOKUP.getOrThrow(Enchantments.BREACH);
	private static final RegistryEntry<Enchantment> UNBREAKING = ENCHANTMENT_LOOKUP.getOrThrow(Enchantments.UNBREAKING);
	private static final RegistryEntry<Enchantment> MENDING = ENCHANTMENT_LOOKUP.getOrThrow(Enchantments.MENDING);
	private static final RegistryEntry<Enchantment> FIRE_ASPECT = ENCHANTMENT_LOOKUP.getOrThrow(Enchantments.FIRE_ASPECT);

	private RosefallEnchantments() {
	}

	public static boolean isRosefall(ItemStack stack) {
		return !stack.isEmpty() && stack.isOf(WeapilicsItems.ROSEFALL);
	}

	public static void ensureRosefallEnchantments(ItemStack stack) {
		if (!isRosefall(stack)) {
			return;
		}

		EnchantmentHelper.apply(stack, builder -> {
			builder.remove(entry -> true);
			builder.set(DENSITY, DENSITY_LEVEL);
			builder.set(BREACH, BREACH_LEVEL);
			builder.set(UNBREAKING, UNBREAKING_LEVEL);
			builder.set(MENDING, MENDING_LEVEL);
			builder.set(FIRE_ASPECT, FIRE_ASPECT_LEVEL);
		});

		stack.set(DataComponentTypes.CUSTOM_NAME, ROSEFALL_DISPLAY_NAME);
	}
}
