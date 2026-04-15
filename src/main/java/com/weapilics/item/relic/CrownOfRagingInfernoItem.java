package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;


public class CrownOfRagingInfernoItem extends RelicItem {
	public static final int MAX_RAGE = 100;
	public static final int RAGE_GAIN_ON_FIRE_DAMAGE = 15;
	public static final int RAGE_GAIN_ON_ATTACK = 5;
	public static final int FIRE_DURATION_TICKS = 30; 
	public static final int FIRE_DAMAGE_COOLDOWN = 20; 

	
	private static final Map<UUID, Integer> PLAYER_RAGE = new HashMap<>();
	private static final Map<UUID, Integer> FIRE_COOLDOWN = new HashMap<>();
	private static final Map<UUID, Integer> FIREBALL_COOLDOWN = new HashMap<>();

	public CrownOfRagingInfernoItem(Settings settings) {
		super(settings);
	}
	public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
		
		UUID uuid = player.getUuid();
		
		if (FIRE_COOLDOWN.containsKey(uuid)) {
			int cooldown = FIRE_COOLDOWN.get(uuid);
			if (cooldown > 0) {
				FIRE_COOLDOWN.put(uuid, cooldown - 1);
			} else {
				FIRE_COOLDOWN.remove(uuid);
			}
		}
		
		if (FIREBALL_COOLDOWN.containsKey(uuid)) {
			int cooldown = FIREBALL_COOLDOWN.get(uuid);
			if (cooldown > 0) {
				FIREBALL_COOLDOWN.put(uuid, cooldown - 1);
			} else {
				FIREBALL_COOLDOWN.remove(uuid);
			}
		}
	}
	public boolean onDamageTaken(ServerWorld world, PlayerEntity player, ItemStack stack, DamageSource source, float amount) {
		UUID uuid = player.getUuid();
		
		
		if (source.isOf(DamageTypes.IN_FIRE) || source.isOf(DamageTypes.LAVA) || source.isOf(DamageTypes.HOT_FLOOR)) {
			addRage(uuid, RAGE_GAIN_ON_FIRE_DAMAGE);
		}

		
		int cooldown = FIRE_COOLDOWN.getOrDefault(uuid, 0);
		if (cooldown <= 0) {
			player.setOnFireFor(FIRE_DURATION_TICKS);
			FIRE_COOLDOWN.put(uuid, FIRE_DAMAGE_COOLDOWN);
		}

		return false; 
	}
	public void onAttackDealt(ServerWorld world, PlayerEntity player, ItemStack stack, net.minecraft.entity.LivingEntity target, float damage) {
		UUID uuid = player.getUuid();
		
		
		addRage(uuid, RAGE_GAIN_ON_ATTACK);

		
		if (getRage(uuid) >= MAX_RAGE) {
			fireIceball(world, player, stack);
			setRage(uuid, 0);
		}
	}

	private void fireIceball(ServerWorld world, PlayerEntity player, ItemStack stack) {
		
		
		player.sendMessage(Text.literal("§cRage unleashed!"), false);
	}

	private int getRage(UUID uuid) {
		return PLAYER_RAGE.getOrDefault(uuid, 0);
	}

	private void setRage(UUID uuid, int rage) {
		PLAYER_RAGE.put(uuid, Math.max(0, Math.min(rage, MAX_RAGE)));
	}

	private void addRage(UUID uuid, int amount) {
		int current = getRage(uuid);
		setRage(uuid, current + amount);
	}
}
