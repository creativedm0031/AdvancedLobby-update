package de.cyne.advancedlobby.crossversion;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Particle helper. Accepts both the current Bukkit particle names and the legacy (pre 1.20.5)
 * names that are still used in this plugin and in older configurations.
 */
public class VParticle {

    private static final Map<String, String> LEGACY_NAMES = new HashMap<>();

    static {
        LEGACY_NAMES.put("SPELL", "EFFECT");
        LEGACY_NAMES.put("SPELL_MOB", "ENTITY_EFFECT");
        LEGACY_NAMES.put("SPELL_INSTANT", "INSTANT_EFFECT");
        LEGACY_NAMES.put("SPELL_WITCH", "WITCH");
        LEGACY_NAMES.put("EXPLOSION_NORMAL", "POOF");
        LEGACY_NAMES.put("EXPLOSION_LARGE", "EXPLOSION");
        LEGACY_NAMES.put("EXPLOSION_HUGE", "EXPLOSION_EMITTER");
        LEGACY_NAMES.put("FIREWORKS_SPARK", "FIREWORK");
        LEGACY_NAMES.put("VILLAGER_HAPPY", "HAPPY_VILLAGER");
        LEGACY_NAMES.put("VILLAGER_ANGRY", "ANGRY_VILLAGER");
        LEGACY_NAMES.put("REDSTONE", "DUST");
        LEGACY_NAMES.put("SMOKE_NORMAL", "SMOKE");
        LEGACY_NAMES.put("SMOKE_LARGE", "LARGE_SMOKE");
        LEGACY_NAMES.put("WATER_BUBBLE", "BUBBLE");
        LEGACY_NAMES.put("WATER_SPLASH", "SPLASH");
        LEGACY_NAMES.put("WATER_WAKE", "FISHING");
        LEGACY_NAMES.put("SUSPENDED", "UNDERWATER");
        LEGACY_NAMES.put("SUSPENDED_DEPTH", "UNDERWATER");
        LEGACY_NAMES.put("CRIT_MAGIC", "ENCHANTED_HIT");
        LEGACY_NAMES.put("ENCHANTMENT_TABLE", "ENCHANT");
        LEGACY_NAMES.put("DRIP_WATER", "DRIPPING_WATER");
        LEGACY_NAMES.put("DRIP_LAVA", "DRIPPING_LAVA");
        LEGACY_NAMES.put("SNOWBALL", "ITEM_SNOWBALL");
        LEGACY_NAMES.put("SNOW_SHOVEL", "ITEM_SNOWBALL");
        LEGACY_NAMES.put("SLIME", "ITEM_SLIME");
        LEGACY_NAMES.put("TOWN_AURA", "MYCELIUM");
        LEGACY_NAMES.put("ITEM_CRACK", "ITEM");
        LEGACY_NAMES.put("BLOCK_CRACK", "BLOCK");
        LEGACY_NAMES.put("BLOCK_DUST", "BLOCK");
        LEGACY_NAMES.put("TOTEM", "TOTEM_OF_UNDYING");
        LEGACY_NAMES.put("MOB_APPEARANCE", "ELDER_GUARDIAN");
    }

    /**
     * Resolves a particle by name. Works with both the legacy (pre 1.20.5) and the current names,
     * on old and new servers.
     */
    public static Particle getParticle(String name) {
        String upper = name.toUpperCase(Locale.ROOT);
        Particle particle = byName(upper);
        if (particle == null && LEGACY_NAMES.containsKey(upper)) {
            particle = byName(LEGACY_NAMES.get(upper));
        }
        if (particle == null) {
            for (Map.Entry<String, String> entry : LEGACY_NAMES.entrySet()) {
                if (entry.getValue().equals(upper) && (particle = byName(entry.getKey())) != null) break;
            }
        }
        if (particle == null) {
            throw new IllegalArgumentException("Unknown particle: " + name);
        }
        return particle;
    }

    private static Particle byName(String name) {
        try {
            return Particle.valueOf(name);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static void spawnParticle(Player player, String particle, Location location, int count) {
        spawnParticle(player, particle, location, count, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void spawnParticle(Player player, String particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        Particle type = getParticle(particle);
        Object data = defaultData(type);
        if (data != null) {
            player.spawnParticle(type, location, count, offsetX, offsetY, offsetZ, extra, data);
        } else {
            player.spawnParticle(type, location, count, offsetX, offsetY, offsetZ, extra);
        }
    }

    /**
     * Some particles require additional data since 1.20.5 (e.g. ENTITY_EFFECT needs a colour, DUST needs
     * DustOptions). A random colour is used so that e.g. the "rainbow" cosmetic keeps its look.
     */
    private static Object defaultData(Particle type) {
        Class<?> dataType = type.getDataType();
        if (dataType == Color.class) {
            return randomColor();
        }
        if (dataType == Particle.DustOptions.class) {
            return new Particle.DustOptions(randomColor(), 1.0f);
        }
        if (dataType == Float.class) {
            return 0.0f;
        }
        if (dataType == Integer.class) {
            return 0;
        }
        return null;
    }

    private static Color randomColor() {
        java.awt.Color c = java.awt.Color.getHSBColor(ThreadLocalRandom.current().nextFloat(), 1.0f, 1.0f);
        return Color.fromRGB(c.getRed(), c.getGreen(), c.getBlue());
    }

}
