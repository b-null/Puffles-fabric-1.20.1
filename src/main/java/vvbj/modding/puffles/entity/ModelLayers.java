package vvbj.modding.puffles.entity;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import vvbj.modding.puffles.PufflesMod;

public class ModelLayers {
    public static final EntityModelLayer PUFFLE =
            new EntityModelLayer(new Identifier(PufflesMod.MOD_ID, "puffle"), "main");
}
