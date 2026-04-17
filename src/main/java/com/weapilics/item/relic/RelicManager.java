package com.weapilics.item.relic;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import com.weapilics.WeapilicsMod;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;


public final class RelicManager {
	private RelicManager() {
	}

	private static final Map<UUID, Integer> PLAYER_FOOD_LEVEL = new ConcurrentHashMap<>();

	public static void register() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(RelicManager::onLivingEntityDamage);

		AttackEntityCallback.EVENT.register(RelicManager::onAttackEntity);

		// Server tick handler: call relic tick/move/sneak hooks for equipped relics
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				if (!(player.getEntityWorld() instanceof ServerWorld world)) continue;
					// detect food consumption (simple delta check)
					int currentFood = player.getHungerManager().getFoodLevel();
					int prevFood = PLAYER_FOOD_LEVEL.getOrDefault(player.getUuid(), currentFood);
					if (currentFood > prevFood) {
						for (EquipmentSlot slot : EquipmentSlot.values()) {
							ItemStack stack = player.getEquippedStack(slot);
									if (stack.getItem() instanceof RelicArmorItem relicArmor) {
										if (WeapilicsMod.DEBUG) WeapilicsMod.LOGGER.debug("Player {} ate - triggering onEat for {}", player.getName().getString(), stack.getItem().toString());
										try {
											relicArmor.onEat(world, player, stack);
										} catch (Exception e) {
											WeapilicsMod.LOGGER.error("Error onEat for {}: {}", stack, e.toString());
										}
									} else if (stack.getItem() instanceof RelicItem relic) {
										if (WeapilicsMod.DEBUG) WeapilicsMod.LOGGER.debug("Player {} ate - triggering onEat for {}", player.getName().getString(), stack.getItem().toString());
										try {
											relic.onEat(world, player, stack);
										} catch (Exception e) {
											WeapilicsMod.LOGGER.error("Error onEat for {}: {}", stack, e.toString());
										}
									}
						}
					}
					PLAYER_FOOD_LEVEL.put(player.getUuid(), currentFood);

					for (EquipmentSlot slot : EquipmentSlot.values()) {
						ItemStack stack = player.getEquippedStack(slot);
								if (stack.getItem() instanceof RelicArmorItem relicArmor) {
									if (WeapilicsMod.DEBUG) WeapilicsMod.LOGGER.debug("Relic armor detected on player {}: {}", player.getName().getString(), stack.getItem().toString());
									try {
										relicArmor.onTick(world, player, stack, slot);
										relicArmor.onMove(world, player, stack);
										relicArmor.onSneak(world, player, stack, player.isSneaking());
									} catch (Exception e) {
										WeapilicsMod.LOGGER.error("Error ticking relic armor {} for {}: {}", stack, player.getName().getString(), e.toString());
									}
								} else if (stack.getItem() instanceof RelicItem relic) {
									if (WeapilicsMod.DEBUG) WeapilicsMod.LOGGER.debug("Relic detected on player {}: {}", player.getName().getString(), stack.getItem().toString());
									try {
										relic.onTick(world, player, stack, slot);
										relic.onMove(world, player, stack);
										relic.onSneak(world, player, stack, player.isSneaking());
									} catch (Exception e) {
										WeapilicsMod.LOGGER.error("Error ticking relic {} for {}: {}", stack, player.getName().getString(), e.toString());
									}
								}
					}
            }

			// Cleanup PLAYER_FOOD_LEVEL once per tick: remove entries for players no longer online
			java.util.Set<UUID> online = new java.util.HashSet<>();
			for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
				online.add(p.getUuid());
			}
			PLAYER_FOOD_LEVEL.keySet().removeIf(u -> !online.contains(u));
		});
	}

	private static boolean onLivingEntityDamage(net.minecraft.entity.LivingEntity entity, DamageSource source, float amount) {
		if (!(entity instanceof ServerPlayerEntity player)) {
			return true;
		}

		
		if (!(player.getEntityWorld() instanceof ServerWorld world)) {
			return true;
		}

		
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = player.getEquippedStack(slot);
			if (stack.getItem() instanceof RelicArmorItem relicArmor) {
				if (relicArmor.onDamageTaken(world, player, stack, source, amount)) {
					return false;
				}
			} else if (stack.getItem() instanceof RelicItem relic) {
				if (relic.onDamageTaken(world, player, stack, source, amount)) {
					return false;
				}
			}
		}

		return true; 
	}

	private static ActionResult onAttackEntity(PlayerEntity player, net.minecraft.world.World world, net.minecraft.util.Hand hand, net.minecraft.entity.Entity target, net.minecraft.util.hit.EntityHitResult hitResult) {
		if (world.isClient() || !(target instanceof net.minecraft.entity.LivingEntity livingTarget) || !(player instanceof ServerPlayerEntity serverPlayer)) {
			return ActionResult.PASS;
		}

		ServerWorld serverWorld = (ServerWorld) world;

		
			for (EquipmentSlot slot : EquipmentSlot.values()) {
				ItemStack stack = serverPlayer.getEquippedStack(slot);
				if (stack.getItem() instanceof RelicArmorItem relicArmor) {
					relicArmor.onAttackDealt(serverWorld, serverPlayer, stack, livingTarget, 0);
				} else if (stack.getItem() instanceof RelicItem relic) {
					relic.onAttackDealt(serverWorld, serverPlayer, stack, livingTarget, 0);
				}
			}

		return ActionResult.PASS;
	}
}
