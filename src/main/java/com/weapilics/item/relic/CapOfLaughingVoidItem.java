package com.weapilics.item.relic;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.server.world.ServerWorld;

public class CapOfLaughingVoidItem extends RelicArmorItem {
    public static final int INVISIBILITY_DURATION = 5; // Duration in ticks

    public CapOfLaughingVoidItem(Settings settings) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, settings);
    }

    @Override
    public void onTick(ServerWorld world, PlayerEntity player, ItemStack stack, EquipmentSlot slot) {
        // Apply invisibility effect when the player is sneaking
        if (player.isSneaking()) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.INVISIBILITY,
                INVISIBILITY_DURATION,
                0,
                false, // No particles
                false  // No icon
            ));
        }
    }
}
