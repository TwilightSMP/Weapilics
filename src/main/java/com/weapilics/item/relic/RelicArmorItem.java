package com.weapilics.item.relic;

import com.weapilics.WeapilicsMod;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Settings;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.entry.RegistryEntry;

public abstract class RelicArmorItem extends RelicItem {
    private final EquipmentSlot intendedSlot;

    private static final Identifier MOD_HEAD = Identifier.of(WeapilicsMod.MOD_ID, "relic_head");
    private static final Identifier MOD_CHEST = Identifier.of(WeapilicsMod.MOD_ID, "relic_chest");
    private static final Identifier MOD_LEGS = Identifier.of(WeapilicsMod.MOD_ID, "relic_legs");
    private static final Identifier MOD_FEET = Identifier.of(WeapilicsMod.MOD_ID, "relic_feet");
    private static final Identifier MOD_CHEST_TOUGH = Identifier.of(WeapilicsMod.MOD_ID, "relic_chest_toughness");
    private static final Identifier MOD_CHEST_KNOCK = Identifier.of(WeapilicsMod.MOD_ID, "relic_chest_knockback");

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
                RegistryEntry<EntityAttribute> attrEntry = Registries.ATTRIBUTE.get(Identifier.of("minecraft", "generic.armor"));
                EntityAttributeInstance inst = player.getAttributeInstance(attrEntry);
                Identifier modId = idForSlot(intendedSlot);
                EntityAttributeModifier.Operation op;
                try {
                    op = EntityAttributeModifier.Operation.valueOf("ADDITION");
                } catch (Exception ex) {
                    try {
                        op = EntityAttributeModifier.Operation.valueOf("ADD");
                    } catch (Exception ex2) {
                        op = null;
                    }
                }
                if (inst != null && inst.getModifier(modId) == null) {
                    if (op != null) inst.addPersistentModifier(new EntityAttributeModifier(modId, armorVal, op));
                    else inst.addPersistentModifier(new EntityAttributeModifier(modId, armorVal, EntityAttributeModifier.Operation.valueOf("ADDITION")));
                }
            }

            if (intendedSlot == EquipmentSlot.CHEST) {
                RegistryEntry<EntityAttribute> toughAttr = Registries.ATTRIBUTE.get(Identifier.of("minecraft", "generic.armor_toughness"));
                EntityAttributeInstance tough = player.getAttributeInstance(toughAttr);
                if (tough != null && tough.getModifier(MOD_CHEST_TOUGH) == null) {
                    if (op != null) tough.addPersistentModifier(new EntityAttributeModifier(MOD_CHEST_TOUGH, toughnessForChest(), op));
                    else tough.addPersistentModifier(new EntityAttributeModifier(MOD_CHEST_TOUGH, toughnessForChest(), EntityAttributeModifier.Operation.valueOf("ADDITION")));
                }

                RegistryEntry<EntityAttribute> kbAttr = Registries.ATTRIBUTE.get(Identifier.of("minecraft", "generic.knockback_resistance"));
                EntityAttributeInstance kb = player.getAttributeInstance(kbAttr);
                if (kb != null && kb.getModifier(MOD_CHEST_KNOCK) == null) {
                    if (op != null) kb.addPersistentModifier(new EntityAttributeModifier(MOD_CHEST_KNOCK, knockbackForChest(), op));
                    else kb.addPersistentModifier(new EntityAttributeModifier(MOD_CHEST_KNOCK, knockbackForChest(), EntityAttributeModifier.Operation.valueOf("ADDITION")));
                }
            }
        } catch (Exception e) {
            WeapilicsMod.LOGGER.error("Error applying relic equip modifiers: {}", e.toString());
        }
    }

    public void onUnequip(ServerWorld world, PlayerEntity player, ItemStack stack) {
        try {
            RegistryEntry<EntityAttribute> attrEntry = Registries.ATTRIBUTE.get(Identifier.of("minecraft", "generic.armor"));
            EntityAttributeInstance inst = player.getAttributeInstance(attrEntry);
            if (inst != null) inst.removeModifier(idForSlot(intendedSlot));

            if (intendedSlot == EquipmentSlot.CHEST) {
                RegistryEntry<EntityAttribute> toughAttr = Registries.ATTRIBUTE.get(Identifier.of("minecraft", "generic.armor_toughness"));
                EntityAttributeInstance tough = player.getAttributeInstance(toughAttr);
                if (tough != null) tough.removeModifier(MOD_CHEST_TOUGH);

                RegistryEntry<EntityAttribute> kbAttr = Registries.ATTRIBUTE.get(Identifier.of("minecraft", "generic.knockback_resistance"));
                EntityAttributeInstance kb = player.getAttributeInstance(kbAttr);
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

    private static Identifier idForSlot(EquipmentSlot slot) {
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
