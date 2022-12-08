package net.eyeballtea.miniman.init;

import net.eyeballtea.miniman.MiniMan;
import net.eyeballtea.miniman.base.ModArmorMaterials;
import net.eyeballtea.miniman.base.custom.ModArmorItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MiniMan.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(props().stacksTo(64)));
    public static final RegistryObject<ForgeSpawnEggItem> LITTLE_GUY_SPAWN_EGG = ITEMS.register("little_guy_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.LITTLEGUY, 5439542, 13010794, props().stacksTo(64)));
    public static final RegistryObject<Item> EXOSKELETON_BIT = ITEMS.register("exoskeleton_bit", () -> new Item(props().stacksTo(64)));

    //Exoskeleton Armor Creation
    public static final RegistryObject<ArmorItem> EXOSKELETON_HELMET = ITEMS.register("exoskeleton_helmet",
            () -> new ModArmorItem(ModArmorMaterials.EXOSKELETON_BIT, EquipmentSlot.HEAD, props()));
    public static final RegistryObject<ArmorItem> EXOSKELETON_CHESTPLATE = ITEMS.register("exoskeleton_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.EXOSKELETON_BIT, EquipmentSlot.CHEST, props()));
    public static final RegistryObject<ArmorItem> EXOSKELETON_LEGGINGS = ITEMS.register("exoskeleton_leggings",
            () -> new ModArmorItem(ModArmorMaterials.EXOSKELETON_BIT, EquipmentSlot.LEGS, props()));
    public static final RegistryObject<ArmorItem> EXOSKELETON_BOOTS = ITEMS.register("exoskeleton_boots",
            () -> new ModArmorItem(ModArmorMaterials.EXOSKELETON_BIT, EquipmentSlot.FEET, props()));
    private static Item.Properties props() {
        return new Item.Properties().tab(MiniMan.TAB).fireResistant();
    }
    //more armor things
}
