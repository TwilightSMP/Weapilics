package com.weapilics.effect;

import com.weapilics.enchantment.RosefallEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public final class RosefallEffects {
	public static final int BLOOM_PARTICLE_COLOR = 0xF475D1;
	public static final int CORE_PARTICLE_COLOR = 0xFFC3E9;
	public static final float BLOOM_PARTICLE_SCALE = 1.4f;
	public static final float CORE_PARTICLE_SCALE = 1.0f;
	public static final int BLOOM_PARTICLE_COUNT = 26;
	public static final int CORE_PARTICLE_COUNT = 18;
	public static final int PETAL_PARTICLE_COUNT = 20;
	public static final double PARTICLE_SPREAD_X = 0.45;
	public static final double PARTICLE_SPREAD_Y = 0.65;
	public static final double PARTICLE_SPREAD_Z = 0.45;
	public static final double PARTICLE_SPEED = 0.025;
	public static final double PETAL_SPEED = 0.012;

	private static final DustParticleEffect BLOOM_DUST = new DustParticleEffect(BLOOM_PARTICLE_COLOR, BLOOM_PARTICLE_SCALE);
	private static final DustParticleEffect CORE_DUST = new DustParticleEffect(CORE_PARTICLE_COLOR, CORE_PARTICLE_SCALE);

	private RosefallEffects() {
	}

	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register(RosefallEffects::onAfterDeath);
	}

	private static void onAfterDeath(LivingEntity victim, DamageSource damageSource) {
		if (!(victim.getEntityWorld() instanceof ServerWorld serverWorld)) {
			return;
		}

		ServerPlayerEntity player = findRosefallKiller(victim, damageSource);
		if (player == null) {
			return;
		}

		spawnBloomParticles(serverWorld, victim);
	}

	private static ServerPlayerEntity findRosefallKiller(LivingEntity victim, DamageSource damageSource) {
		Entity attacker = damageSource.getAttacker();
		if (attacker instanceof ServerPlayerEntity player && isHoldingRosefall(player)) {
			return player;
		}

		LivingEntity recentAttacker = victim.getAttacker();
		if (recentAttacker instanceof ServerPlayerEntity player && isHoldingRosefall(player)) {
			return player;
		}

		return null;
	}

	private static boolean isHoldingRosefall(ServerPlayerEntity player) {
		return RosefallEnchantments.isRosefall(player.getMainHandStack())
			|| RosefallEnchantments.isRosefall(player.getOffHandStack());
	}

	private static void spawnBloomParticles(ServerWorld serverWorld, LivingEntity victim) {
		serverWorld.spawnParticles(
			BLOOM_DUST,
			victim.getX(),
			victim.getBodyY(0.5),
			victim.getZ(),
			BLOOM_PARTICLE_COUNT,
			PARTICLE_SPREAD_X,
			PARTICLE_SPREAD_Y,
			PARTICLE_SPREAD_Z,
			PARTICLE_SPEED
		);

		serverWorld.spawnParticles(
			CORE_DUST,
			victim.getX(),
			victim.getBodyY(0.55),
			victim.getZ(),
			CORE_PARTICLE_COUNT,
			PARTICLE_SPREAD_X * 0.6,
			PARTICLE_SPREAD_Y * 0.6,
			PARTICLE_SPREAD_Z * 0.6,
			PARTICLE_SPEED * 0.8
		);

		serverWorld.spawnParticles(
			ParticleTypes.CHERRY_LEAVES,
			victim.getX(),
			victim.getBodyY(0.7),
			victim.getZ(),
			PETAL_PARTICLE_COUNT,
			PARTICLE_SPREAD_X,
			PARTICLE_SPREAD_Y,
			PARTICLE_SPREAD_Z,
			PETAL_SPEED
		);
	}
}
