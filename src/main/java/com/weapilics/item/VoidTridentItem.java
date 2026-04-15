package com.weapilics.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VoidTridentItem extends TridentItem {
	public static final int VOID_RIPTIDE_LEVEL = 4;
	public static final int VOID_RIPTIDE_DURATION_TICKS = 20;
	public static final float VOID_RIPTIDE_ATTACK_DAMAGE = 8.0f;
	public static final float BASE_RIPTIDE_SPEED = 3.0f;
	public static final double GROUND_LIFT = 1.1999999f;

	private static final Text VOID_TRIDENT_DISPLAY_NAME = Text.literal("Void Trident")
		.setStyle(Style.EMPTY.withItalic(false).withFormatting(Formatting.BLACK));

	public VoidTridentItem(Item.Settings settings) {
		super(settings);
	}
	public ItemStack getDefaultStack() {
		ItemStack stack = super.getDefaultStack();
		applyVoidName(stack);
		return stack;
	}
	public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, EquipmentSlot slot) {
		super.inventoryTick(stack, world, entity, slot);
		applyVoidName(stack);
	}
	public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (!(user instanceof PlayerEntity player)) {
			return false;
		}

		int useTicks = this.getMaxUseTime(stack, user) - remainingUseTicks;
		if (useTicks < MIN_DRAW_DURATION) {
			return false;
		}

		if (!world.isClient()) {
			EquipmentSlot activeSlot = player.getActiveHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
			stack.damage(1, player, activeSlot);
		}

		Vec3d launchVelocity = player.getRotationVector()
			.normalize()
			.multiply(calculateVoidRiptideSpeed(VOID_RIPTIDE_LEVEL));

		player.addVelocity(launchVelocity.x, launchVelocity.y, launchVelocity.z);
		player.useRiptide(VOID_RIPTIDE_DURATION_TICKS, VOID_RIPTIDE_ATTACK_DAMAGE, stack);

		if (player.isOnGround()) {
			player.move(MovementType.SELF, new Vec3d(0.0, GROUND_LIFT, 0.0));
		}

		world.playSoundFromEntity(null, player, SoundEvents.ITEM_TRIDENT_RIPTIDE_3.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
		return true;
	}

	private static float calculateVoidRiptideSpeed(int riptideLevel) {
		return BASE_RIPTIDE_SPEED * ((1.0f + riptideLevel) / 4.0f);
	}

	private static void applyVoidName(ItemStack stack) {
		if (stack.isOf(WeapilicsItems.VOID_TRIDENT)) {
			stack.set(DataComponentTypes.CUSTOM_NAME, VOID_TRIDENT_DISPLAY_NAME);
		}
	}
}