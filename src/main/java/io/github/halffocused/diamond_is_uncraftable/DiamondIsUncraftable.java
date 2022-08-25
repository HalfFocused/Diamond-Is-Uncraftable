package io.github.halffocused.diamond_is_uncraftable;

import io.github.halffocused.diamond_is_uncraftable.capability.*;
import io.github.halffocused.diamond_is_uncraftable.command.impl.StandCommand;
import io.github.halffocused.diamond_is_uncraftable.config.DiamondIsUncraftableConfig;
import io.github.halffocused.diamond_is_uncraftable.init.*;
import io.github.halffocused.diamond_is_uncraftable.network.message.PacketHandler;
import io.github.halffocused.diamond_is_uncraftable.particle.ParticleList;
import io.github.halffocused.diamond_is_uncraftable.proxy.ClientProxy;
import io.github.halffocused.diamond_is_uncraftable.proxy.IProxy;
import io.github.halffocused.diamond_is_uncraftable.proxy.ServerProxy;
import io.github.halffocused.diamond_is_uncraftable.world.gen.feature.structure.DesertStructure;
import io.github.halffocused.diamond_is_uncraftable.world.gen.feature.structure.DesertStructurePieces;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import software.bernie.geckolib3.GeckoLib;

/**
 * @author HalfFocused
 * <p>
 * The main {@link Mod} class, used mostly for registering objects.
 * Much respect to Novarch for being the beginning of this project!!
 * After he left, I took over, so you may see conflicting coding styles and old systems lurking around.
 */
@Mod("diamond_is_uncraftable")
public class DiamondIsUncraftable {
    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final String MOD_ID = "diamond_is_uncraftable";
    public static final ResourceLocation STRUCTURE = new ResourceLocation(MOD_ID, "desert_structure");
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    @ObjectHolder(MOD_ID + ":desert_structure")
    public static Structure<NoFeatureConfig> DESERT_STRUCTURE;

    public DiamondIsUncraftable() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modBus.addListener(this::setup);
        forgeBus.addListener(this::onServerStarting); //FMLServerStartingEvent is fired on the Forge bus.

        EventInit.registerForgeBus(MinecraftForge.EVENT_BUS);
        ItemInit.ITEMS.register(modBus);
        EntityInit.ENTITY_TYPES.register(modBus);
        SoundInit.SOUNDS.register(modBus);
        ParticleList.PARTICLES.register(modBus);
        DimensionInit.DIMENSIONS.register(modBus); //Deprecated in preparation for 1.16.
        EffectInit.EFFECTS.register(modBus);
        GeckoLib.initialize();
        DiamondIsUncraftableConfig.register(ModLoadingContext.get());
        modBus.register(this);
    }

    @SubscribeEvent
    public void registerFeatures(RegistryEvent.Register<Feature<?>> args) {
        //DesertStructurePieces.DESERT_STRUCTURE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, STRUCTURE, DesertStructurePieces.Piece::new);
        //args.getRegistry().register(new DesertStructure(NoFeatureConfig::deserialize).setRegistryName(STRUCTURE));
    }

    private void setup(FMLCommonSetupEvent event) {
        Stand.register();
        Timestop.register();
        StandEffects.register();
        StandChunkEffects.register();
        StandPlayerEffects.register();
        StandTileEntityEffects.register();
        PacketHandler.register();
        StandPerWorldCapability.register();
        CombatCapability.register();
        WorldTimestopCapability.register();

        /*
        DeferredWorkQueue.runLater(() -> ForgeRegistries.BIOMES.forEach(biome -> { //This is deprecated for no reason at all.
            if (biome.getCategory() != Biome.Category.DESERT) return;
            biome.addStructure(DESERT_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, DESERT_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }));
         */
    }

    @Deprecated //Replace with RegisterCommandsEvent in 1.16, todo.
    private void onServerStarting(FMLServerStartingEvent event) {
        StandCommand.register(event.getCommandDispatcher());
    }

    @MethodsReturnNonnullByDefault
    public static class JojoItemGroup extends ItemGroup {
        public static final ItemGroup INSTANCE = new JojoItemGroup();

        private JojoItemGroup() {
            super(MOD_ID);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.STAND_ARROW.get());
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }
}
