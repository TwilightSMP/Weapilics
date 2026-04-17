package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
// ArmorMaterials removed — use RelicArmorItem constructor with EquipmentSlot
import net.minecraft.item.Item.Settings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.weapilics.WeapilicsMod;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;


public class CrownOfRagingInfernoItem extends RelicArmorItem {
	public static final int MAX_RAGE = 100;
	public static final int RAGE_GAIN_ON_FIRE_DAMAGE = 15;
	public static final int RAGE_GAIN_ON_ATTACK = 5;
	public static final int FIRE_DURATION_TICKS = 30; 
	public static final int FIRE_DAMAGE_COOLDOWN = 20; 

	
	private static final Map<UUID, Integer> FIRE_COOLDOWN = new ConcurrentHashMap<>();
	private static final Map<UUID, Integer> FIREBALL_COOLDOWN = new ConcurrentHashMap<>();
	private static final Map<UUID, Integer> RAGE = new ConcurrentHashMap<>();

	public CrownOfRagingInfernoItem(Settings settings) {
		super(EquipmentSlot.HEAD, settings);
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
			addRage(world, uuid, RAGE_GAIN_ON_FIRE_DAMAGE);
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

		addRage(world, uuid, RAGE_GAIN_ON_ATTACK);

		if (getRage(world, uuid) >= MAX_RAGE) {
			fireIceball(world, player, stack);
			setRage(world, uuid, 0);
		}
	}

	private void fireIceball(ServerWorld world, PlayerEntity player, ItemStack stack) {
		UUID uuid = player.getUuid();
		int cd = FIREBALL_COOLDOWN.getOrDefault(uuid, 0);
		if (cd > 0) return;

		Vec3d look = player.getRotationVec(1.0F);
		SmallFireballEntity fireball = new SmallFireballEntity(world, player.getX() + look.x, player.getEyeY() + look.y, player.getZ() + look.z, look);
		world.spawnEntity(fireball);
		FIREBALL_COOLDOWN.put(uuid, 100);
		world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);
		if (WeapilicsMod.DEBUG) WeapilicsMod.LOGGER.info("Crown unleashed rage fireball for {}", player.getName().getString());
	}

	private int getRage(ServerWorld world, UUID uuid) {
		return Math.min(MAX_RAGE, Math.max(0, RAGE.getOrDefault(uuid, 0)));
	}

	private void setRage(ServerWorld world, UUID uuid, int rage) {
		RAGE.put(uuid, Math.max(0, Math.min(rage, MAX_RAGE)));
	}

	private void addRage(ServerWorld world, UUID uuid, int amount) {
		RAGE.merge(uuid, amount, Integer::sum);
		if (WeapilicsMod.DEBUG) WeapilicsMod.LOGGER.debug("Rage for {} now {}", uuid.toString(), RAGE.getOrDefault(uuid, 0));
	}
}
