package net.eyeballtea.miniman.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.eyeballtea.miniman.MiniMan;
import net.eyeballtea.miniman.entities.LittleGuy;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Vector;

public class LittleGuyModel extends EntityModel<LittleGuy> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MiniMan.MODID, "little_guy"), "main");
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart frontright;
    private final ModelPart frontleft;
    private final ModelPart backright;
    private final ModelPart backleft;
    private final ModelPart rwing;
    private final ModelPart lwing;
    private final ModelPart mainbody;

    public LittleGuyModel(ModelPart root) {
        this.head = root.getChild("head");
        this.tail = root.getChild("tail");
        this.frontright = root.getChild("frontright");
        this.frontleft = root.getChild("frontleft");
        this.backright = root.getChild("backright");
        this.backleft = root.getChild("backleft");
        this.rwing = root.getChild("rwing");
        this.lwing = root.getChild("lwing");
        this.mainbody = root.getChild("mainbody");
    }

    public static LayerDefinition createBodyLayer() {
        var mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();

        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 12).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 16).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(1.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 12).addBox(-2.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -5.0F));

        PartDefinition tail = parts.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 4.0F));

        PartDefinition tailmain = tail.addOrReplaceChild("tailmain", CubeListBuilder.create().texOffs(19, 31).addBox(-1.0F, -0.7391F, 2.588F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.7391F, -0.588F));

        PartDefinition cube_r1 = tailmain.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(35, 34).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2609F, 6.588F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r2 = tailmain.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(31, 21).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2609F, 2.588F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r3 = tailmain.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -1.5F, -2.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2391F, 1.088F, -0.2182F, 0.0F, 0.0F));

        PartDefinition tailtip = tail.addOrReplaceChild("tailtip", CubeListBuilder.create().texOffs(29, 31).addBox(-1.0F, -2.6667F, -0.1667F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-1.0F, -3.6667F, -1.1667F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 26).addBox(-1.0F, -2.6667F, 0.8333F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.3333F, 4.1667F));

        PartDefinition frontright = parts.addOrReplaceChild("frontright", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -3.0F));

        PartDefinition frontleft = parts.addOrReplaceChild("frontleft", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.5F, -3.0F));

        PartDefinition backright = parts.addOrReplaceChild("backright", CubeListBuilder.create().texOffs(31, 15).addBox(-1.0F, -0.5F, -1.25F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 0).addBox(-1.0F, 1.5F, 0.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 19.5F, 2.25F));

        PartDefinition backleft = parts.addOrReplaceChild("backleft", CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -0.5F, -1.25F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 32).addBox(-1.0F, 1.5F, 0.75F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.5F, 2.25F));

        PartDefinition rwing = parts.addOrReplaceChild("rwing", CubeListBuilder.create(), PartPose.offset(-2.0F, 18.0F, -2.0F));

        PartDefinition base = rwing.addOrReplaceChild("base", CubeListBuilder.create().texOffs(24, 24).addBox(-3.0F, -9.0F, -3.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 6.0F, 2.0F));

        PartDefinition tip = rwing.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(10, 16).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -3.0F, -1.0F));

        PartDefinition lwing = parts.addOrReplaceChild("lwing", CubeListBuilder.create(), PartPose.offset(2.0F, 18.0F, -2.0F));

        PartDefinition base2 = lwing.addOrReplaceChild("base2", CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, -9.0F, -3.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 6.0F, 2.0F));

        PartDefinition tip2 = lwing.addOrReplaceChild("tip2", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -3.0F, -1.0F));

        PartDefinition mainbody = parts.addOrReplaceChild("mainbody", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = mainbody.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -4.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition neck = mainbody.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(10, 28).addBox(-2.0F, -7.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = neck.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(28, 7).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -5.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r5 = neck.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, -4.5F, -0.6545F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void prepareMobModel(LittleGuy entity, float limbSwing, float limbSwingAmount, float p_104135_) {
        if (entity.isInSittingPose()) {
            //sitting body poses
            this.mainbody.setPos(0.0F, 24.0F, -2.0F);this.mainbody.setRotation(-0.6109F, 0.0F, 0.0F);
            this.tail.setPos(0.0F, 22.0F, 5.0F); this.tail.setRotation(0.2182F, 0.0F, 0.0F);
            this.backleft.setPos(2.0F, 21.5F, 4.25F); this.backright.setPos(-2.0F, 21.5F, 4.25F);
            this.frontleft.setPos(2.0F, 19.5F, -2.0F); this.frontright.setPos(-2.0F, 20.0F, -2.0F);
            this.head.setPos(0.0F, 16.0F, -3.0F); this.mainbody.getChild("neck").setPos(0.0F, -1.0F, 2.0F);this.mainbody.getChild("neck").setRotation(0.3491F, 0.0F, 0.0F);
            this.rwing.setPos(-2.0F, 18.0F, -1.0F); this.rwing.setRotation(-0.5236F, 0.0F, 0.0F);
            this.lwing.setPos(2.0F, 18.0F, -1.0F); this.lwing.setRotation( -0.5236F, 0.0F, 0.0F);
            //sitting leg poses, stop swinging
            this.backleft.setRotation(-1.57F, 0.0F, 0.0F);this.backright.setRotation(-1.57F, 0.0F, 0.0F);
            this.frontright.setRotation(0,0,0);this.frontleft.setRotation(0,0,0);
        }else{
            //rot basics to reset rotation from sit pose
            this.tail.xRot = 0;this.backright.xRot = 0;this.backleft.xRot = 0;this.lwing.xRot = 0;this.rwing.xRot = 0;this.mainbody.xRot = 0;
            //set back to og model pos
            this.head.setPos(0.0F, 17.0F, -5.0F); this.mainbody.getChild("neck").setPos(0.0F, 0.0F, 0.0F);this.mainbody.getChild("neck").setRotation(0,0,0);
            this.tail.setPos(0.0F, 19.0F, 4.0F);
            this.mainbody.setPos(0.0F, 24.0F, 0.0F);
            this.lwing.setPos(2.0F, 18.0F, -2.0F); this.rwing.setPos(-2.0F, 18.0F, -2.0F);
            //he does in fact walk
            this.frontleft.setPos(2.0F, 19.5F, -3.0F); this.frontright.setPos(-2.0F, 20.0F, -3.0F);
            this.backleft.setPos(2.0F, 19.5F, 2.25F); this.backright.setPos(-2.0F, 19.5F, 2.25F);
            this.backleft.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
            this.backright.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI + 0.3F) * limbSwingAmount;
            this.frontleft.xRot = Mth.cos(limbSwing * 0.6662F + 0.3F) * limbSwingAmount;
            this.frontright.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
            //wing animation (z rot) flappy!
            this.lwing.zRot = Mth.cos(limbSwing * 0.333F + 0.3F) * (limbSwingAmount*(float).3);
            this.rwing.zRot = (Mth.cos(limbSwing * 0.333F + 0.3F) * (limbSwingAmount*(float).3))*-1;
        }
    }
    @Override
    public void setupAnim(LittleGuy entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //head animation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        //adjust scale to prevent render flicker
        this.frontleft.xScale = .99F;this.frontright.xScale = .99F;this.backleft.xScale = .99F;this.backright.xScale = .99F;this.lwing.xScale = .99F;this.rwing.xScale = .99F;
        (this.mainbody.getChild("neck").getChild("cube_r4")).xScale = .98F; this.tail.getChild("tailtip").xScale = 1.01F;
        (this.tail.getChild("tailmain").getChild("cube_r1")).xScale = .99F;(this.tail.getChild("tailmain").getChild("cube_r2")).xScale = .98F;(this.tail.getChild("tailmain").getChild("cube_r3")).xScale = .97F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        frontright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        frontleft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        backright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        backleft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rwing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        lwing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        mainbody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}