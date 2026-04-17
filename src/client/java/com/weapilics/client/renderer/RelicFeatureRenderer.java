package com.weapilics.client.renderer;

import com.weapilics.item.relic.RelicArmorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.client.render.item.ItemRenderer;

public class RelicFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final ItemRenderer itemRenderer;

    public RelicFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
        this.itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!(entity instanceof PlayerEntity player)) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getEquippedStack(slot);
            if (stack.isEmpty()) continue;
            Item item = stack.getItem();
            if (!(item instanceof RelicArmorItem)) continue;

            matrices.push();
            switch (slot) {
                case HEAD -> {
                    matrices.translate(0.0, 0.225, 0.0);
                    matrices.scale(0.9f, 0.9f, 0.9f);
                    this.itemRenderer.renderItem(stack, ModelTransformation.Mode.HEAD, light, 0, matrices, vertexConsumers, player.world);
                }
                case CHEST -> {
                    matrices.translate(0.0, 0.1, 0.0);
                    this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, 0, matrices, vertexConsumers, player.world);
                }
                case LEGS -> {
                    matrices.translate(0.0, -0.1, 0.0);
                    this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, 0, matrices, vertexConsumers, player.world);
                }
                case FEET -> {
                    matrices.translate(0.0, -0.35, 0.0);
                    this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, 0, matrices, vertexConsumers, player.world);
                }
                default -> {}
            }
            matrices.pop();
        }
    }
}
