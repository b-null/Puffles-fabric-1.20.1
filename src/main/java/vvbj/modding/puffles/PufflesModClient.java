package vvbj.modding.puffles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import vvbj.modding.puffles.block.BlockRegistry;
import vvbj.modding.puffles.entity.EntityRegistry;
import vvbj.modding.puffles.entity.ModelLayers;
import vvbj.modding.puffles.entity.client.PuffleModel;
import vvbj.modding.puffles.entity.client.PuffleRenderer;
import vvbj.modding.puffles.item.ItemRegistry;
import vvbj.modding.puffles.item.PuffleBoxItem;

public class PufflesModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.O_BERRY_BUSH, RenderLayer.getCutout());

        EntityModelLayerRegistry.registerModelLayer(ModelLayers.PUFFLE, PuffleModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegistry.PUFFLE, PuffleRenderer::new);

        ModelPredicateProviderRegistry.register(
                ItemRegistry.PUFFLE_BOX,
                new Identifier("has_puffles"),
                (stack, world, entity, seed) -> PuffleBoxItem.hasPuffles(stack) ? 1.0f : 0.0f
        );
    }
}
