package vvbj.modding.puffles.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vvbj.modding.puffles.PufflesMod;

public class EntityRegistry {
    public static EntityType<PuffleEntity> PUFFLE;

    public static void register(){
        PUFFLE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(PufflesMod.MOD_ID, "puffle"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PuffleEntity::new)
                        .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
        registerAttributes();

    }

    private static void registerAttributes(){
        FabricDefaultAttributeRegistry.register(PUFFLE, PuffleEntity.createAttributes());
    }
}
