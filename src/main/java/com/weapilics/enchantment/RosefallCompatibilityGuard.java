package com.weapilics.enchantment;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.server.network.ServerPlayerEntity;

public final class RosefallCompatibilityGuard {
	private RosefallCompatibilityGuard() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				enforceForInventory(player);
			}
		});

		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity instanceof ItemEntity itemEntity) {
				RosefallEnchantments.ensureRosefallEnchantments(itemEntity.getStack());
			}
		});
	}

	private static void enforceForInventory(ServerPlayerEntity player) {
		PlayerInventory inventory = player.getInventory();

		for (int slot = 0; slot < inventory.size(); slot++) {
			RosefallEnchantments.ensureRosefallEnchantments(inventory.getStack(slot));
		}

		RosefallEnchantments.ensureRosefallEnchantments(player.currentScreenHandler.getCursorStack());
	}
}
