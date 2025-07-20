package vvbj.modding.puffles.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import vvbj.modding.puffles.entity.EntityRegistry;

public class PuffleSpawn {

    public static void init(){
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA,
                        BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.SNOWY_PLAINS,
                                BiomeKeys.SNOWY_BEACH, BiomeKeys.JAGGED_PEAKS)
                        .or(BiomeSelectors.tag(BiomeTags.IS_TAIGA)).or(biome -> biome.getBiomeRegistryEntry().value().getTemperature() <= 0.35f), SpawnGroup.CREATURE, EntityRegistry.PUFFLE,
                8, 2, 4);

        SpawnRestriction.register(EntityRegistry.PUFFLE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);
    }
}
