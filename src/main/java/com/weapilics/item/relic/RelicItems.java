package com.weapilics.item.relic;

import com.weapilics.WeapilicsMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public final class RelicItems {

	
	public static final Identifier BOOTS_OF_FLOWING_WIND_ID = Identifier.of(WeapilicsMod.MOD_ID, "boots_of_flowing_wind");
	public static final RegistryKey<Item> BOOTS_OF_FLOWING_WIND_KEY = RegistryKey.of(RegistryKeys.ITEM, BOOTS_OF_FLOWING_WIND_ID);
	public static final Item BOOTS_OF_FLOWING_WIND = Registry.register(
		Registries.ITEM,
		BOOTS_OF_FLOWING_WIND_ID,
		new BootsOfFlowingWindItem(
			new Item.Settings()
				.registryKey(BOOTS_OF_FLOWING_WIND_KEY)
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.RARE)
		)
	);

	
	public static final Identifier GOWN_OF_BLOOMING_ROSE_ID = Identifier.of(WeapilicsMod.MOD_ID, "gown_of_blooming_rose");
	public static final RegistryKey<Item> GOWN_OF_BLOOMING_ROSE_KEY = RegistryKey.of(RegistryKeys.ITEM, GOWN_OF_BLOOMING_ROSE_ID);
	public static final Item GOWN_OF_BLOOMING_ROSE = Registry.register(
		Registries.ITEM,
		GOWN_OF_BLOOMING_ROSE_ID,
		new GownOfBloomingRoseItem(
			new Item.Settings()
				.registryKey(GOWN_OF_BLOOMING_ROSE_KEY)
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.RARE)
		)
	);

	
	public static final Identifier CROWN_OF_RAGING_INFERNO_ID = Identifier.of(WeapilicsMod.MOD_ID, "crown_of_raging_inferno");
	public static final RegistryKey<Item> CROWN_OF_RAGING_INFERNO_KEY = RegistryKey.of(RegistryKeys.ITEM, CROWN_OF_RAGING_INFERNO_ID);
	public static final Item CROWN_OF_RAGING_INFERNO = Registry.register(
		Registries.ITEM,
		CROWN_OF_RAGING_INFERNO_ID,
		new CrownOfRagingInfernoItem(
			new Item.Settings()
				.registryKey(CROWN_OF_RAGING_INFERNO_KEY)
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.EPIC)
		)
	);

	
	public static final Identifier PENDANT_OF_SURGING_TIDE_ID = Identifier.of(WeapilicsMod.MOD_ID, "pendant_of_surging_tide");
	public static final RegistryKey<Item> PENDANT_OF_SURGING_TIDE_KEY = RegistryKey.of(RegistryKeys.ITEM, PENDANT_OF_SURGING_TIDE_ID);
	public static final Item PENDANT_OF_SURGING_TIDE = Registry.register(
		Registries.ITEM,
		PENDANT_OF_SURGING_TIDE_ID,
		new PendantOfSurgingTideItem(
			new Item.Settings()
				.registryKey(PENDANT_OF_SURGING_TIDE_KEY)
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.RARE)
		)
	);

	
	public static final Identifier CAP_OF_LAUGHING_VOID_ID = Identifier.of(WeapilicsMod.MOD_ID, "cap_of_laughing_void");
	public static final RegistryKey<Item> CAP_OF_LAUGHING_VOID_KEY = RegistryKey.of(RegistryKeys.ITEM, CAP_OF_LAUGHING_VOID_ID);
	public static final Item CAP_OF_LAUGHING_VOID = Registry.register(
		Registries.ITEM,
		CAP_OF_LAUGHING_VOID_ID,
		new CapOfLaughingVoidItem(
			new Item.Settings()
				.registryKey(CAP_OF_LAUGHING_VOID_KEY)
				.maxCount(1)
				.maxDamage(500)
				.rarity(Rarity.RARE)
		)
	);

	private RelicItems() {
	}

	public static void register() {
		
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(BOOTS_OF_FLOWING_WIND.getDefaultStack());
			entries.add(GOWN_OF_BLOOMING_ROSE.getDefaultStack());
			entries.add(CROWN_OF_RAGING_INFERNO.getDefaultStack());
			entries.add(PENDANT_OF_SURGING_TIDE.getDefaultStack());
			entries.add(CAP_OF_LAUGHING_VOID.getDefaultStack());
		});
	}
}
