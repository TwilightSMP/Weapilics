package com.weapilics.item;

import com.weapilics.WeapilicsMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.MaceItem;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public final class WeapilicsItems {
	public static final Identifier VOID_TRIDENT_ID = Identifier.of(WeapilicsMod.MOD_ID, "void_trident");
	public static final RegistryKey<Item> VOID_TRIDENT_KEY = RegistryKey.of(RegistryKeys.ITEM, VOID_TRIDENT_ID);
	public static final Item VOID_TRIDENT = Registry.register(
		Registries.ITEM,
		VOID_TRIDENT_ID,
		new VoidTridentItem(
			new Item.Settings()
				.registryKey(VOID_TRIDENT_KEY)
				.attributeModifiers(TridentItem.createAttributeModifiers())
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.EPIC)
		)
	);

	public static final Identifier ROSEFALL_ID = Identifier.of(WeapilicsMod.MOD_ID, "rosefall");
	public static final RegistryKey<Item> ROSEFALL_KEY = RegistryKey.of(RegistryKeys.ITEM, ROSEFALL_ID);
	public static final Item ROSEFALL = Registry.register(
		Registries.ITEM,
		ROSEFALL_ID,
		new RosefallItem(
			new Item.Settings()
				.registryKey(ROSEFALL_KEY)
				.attributeModifiers(MaceItem.createAttributeModifiers())
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.EPIC)
		)
	);

	private WeapilicsItems() {
	}

	public static void register() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(VOID_TRIDENT.getDefaultStack());
			entries.add(ROSEFALL.getDefaultStack());
		});
	}
}
