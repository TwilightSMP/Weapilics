package com.weapilics;

import com.weapilics.effect.RosefallEffects;
import com.weapilics.enchantment.RosefallCompatibilityGuard;
import com.weapilics.item.WeapilicsItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WeapilicsMod implements ModInitializer {
	public static final String MOD_ID = "weapilics";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		WeapilicsItems.register();
		RosefallCompatibilityGuard.register();
		RosefallEffects.register();

		LOGGER.info("Weapilics initialized");
	}
}
