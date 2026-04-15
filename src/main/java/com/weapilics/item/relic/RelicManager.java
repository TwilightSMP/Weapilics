package com.weapilics.item.relic;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;


public final class RelicManager {
	private RelicManager() {
	}

	public static void register() {
		
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(RelicManager::onLivingEntityDamage);

		
		AttackEntityCallback.EVENT.register(RelicManager::onAttackEntity);

		
		
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
			if (stack.getItem() instanceof RelicItem relic) {
				
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
			if (stack.getItem() instanceof RelicItem relic) {
				relic.onAttackDealt(serverWorld, serverPlayer, stack, livingTarget, 0);
			}
		}

		return ActionResult.PASS;
	}
}
