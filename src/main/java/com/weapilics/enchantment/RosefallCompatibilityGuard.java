package com.weapilics.enchantment;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.network.ServerPlayerEntity;

public final class RosefallCompatibilityGuard {
	private RosefallCompatibilityGuard() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			var registryManager = server.getRegistryManager();
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				enforceForInventory(player, registryManager);
			}
		});

		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity instanceof ItemEntity itemEntity) {
				RosefallEnchantments.ensureRosefallEnchantments(itemEntity.getStack(), world.getRegistryManager());
			}
		});
	}

	private static void enforceForInventory(ServerPlayerEntity player, DynamicRegistryManager registryManager) {
		PlayerInventory inventory = player.getInventory();

		for (int slot = 0; slot < inventory.size(); slot++) {
			RosefallEnchantments.ensureRosefallEnchantments(inventory.getStack(slot), registryManager);
		}

		RosefallEnchantments.ensureRosefallEnchantments(player.currentScreenHandler.getCursorStack(), registryManager);
	}
}
