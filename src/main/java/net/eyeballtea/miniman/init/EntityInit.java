package net.eyeballtea.miniman.init;

import net.eyeballtea.miniman.MiniMan;
import net.eyeballtea.miniman.entities.LittleGuy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MiniMan.MODID);

    public static final RegistryObject<EntityType<LittleGuy>> LITTLEGUY = ENTITIES.register("little_guy",
            () -> EntityType.Builder.of(LittleGuy::new, MobCategory.CREATURE).sized(1.0f, 1.0f).build(MiniMan.MODID + ":little"));
}