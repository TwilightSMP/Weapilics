package com.weapilics.client;

import com.weapilics.client.renderer.RelicFeatureRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class WeapilicsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityType == EntityType.PLAYER) {
                @SuppressWarnings("unchecked")
                LivingEntityRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> playerRenderer = (LivingEntityRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>>) entityRenderer;
                playerRenderer.addFeature(new RelicFeatureRenderer<>(playerRenderer));
            }
        });
    }
}
