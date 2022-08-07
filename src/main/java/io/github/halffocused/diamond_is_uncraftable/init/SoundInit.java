package io.github.halffocused.diamond_is_uncraftable.init;

import io.github.halffocused.diamond_is_uncraftable.DiamondIsUncraftable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {
    public static DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, DiamondIsUncraftable.MOD_ID);

    public static final RegistryObject<SoundEvent> CANZONI_PREFERITE = SOUNDS.register("canzoni_preferite", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "canzoni_preferite")));
    public static final RegistryObject<SoundEvent> PUNCH_MISS = SOUNDS.register("punch_miss", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch_miss")));
    public static final RegistryObject<SoundEvent> SPAWN_KING_CRIMSON = SOUNDS.register("spawn_king_crimson", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_king_crimson")));
    public static final RegistryObject<SoundEvent> SPAWN_D4C = SOUNDS.register("spawn_d4c", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_d4c")));
    public static final RegistryObject<SoundEvent> SPAWN_GOLD_EXPERIENCE = SOUNDS.register("spawn_gold_experience", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_gold_experience")));
    public static final RegistryObject<SoundEvent> MUDAGIORNO = SOUNDS.register("mudagiorno", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "mudagiorno")));
    public static final RegistryObject<SoundEvent> TRANSMUTE = SOUNDS.register("transmute", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "transmute")));
    public static final RegistryObject<SoundEvent> SPAWN_MADE_IN_HEAVEN = SOUNDS.register("spawn_made_in_heaven", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_made_in_heaven")));
    public static final RegistryObject<SoundEvent> SPAWN_GER = SOUNDS.register("spawn_ger", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_ger")));
    public static final RegistryObject<SoundEvent> SPAWN_AEROSMITH = SOUNDS.register("spawn_aerosmith", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_aerosmith")));
    public static final RegistryObject<SoundEvent> VOLARUSH = SOUNDS.register("volarush", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "volarush")));
    public static final RegistryObject<SoundEvent> SPAWN_WEATHER_REPORT = SOUNDS.register("spawn_weather_report", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_weather_report")));
    public static final RegistryObject<SoundEvent> SPAWN_KILLER_QUEEN = SOUNDS.register("spawn_killer_queen", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_killer_queen")));
    public static final RegistryObject<SoundEvent> LOOK_HERE = SOUNDS.register("look_here", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "look_here")));
    public static final RegistryObject<SoundEvent> SPAWN_CRAZY_DIAMOND = SOUNDS.register("spawn_crazy_diamond", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_crazy_diamond")));
    public static final RegistryObject<SoundEvent> DORARUSH = SOUNDS.register("dorarush", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "dorarush")));
    public static final RegistryObject<SoundEvent> SPAWN_PURPLE_HAZE = SOUNDS.register("spawn_purple_haze", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_purple_haze")));
    public static final RegistryObject<SoundEvent> PURPLE_HAZE_RUSH = SOUNDS.register("purple_haze_rush", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "purple_haze_rush")));
    public static final RegistryObject<SoundEvent> SPAWN_THE_EMPEROR = SOUNDS.register("spawn_the_emperor", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_the_emperor")));
    public static final RegistryObject<SoundEvent> SPAWN_WHITESNAKE = SOUNDS.register("spawn_whitesnake", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_whitesnake")));
    public static final RegistryObject<SoundEvent> SPAWN_CMOON = SOUNDS.register("spawn_cmoon", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_cmoon")));
    public static final RegistryObject<SoundEvent> SPAWN_THE_WORLD = SOUNDS.register("spawn_the_world", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_the_world")));
    public static final RegistryObject<SoundEvent> STOP_TIME = SOUNDS.register("stoptime", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "stoptime")));
    public static final RegistryObject<SoundEvent> RESUME_TIME = SOUNDS.register("resumetime", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "resumetime")));
    public static final RegistryObject<SoundEvent> MUDARUSH = SOUNDS.register("mudarush", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "mudarush")));
    //public static final RegistryObject<SoundEvent> THE_WORLD_TELEPORT = SOUNDS.register("the_world_teleport", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "the_world_teleport")));
    public static final RegistryObject<SoundEvent> SPAWN_STAR_PLATINUM = SOUNDS.register("spawn_star_platinum", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_star_platinum")));
    public static final RegistryObject<SoundEvent> ORARUSH = SOUNDS.register("orarush", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "orarush")));
    public static final RegistryObject<SoundEvent> STAR_PLATINUM_THE_WORLD = SOUNDS.register("star_platinum_the_world", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "star_platinum_the_world")));
    public static final RegistryObject<SoundEvent> RESUME_TIME_STAR_PLATINUM = SOUNDS.register("resume_time_star_platinum", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "resume_time_star_platinum")));
    public static final RegistryObject<SoundEvent> SPAWN_SILVER_CHARIOT = SOUNDS.register("spawn_silver_chariot", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_silver_chariot")));
    public static final RegistryObject<SoundEvent> SILVER_CHARIOT_RUSH = SOUNDS.register("silver_chariot_rush", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "silver_chariot_rush")));
    public static final RegistryObject<SoundEvent> SPAWN_MAGICIANS_RED = SOUNDS.register("spawn_magicians_red", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_magicians_red")));
    public static final RegistryObject<SoundEvent> CROSSFIRE_HURRICANE_SPECIAL = SOUNDS.register("crossfire_hurricane_special", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "crossfire_hurricane_special")));
    public static final RegistryObject<SoundEvent> SPAWN_THE_HAND = SOUNDS.register("spawn_the_hand", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_the_hand")));
    public static final RegistryObject<SoundEvent> THE_HAND_TELEPORT = SOUNDS.register("the_hand_teleport", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "the_hand_teleport")));
    public static final RegistryObject<SoundEvent> THE_HAND_PULL = SOUNDS.register("the_hand_pull", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "the_hand_pull")));
    public static final RegistryObject<SoundEvent> SPAWN_HIEROPHANT_GREEN = SOUNDS.register("spawn_hierophant_green", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_hierophant_green")));
    public static final RegistryObject<SoundEvent> EMERALD_SPLASH = SOUNDS.register("emerald_splash", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "emerald_splash")));
    public static final RegistryObject<SoundEvent> SPAWN_GREEN_DAY = SOUNDS.register("spawn_green_day", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_green_day")));
    public static final RegistryObject<SoundEvent> SPAWN_THE_GRATEFUL_DEAD = SOUNDS.register("spawn_the_grateful_dead", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_the_grateful_dead")));
    public static final RegistryObject<SoundEvent> SPAWN_STICKY_FINGERS = SOUNDS.register("spawn_sticky_fingers", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_sticky_fingers")));
    public static final RegistryObject<SoundEvent> ZIPPER = SOUNDS.register("zipper", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "zipper")));
    public static final RegistryObject<SoundEvent> ARRIVEDERCI = SOUNDS.register("arrivederci", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "arrivederci")));
    public static final RegistryObject<SoundEvent> SPAWN_TUSK_ACT_1 = SOUNDS.register("spawn_tusk_act_1", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_tusk_act_1")));
    public static final RegistryObject<SoundEvent> SPAWN_TUSK_ACT_2 = SOUNDS.register("spawn_tusk_act_2", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_tusk_act_2")));
    public static final RegistryObject<SoundEvent> SPAWN_TUSK_ACT_3 = SOUNDS.register("spawn_tusk_act_3", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_tusk_act_3")));
    public static final RegistryObject<SoundEvent> SPAWN_TUSK_ACT_4 = SOUNDS.register("spawn_tusk_act_4", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_tusk_act_4")));
    public static final RegistryObject<SoundEvent> TUSK_ACT_4_ORA = SOUNDS.register("tusk_act_4_ora", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "tusk_act_4_ora")));
    public static final RegistryObject<SoundEvent> SPAWN_ECHOES_ACT_1 = SOUNDS.register("spawn_echoes_act_1", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_echoes_act_1")));
    public static final RegistryObject<SoundEvent> SPAWN_ECHOES_ACT_2 = SOUNDS.register("spawn_echoes_act_2", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_echoes_act_2")));
    public static final RegistryObject<SoundEvent> SPAWN_ECHOES_ACT_3 = SOUNDS.register("spawn_echoes_act_3", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_echoes_act_3")));
    public static final RegistryObject<SoundEvent> THREE_FREEZE = SOUNDS.register("three_freeze", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "three_freeze")));
    public static final RegistryObject<SoundEvent> SPAWN_BEACH_BOY = SOUNDS.register("spawn_beach_boy", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_beach_boy")));
    public static final RegistryObject<SoundEvent> SPAWN_SOFT_AND_WET = SOUNDS.register("spawn_soft_and_wet", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "spawn_soft_and_wet")));

    /*
    Silver Chariot
     */
    public static final RegistryObject<SoundEvent> ARMOR_OFF_1 = SOUNDS.register("silverchariotarmoroff1", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "silverchariotarmoroff1")));
    public static final RegistryObject<SoundEvent> ARMOR_OFF_2 = SOUNDS.register("silverchariotarmoroff2", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "silverchariotarmoroff2")));
    public static final RegistryObject<SoundEvent> ARMOR_OFF_3 = SOUNDS.register("silverchariotarmoroff3", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "silverchariotarmoroff3")));

    /*
    Killer Queen
    */

    public static final RegistryObject<SoundEvent> DETONATION_CLICK = SOUNDS.register("kq_click", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "kq_click")));

    /*
    The World
     */
    public static final RegistryObject<SoundEvent> THE_WORLD_TIME_STOP = SOUNDS.register("twstop", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "twstop")));
    public static final RegistryObject<SoundEvent> THE_WORLD_TIME_RESUME = SOUNDS.register("twresume", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "twresume")));
    public static final RegistryObject<SoundEvent> THE_WORLD_SUMMON = SOUNDS.register("twsummon", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "twsummon")));
    public static final RegistryObject<SoundEvent> THE_WORLD_TELEPORT = SOUNDS.register("twteleport", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "twteleport")));

    /*
    King Crimson
    */
    public static final RegistryObject<SoundEvent> KING_CRIMSON_COMBO = SOUNDS.register("kccombo", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "kccombo")));
    public static final RegistryObject<SoundEvent> TIME_SKIP_AMBIANCE = SOUNDS.register("kcskipambiance", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "kcskipambiance")));
    public static final RegistryObject<SoundEvent> TIME_SKIP_END = SOUNDS.register("kcskipend", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "kcskipend")));
    public static final RegistryObject<SoundEvent> TIME_SKIP_BEGIN = SOUNDS.register("kcskipstart", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "kcskipstart")));


    /*
    Generic sounds shared by a lot of the stands
     */

    public static final RegistryObject<SoundEvent> PUNCH_1 = SOUNDS.register("punch1", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch1")));
    public static final RegistryObject<SoundEvent> PUNCH_2 = SOUNDS.register("punch2", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch2")));
    public static final RegistryObject<SoundEvent> PUNCH_3 = SOUNDS.register("punch3", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch3")));
    public static final RegistryObject<SoundEvent> PUNCH_4 = SOUNDS.register("punch4", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch4")));
    public static final RegistryObject<SoundEvent> PUNCH_5 = SOUNDS.register("punch5", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch5")));
    public static final RegistryObject<SoundEvent> PUNCH_6 = SOUNDS.register("punch6", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch6")));
    public static final RegistryObject<SoundEvent> PUNCH_7 = SOUNDS.register("punch7", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "punch7")));
    public static final RegistryObject<SoundEvent> SUMMON_STAND = SOUNDS.register("standout", () -> new SoundEvent(new ResourceLocation(DiamondIsUncraftable.MOD_ID, "standout")));



}
