package io.github.halffocused.diamond_is_uncraftable.init;

import io.github.halffocused.diamond_is_uncraftable.DiamondIsUncraftable;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyInit {
    private static final String KEY_CATEGORY = "key.categories." + DiamondIsUncraftable.MOD_ID;
    public static final KeyBinding SPAWN_STAND = new KeyBinding("key.spawnstand.desc", GLFW.GLFW_KEY_V, KEY_CATEGORY);
    public static final KeyBinding TOGGLE_ABILITY = new KeyBinding("key.ability.desc", GLFW.GLFW_KEY_Y, KEY_CATEGORY);
    public static final KeyBinding ABILITY1 = new KeyBinding("key.ability2.desc", GLFW.GLFW_KEY_X, KEY_CATEGORY);
    public static final KeyBinding ABILITY2 = new KeyBinding("key.ability3.desc", GLFW.GLFW_KEY_Z, KEY_CATEGORY);
    public static final KeyBinding ABILITY3 = new KeyBinding("key.ability4.desc", GLFW.GLFW_KEY_R, KEY_CATEGORY);
    public static final KeyBinding SWITCH_ACT = new KeyBinding("key.switchact.desc", GLFW.GLFW_KEY_B, KEY_CATEGORY);
    public static final KeyBinding SWITCH_TARGETING = new KeyBinding("key.switchtarget.desc", GLFW.GLFW_KEY_C, KEY_CATEGORY);

    public static void register() {
        ClientRegistry.registerKeyBinding(SPAWN_STAND);
        ClientRegistry.registerKeyBinding(TOGGLE_ABILITY);
        ClientRegistry.registerKeyBinding(ABILITY1);
        ClientRegistry.registerKeyBinding(ABILITY2);
        ClientRegistry.registerKeyBinding(ABILITY3);
        ClientRegistry.registerKeyBinding(SWITCH_ACT);
        ClientRegistry.registerKeyBinding(SWITCH_TARGETING);
    }
}
