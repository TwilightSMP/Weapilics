package com.weapilics.effect;

import com.weapilics.enchantment.RosefallEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public final class RosefallEffects {
	public static final int PARTICLE_COLOR = 0xF475D1;
	public static final float PARTICLE_SCALE = 1.35f;
	public static final int PARTICLE_COUNT = 26;
	public static final double PARTICLE_SPREAD_X = 0.45;
	public static final double PARTICLE_SPREAD_Y = 0.65;
	public static final double PARTICLE_SPREAD_Z = 0.45;
	public static final double PARTICLE_SPEED = 0.025;

	private static final DustParticleEffect ROSEFALL_DUST = new DustParticleEffect(PARTICLE_COLOR, PARTICLE_SCALE);

	private RosefallEffects() {
	}

	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register(RosefallEffects::onAfterDeath);
	}

	private static void onAfterDeath(LivingEntity victim, DamageSource damageSource) {
		if (!(victim.getEntityWorld() instanceof ServerWorld serverWorld)) {
			return;
		}

		Entity attacker = damageSource.getAttacker();
		if (!(attacker instanceof ServerPlayerEntity player)) {
			return;
		}

		if (!RosefallEnchantments.isRosefall(player.getMainHandStack())) {
			return;
		}

		serverWorld.spawnParticles(
			ROSEFALL_DUST,
			victim.getX(),
			victim.getBodyY(0.5),
			victim.getZ(),
			PARTICLE_COUNT,
			PARTICLE_SPREAD_X,
			PARTICLE_SPREAD_Y,
			PARTICLE_SPREAD_Z,
			PARTICLE_SPEED
		);
	}
}
