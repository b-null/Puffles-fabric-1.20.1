package vvbj.modding.puffles.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import vvbj.modding.puffles.PufflesMod;
import vvbj.modding.puffles.entity.ModelLayers;
import vvbj.modding.puffles.entity.PuffleEntity;
import vvbj.modding.puffles.entity.PuffleVariant;

import java.util.Map;

public class PuffleRenderer extends MobEntityRenderer<PuffleEntity, PuffleModel<PuffleEntity>> {

    private static final Map<PuffleVariant, Identifier> TEXTURE_MAP =
            Util.make(Maps.newEnumMap(PuffleVariant.class), map -> {
                map.put(PuffleVariant.RED, new Identifier(PufflesMod.MOD_ID, "textures/entity/red_puffle.png"));
                map.put(PuffleVariant.BLUE, new Identifier(PufflesMod.MOD_ID, "textures/entity/blue_puffle.png"));
                map.put(PuffleVariant.GREEN, new Identifier(PufflesMod.MOD_ID, "textures/entity/green_puffle.png"));
                map.put(PuffleVariant.YELLOW, new Identifier(PufflesMod.MOD_ID, "textures/entity/yellow_puffle.png"));
                map.put(PuffleVariant.ORANGE, new Identifier(PufflesMod.MOD_ID, "textures/entity/orange_puffle.png"));
                map.put(PuffleVariant.BROWN, new Identifier(PufflesMod.MOD_ID, "textures/entity/brown_puffle.png"));
                map.put(PuffleVariant.PINK, new Identifier(PufflesMod.MOD_ID, "textures/entity/pink_puffle.png"));
                map.put(PuffleVariant.PURPLE, new Identifier(PufflesMod.MOD_ID, "textures/entity/purple_puffle.png"));
                map.put(PuffleVariant.BLACK, new Identifier(PufflesMod.MOD_ID, "textures/entity/black_puffle.png"));
                map.put(PuffleVariant.WHITE, new Identifier(PufflesMod.MOD_ID, "textures/entity/white_puffle.png"));
            });

    public PuffleRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PuffleModel<>(ctx.getPart(ModelLayers.PUFFLE)), 0.6f);
    }

    @Override
    public Identifier getTexture(PuffleEntity entity) {
        return TEXTURE_MAP.get(entity.getVariant());
    }

    @Override
    public void render(PuffleEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby())
            matrixStack.scale(0.25f, 0.25f, 0.25f);
        else
            matrixStack.scale(0.65f, 0.65f, 0.65f);

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
