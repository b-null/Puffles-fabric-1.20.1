package vvbj.modding.puffles.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import vvbj.modding.puffles.entity.PuffleEntity;

public class PuffleModel<T extends PuffleEntity> extends EntityModel<T> {
    public final ModelPart puffle;
    public PuffleModel(ModelPart root) {
        this.puffle = root.getChild("puffle");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData puffle = modelPartData.addChild("puffle", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = puffle.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -2.0F, -5.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-6.0F, -13.0F, -6.0F, 12.0F, 11.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 23).cuboid(-5.0F, -12.0F, -7.0F, 10.0F, 9.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.0F, -12.0F, 6.0F, 10.0F, 9.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.0F, -14.0F, -5.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-7.0F, -12.0F, -5.0F, 1.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(6.0F, -12.0F, -5.0F, 1.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData hair = body.addChild("hair", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -16.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(1.0F, -16.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(2.0F, -17.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.0F, -18.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData lump = hair.addChild("lump", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.6667F, -15.0F, 3.0F, 0.0F, 0.3491F, 0.0F));

        ModelPartData lump2 = hair.addChild("lump2", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.6667F, -15.0F, 0.0F));

        ModelPartData lump3 = hair.addChild("lump3", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.6667F, -15.0F, -3.0F, 0.0F, -0.3054F, 0.0F));

        ModelPartData lump4 = hair.addChild("lump4", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.6667F, -15.0F, -3.0F, 0.0F, -2.618F, 0.0F));

        ModelPartData lump5 = hair.addChild("lump5", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.6667F, -15.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData lump6 = hair.addChild("lump6", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.6667F, -15.0F, 3.0F, 0.0F, 2.8362F, 0.0F));

        ModelPartData lump7 = hair.addChild("lump7", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.1667F, -15.0F, 5.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData lump8 = hair.addChild("lump8", ModelPartBuilder.create().uv(0, 0).cuboid(0.6667F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.3333F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3333F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.1667F, -15.0F, -5.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 48, 34);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        puffle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
