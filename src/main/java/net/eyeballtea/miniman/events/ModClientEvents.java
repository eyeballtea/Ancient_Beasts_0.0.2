package net.eyeballtea.miniman.events;

import net.eyeballtea.miniman.MiniMan;
import net.eyeballtea.miniman.client.models.LittleGuyModel;
import net.eyeballtea.miniman.client.renderer.LittleGuyRenderer;
import net.eyeballtea.miniman.init.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MiniMan.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.LITTLEGUY.get(), LittleGuyRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LittleGuyModel.LAYER_LOCATION, LittleGuyModel::createBodyLayer);
    }
}