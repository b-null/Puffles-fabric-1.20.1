package vvbj.modding.puffles.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import vvbj.modding.puffles.PufflesMod;

public class WorldGen {

    private static final RegistryKey<PlacedFeature> O_BERRY_BUSH = registerKey("o_berry_bush_placed");

    public static void generateFeatures(){
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA,
                        BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_BEACH).or(BiomeSelectors.tag(BiomeTags.IS_TAIGA)),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, O_BERRY_BUSH);
    }

    public static RegistryKey<PlacedFeature> registerKey(String name){
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(PufflesMod.MOD_ID, name));
    }
}
