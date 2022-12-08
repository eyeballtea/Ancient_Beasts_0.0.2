package net.eyeballtea.miniman;

import com.mojang.logging.LogUtils;
import net.eyeballtea.miniman.init.EntityInit;
import net.eyeballtea.miniman.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(MiniMan.MODID)
public class MiniMan {
    public static final String MODID = "miniman";

    public MiniMan() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //register items and entities w bus
        ItemInit.ITEMS.register(bus);
        EntityInit.ENTITIES.register(bus);
    }
    public static final CreativeModeTab TAB = new CreativeModeTab(12, "Ancient Beasts") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemInit.LITTLE_GUY_SPAWN_EGG.get().getDefaultInstance();
        }
        @Override
        public Component getDisplayName() {
            Component name = Component.translatable("Ancient Beasts");
            return name;
        }
    };
}