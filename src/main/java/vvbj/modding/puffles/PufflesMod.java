package vvbj.modding.puffles;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vvbj.modding.puffles.block.BlockRegistry;
import vvbj.modding.puffles.entity.EntityRegistry;
import vvbj.modding.puffles.item.ItemRegistry;
import vvbj.modding.puffles.world.PuffleSpawn;
import vvbj.modding.puffles.world.WorldGen;

public class PufflesMod implements ModInitializer {
	public static final String MOD_ID = "puffles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Sounds.register();
		EntityRegistry.register();
		BlockRegistry.register();
		ItemRegistry.register();

		PuffleSpawn.init();
		WorldGen.generateFeatures();
	}
}