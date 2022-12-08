package net.eyeballtea.miniman.client.renderer;


import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.eyeballtea.miniman.MiniMan;
import net.eyeballtea.miniman.client.models.LittleGuyModel;
import net.eyeballtea.miniman.entities.LittleGuy;
import net.eyeballtea.miniman.entities.variant.LittleVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;

import java.util.Map;

public class LittleGuyRenderer extends MobRenderer<LittleGuy, LittleGuyModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MiniMan.MODID, "textures/entities/miniman_text.png");

    private static final Map<LittleVariant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(LittleVariant.class), (p_114874_) -> {
        p_114874_.put(LittleVariant.DEFAULT, new ResourceLocation(MiniMan.MODID,"textures/entities/miniman_text.png"));
        p_114874_.put(LittleVariant.PURPLE, new ResourceLocation(MiniMan.MODID,"textures/entities/variants/miniman_text_purp.png"));
        p_114874_.put(LittleVariant.RED, new ResourceLocation(MiniMan.MODID, "textures/entities/variants/miniman_text_red.png"));
        p_114874_.put(LittleVariant.YELLOW, new ResourceLocation(MiniMan.MODID, "textures/entities/variants/miniman_text_yel.png"));
    });
    public LittleGuyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new LittleGuyModel(ctx.bakeLayer(LittleGuyModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LittleGuy object) {
        return LOCATION_BY_VARIANT.get(object.getVariant());
    }
    
    //baby methods
    @Override
    public void render(LittleGuy entity, float partialTicks, float unsure, PoseStack stack, MultiBufferSource renderTypeBuffer, int packedLightIn) {
        if(entity.isBaby()){
            stack.scale(0.5F, 0.5F, 0.5F);
        } else{
            stack.scale(1F,1F,1F);
        }

        super.render(entity, partialTicks, unsure, stack, renderTypeBuffer, packedLightIn);
    }
}