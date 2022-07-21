package io.github.halffocused.diamond_is_uncraftable.client.gui;

import io.github.halffocused.diamond_is_uncraftable.capability.Stand;
import io.github.halffocused.diamond_is_uncraftable.capability.StandEffects;
import io.github.halffocused.diamond_is_uncraftable.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

@SuppressWarnings("unused")
public class StandGUI extends AbstractGui {
    public static final Minecraft mc = Minecraft.getInstance();

    public void renderTimeValue(int ticks) {
        int seconds = (ticks / 20) - (ticks / 1200 * 60);
        renderString((ticks / 1200) + (seconds < 10 ? ":0" : ":") + seconds);
    }

    public void renderTimeLeft(int ticks) {
        renderString(ticks / 20 + " seconds left.");
    }

    public void renderCooldown(int ticks) {
        renderString("Cooldown: " + ticks / 20);
    }

    protected void renderString(String text, int... position) {
        if (position.length < 2)
            drawString(mc.fontRenderer, text, 4, 4, 0xFFFFFF);
        else if (position.length == 2)
            drawString(mc.fontRenderer, text, position[0], position[1], 0xFFFFFF);
    }

    public void render() {
        assert mc.player != null;
        Stand.getLazyOptional(mc.player).ifPresent(stand -> {
            int standID = stand.getStandID();
            int act = stand.getAct();
            int timeLeft = (int) stand.getTimeLeft();
            int cooldown = (int) stand.getCooldown();
            int transformed = stand.getTransformed();
            double invulnerableTicks = stand.getInvulnerableTicks();
            boolean charging = stand.isCharging();
            float damage = stand.getStandDamage();
            int abilityUseCount = stand.getAbilityUseCount();
            if (stand.getStandOn()) {
                switch (standID) {
                    case Util.StandID.MADE_IN_HEAVEN: {
                        if (stand.getAct() == 0) {
                            if (timeLeft > -1400)
                                renderTimeValue(timeLeft + 1400);
                            else
                                renderString("\"Heaven\" has begun!");
                        }
                        break;
                    }
                    case Util.StandID.TWENTIETH_CENTURY_BOY: {
                        if (timeLeft > 801 && cooldown == 0)
                            renderTimeLeft(timeLeft - 800);
                        break;
                    }
                    case Util.StandID.SILVER_CHARIOT: {
                        if (timeLeft > 801 && cooldown == 0)
                            renderTimeLeft(timeLeft - 800);

                        renderString("Momentum: " + stand.getMomentum(), 4, 16);
                        break;
                    }
                    case Util.StandID.THE_WORLD: {
                        if (timeLeft > 780 && cooldown == 0 && invulnerableTicks == 0)
                            renderTimeLeft(timeLeft - 780);

                        renderString("Momentum: " + stand.getMomentum(), 4, 16);
                        break;
                    }
                    case Util.StandID.STAR_PLATINUM: {
                        if (timeLeft > 900 && cooldown == 0 && invulnerableTicks == 0)
                            renderTimeLeft(timeLeft - 900);
                        break;
                    }
                    case Util.StandID.STICKY_FINGERS:
                    case Util.StandID.THE_GRATEFUL_DEAD: {
                        if (timeLeft > 601 && cooldown == 0)
                            renderTimeLeft(timeLeft - 600);
                        break;
                    }
                    case Util.StandID.TUSK_ACT_2:
                    case Util.StandID.TUSK_ACT_1: {
                        renderString("Nails left: " + (10 - abilityUseCount));
                        if (charging && damage > 4.5f && cooldown == 0)
                            renderString("Damage: " + damage + (damage == (standID == Util.StandID.TUSK_ACT_1 ? 15 : 26) ? " MAX DAMAGE" : ""), 4, 16);
                        break;
                    }
                    case Util.StandID.TUSK_ACT_3: {
                        renderString("Nails left: " + (10 - abilityUseCount));
                        if (charging && damage > 7 && cooldown == 0)
                            renderString("Damage: " + damage + (damage == (act == 2 ? 15 : 26) ? " MAX DAMAGE" : ""), 4, act == 1 && stand.getAbilityActive() ? 32 : 16);
                        if (stand.getAbilityActive() && act == 0 && cooldown == 0)
                            renderString((timeLeft - 800) / 20 + " seconds left.", 4, 16);
                        break;
                    }
                    case Util.StandID.TUSK_ACT_4: {
                        renderString("Nails left: " + (10 - abilityUseCount));
                        int maxDamage = 0;
                        switch (act) {
                            case 4:
                            case 0: {
                                maxDamage = 55;
                                break;
                            }
                            case 1:
                            case 2: {
                                maxDamage = 26;
                                break;
                            }
                            case 3: {
                                maxDamage = 15;
                                break;
                            }
                            default:
                                break;
                        }
                        if (charging && damage > 7 && cooldown == 0)
                            renderString("Damage: " + damage + (damage == maxDamage ? " MAX DAMAGE" : ""), 4, act == 1 && stand.getAbilityActive() ? 32 : 16);
                        if (stand.getAbilityActive() && act == 1 && cooldown == 0)
                            renderString((timeLeft - 800) / 20 + " seconds left.", 4, 16);
                        break;
                    }
                    case Util.StandID.ECHOES_ACT_3:
                    case Util.StandID.ECHOES_ACT_2: {
                        if (act == stand.getAct() - 2)
                            renderString("Sound effects left: " + (4 - abilityUseCount), 4, cooldown > 0 ? 16 : 4);
                        break;
                    }
                }
            }
            switch (standID) {
                default: {
                    if (cooldown > 0)
                        renderCooldown(cooldown);
                    break;
                }
                case Util.StandID.GOLD_EXPERIENCE: {
                    if (cooldown > 0 && transformed > 0)
                        renderCooldown(cooldown);
                    break;
                }
                case Util.StandID.MADE_IN_HEAVEN: {
                    if (cooldown > 0)
                        renderString("Cooldown: " + cooldown / 20, 4, (stand.getStandOn() && act == 0 ? 16 : 4));
                    if (invulnerableTicks > 0)
                        renderString("Invulnerable ticks: " + (int) (invulnerableTicks / 20), 4, stand.getStandOn() ? 16 : 4);
                    break;
                }
                case Util.StandID.GER: {
                    if (cooldown > 0 && transformed > 1)
                        renderCooldown(cooldown);
                    break;
                }
                case Util.StandID.STAR_PLATINUM:
                case Util.StandID.THE_WORLD:
                case Util.StandID.KING_CRIMSON: {
                    if (cooldown > 0)
                        renderString("Cooldown: " + cooldown / 20, 4, (invulnerableTicks > 0 ? 16 : 4));
                    if (invulnerableTicks > 0)
                        renderString("Invulnerable ticks: " + (int) (invulnerableTicks / 20));
                    break;
                }
                case Util.StandID.TUSK_ACT_4:
                case Util.StandID.TUSK_ACT_3:
                case Util.StandID.TUSK_ACT_2:
                case Util.StandID.TUSK_ACT_1: {
                    if (cooldown > 0)
                        renderString("Cooldown: " + cooldown / 20, 4, stand.getStandOn() ? 16 : 4);
                    break;
                }
                case Util.StandID.KILLER_QUEEN: {
                    if (cooldown > 0) {
                        renderTimeValue(cooldown);
                    }
                    break;
                }
                case Util.StandID.PURPLE_HAZE: {

                }
                break;
            }
        });
        StandEffects.getLazyOptional(mc.player).ifPresent(standEffects -> {
            long timeLeft = standEffects.getTimeOfDeath() - mc.player.world.getGameTime();
            if (timeLeft > 0)
                renderString("Time until death: " + timeLeft / 20 + " seconds", 4, 350);
        });
    }
}
