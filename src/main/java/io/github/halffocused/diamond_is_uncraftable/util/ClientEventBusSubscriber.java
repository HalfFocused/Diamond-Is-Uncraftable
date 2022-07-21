package io.github.halffocused.diamond_is_uncraftable.util;

import io.github.halffocused.diamond_is_uncraftable.DiamondIsUncraftable;
import io.github.halffocused.diamond_is_uncraftable.client.entity.model.*;
import io.github.halffocused.diamond_is_uncraftable.client.entity.render.*;
import io.github.halffocused.diamond_is_uncraftable.event.EventClientTick;
import io.github.halffocused.diamond_is_uncraftable.event.EventHandleKeybinds;
import io.github.halffocused.diamond_is_uncraftable.init.EntityInit;
import io.github.halffocused.diamond_is_uncraftable.init.KeyInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DiamondIsUncraftable.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        KeyInit.register();
        MinecraftForge.EVENT_BUS.register(EventHandleKeybinds.class);
        MinecraftForge.EVENT_BUS.register(EventClientTick.class);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.KING_CRIMSON.get(), KingCrimsonRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.D4C.get(), D4CRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.D4C_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOLD_EXPERIENCE.get(), GoldExperienceRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOLD_EXPERIENCE_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new GoldExperiencePunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MADE_IN_HEAVEN.get(), MadeInHeavenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MADE_IN_HEAVEN_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOLD_EXPERIENCE_REQUIEM.get(), GoldExperienceRequiemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GOLD_EXPERIENCE_REQUIEM_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new GoldExperienceRequiemPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.AEROSMITH.get(), AerosmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.AEROSMITH_BULLET.get(), AerosmithBulletRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WEATHER_REPORT.get(), WeatherReportRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WEATHER_REPORT_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.KILLER_QUEEN.get(), KillerQueenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.KILLER_QUEEN_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SHEER_HEART_ATTACK.get(), SheerHeartAttackRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CRAZY_DIAMOND.get(), CrazyDiamondRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CRAZY_DIAMOND_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new CrazyDiamondPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PURPLE_HAZE.get(), PurpleHazeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PURPLE_HAZE_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new PurpleHazePunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WHITESNAKE.get(), WhitesnakeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.WHITESNAKE_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CMOON.get(), CMoonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CMOON_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THE_WORLD.get(), TheWorldRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THE_WORLD_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new TheWorldPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.STAR_PLATINUM.get(), StarPlatinumRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.STAR_PLATINUM_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new StarPlatinumPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SILVER_CHARIOT.get(), SilverChariotRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SILVER_CHARIOT_SWORD.get(), manager -> new StandAttackRenderer<>(manager, new SilverChariotSwordModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MAGICIANS_RED.get(), MagiciansRedRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MAGICIANS_RED_FLAMES.get(), MagiciansRedFlameRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THE_HAND.get(), TheHandRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THE_HAND_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new TheHandPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.STAND_ARROW.get(), StandArrowRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.HIEROPHANT_GREEN.get(), HierophantGreenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.EMERALD_SPLASH.get(), manager -> new EmeraldSplashRenderer(manager, event.getMinecraftSupplier().get().getItemRenderer())); //This renders as an emerald.
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.HIEROPHANT_GREEN_TAIL.get(), manager -> new StandAttackRenderer<>(manager, new HierophantGreenTailModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GREEN_DAY.get(), GreenDayRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GREEN_DAY_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TWENTIETH_CENTURY_BOY.get(), TwentiethCenturyBoyRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THE_GRATEFUL_DEAD.get(), TheGratefulDeadRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.THE_GRATEFUL_DEAD_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new TheGratefulDeadPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.STICKY_FINGERS.get(), StickyFingersRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.STICKY_FINGERS_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new StickyFingersPunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.NAIL_BULLET.get(), manager -> new StandAttackRenderer<>(manager, new NailBulletModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TUSK_ACT_1.get(), TuskAct1Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TUSK_ACT_2.get(), TuskAct2Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TUSK_ACT_3.get(), TuskAct3Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TUSK_ACT_4.get(), TuskAct4Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TUSK_ACT_4_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new DefaultStandAttackModel<>()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ECHOES_ACT_1.get(), EchoesAct1Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ECHOES_ACT_2.get(), EchoesAct2Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ECHOES_ACT_3.get(), EchoesAct3Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ECHOES_ACT_3_PUNCH.get(), manager -> new StandAttackRenderer<>(manager, new EchoesAct3PunchModel()));

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.KING_CRIMSON_AFTERIMAGE.get(), KingCrimsonAfterimageRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CAMERA.get(), CameraRenderer::new);

        EventClientTick.init(event.getMinecraftSupplier().get());
    }
}
