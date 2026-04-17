package com.weapilics.item.relic;

import com.weapilics.WeapilicsMod;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Settings;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.damage.DamageSource;

import java.util.UUID;

public abstract class RelicArmorItem extends RelicItem {
    private final EquipmentSlot intendedSlot;

    private static final UUID MOD_HEAD = UUID.fromString("a3c1d7b7-3f34-4a6b-a1d6-0e2f8c6e0001");
    private static final UUID MOD_CHEST = UUID.fromString("a3c1d7b7-3f34-4a6b-a1d6-0e2f8c6e0002");
    private static final UUID MOD_LEGS = UUID.fromString("a3c1d7b7-3f34-4a6b-a1d6-0e2f8c6e0003");
    private static final UUID MOD_FEET = UUID.fromString("a3c1d7b7-3f34-4a6b-a1d6-0e2f8c6e0004");
    private static final UUID MOD_CHEST_TOUGH = UUID.fromString("a3c1d7b7-3f34-4a6b-a1d6-0e2f8c6e0005");
    private static final UUID MOD_CHEST_KNOCK = UUID.fromString("a3c1d7b7-3f34-4a6b-a1d6-0e2f8c6e0006");

    public RelicArmorItem(EquipmentSlot slot, Settings settings) {
        super(settings);
        this.intendedSlot = slot;
    }

    public EquipmentSlot getIntendedSlot() {
        return intendedSlot;
    }

    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return intendedSlot;
    }

    public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
    }

    /**
     * Default equip behavior: apply netherite-like attribute modifiers for relic armor.
     * Uses per-slot stable UUIDs so modifiers can be removed on unequip.
     */
    public void onEquip(ServerWorld world, PlayerEntity player, ItemStack stack) {
        try {
            double armorVal = protectionForSlot(intendedSlot);
            if (armorVal > 0) {
                EntityAttributeInstance inst = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
                if (inst != null && inst.getModifier(uuidForSlot(intendedSlot)) == null) {
                    inst.addPersistentModifier(new EntityAttributeModifier(uuidForSlot(intendedSlot), "weapilics:relic_armor_" + intendedSlot.name(), armorVal, EntityAttributeModifier.Operation.ADDITION));
                }
            }

            if (intendedSlot == EquipmentSlot.CHEST) {
                EntityAttributeInstance tough = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
                if (tough != null && tough.getModifier(MOD_CHEST_TOUGH) == null) {
                    tough.addPersistentModifier(new EntityAttributeModifier(MOD_CHEST_TOUGH, "weapilics:relic_armor_toughness", toughnessForChest(), EntityAttributeModifier.Operation.ADDITION));
                }

                EntityAttributeInstance kb = player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
                if (kb != null && kb.getModifier(MOD_CHEST_KNOCK) == null) {
                    kb.addPersistentModifier(new EntityAttributeModifier(MOD_CHEST_KNOCK, "weapilics:relic_knockback_resist", knockbackForChest(), EntityAttributeModifier.Operation.ADDITION));
                }
            }
        } catch (Exception e) {
            WeapilicsMod.LOGGER.error("Error applying relic equip modifiers: {}", e.toString());
        }
    }

    public void onUnequip(ServerWorld world, PlayerEntity player, ItemStack stack) {
        try {
            EntityAttributeInstance inst = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
            if (inst != null) inst.removeModifier(uuidForSlot(intendedSlot));

            if (intendedSlot == EquipmentSlot.CHEST) {
                EntityAttributeInstance tough = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
                if (tough != null) tough.removeModifier(MOD_CHEST_TOUGH);

                EntityAttributeInstance kb = player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
                if (kb != null) kb.removeModifier(MOD_CHEST_KNOCK);
            }
        } catch (Exception e) {
            WeapilicsMod.LOGGER.error("Error removing relic equip modifiers: {}", e.toString());
        }
    }

    public boolean onDamageTaken(ServerWorld world, PlayerEntity player, ItemStack stack, DamageSource source, float amount) {
        return false;
    }

    public void onAttackDealt(ServerWorld world, PlayerEntity player, ItemStack stack, LivingEntity target, float damage) {
    }

    public void onEat(ServerWorld world, PlayerEntity player, ItemStack stack) {
    }

    public void onSneak(ServerWorld world, PlayerEntity player, ItemStack stack, boolean sneaking) {
    }

    public void onMove(ServerWorld world, PlayerEntity player, ItemStack stack) {
    }

    // Ticking is driven centrally by RelicManager; do not rely on a custom inventoryTick signature here.

    private static UUID uuidForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> MOD_HEAD;
            case CHEST -> MOD_CHEST;
            case LEGS -> MOD_LEGS;
            case FEET -> MOD_FEET;
            default -> MOD_HEAD;
        };
    }

    private static double protectionForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> 3.0; // netherite helmet
            case CHEST -> 8.0; // netherite chestplate
            case LEGS -> 6.0; // netherite leggings
            case FEET -> 3.0; // netherite boots
            default -> 0.0;
        };
    }

    private static double toughnessForChest() {
        return 3.0; // chestplate netherite-like toughness
    }

    private static double knockbackForChest() {
        return 0.1; // netherite chest knockback resist
    }
}
