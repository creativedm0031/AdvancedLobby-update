package de.cyne.advancedlobby.crossversion;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Small compatibility layer for API differences between Minecraft 1.17 and 26.x.
 * The plugin is compiled against the 1.17 API; everything newer is resolved at runtime.
 */
public final class VCompat {

    private static final Pattern TEXTURE_URL = Pattern.compile("\"url\"\\s*:\\s*\"([^\"]+)\"");

    private static Attribute maxHealthAttribute;
    private static boolean maxHealthResolved;

    private VCompat() {
    }

    /**
     * Max health attribute: {@code GENERIC_MAX_HEALTH} up to 1.21.2, {@code MAX_HEALTH} since 1.21.3.
     */
    public static Attribute getMaxHealthAttribute() {
        if (!maxHealthResolved) {
            maxHealthAttribute = staticConstant(Attribute.class, "MAX_HEALTH", "GENERIC_MAX_HEALTH");
            maxHealthResolved = true;
        }
        return maxHealthAttribute;
    }

    @SuppressWarnings("deprecation")
    public static void setMaxHealth(Player player, double value) {
        Attribute attribute = getMaxHealthAttribute();
        AttributeInstance instance = attribute == null ? null : player.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        } else {
            player.setMaxHealth(value);
        }
    }

    /**
     * Returns the first item flag of the given names that exists on this server version, or null.
     * E.g. HIDE_POTION_EFFECTS was replaced by HIDE_ADDITIONAL_TOOLTIP in 1.20.5.
     */
    public static ItemFlag itemFlag(String... names) {
        for (String name : names) {
            try {
                return ItemFlag.valueOf(name);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return null;
    }

    public static ItemFlag[] itemFlags(String... names) {
        java.util.List<ItemFlag> flags = new java.util.ArrayList<>();
        for (String name : names) {
            ItemFlag flag = itemFlag(name);
            if (flag != null) flags.add(flag);
        }
        return flags.toArray(new ItemFlag[0]);
    }

    /**
     * Applies a base64 encoded skin texture (as used by minecraft-heads.com) to a skull.
     * Uses the PlayerProfile API (1.18.1+) and falls back to the authlib GameProfile (1.17).
     */
    public static void applySkullTexture(SkullMeta skullMeta, String texture) throws Exception {
        try {
            Method createProfile = Bukkit.class.getMethod("createPlayerProfile", UUID.class, String.class);
            String decoded = new String(Base64.getDecoder().decode(texture), StandardCharsets.UTF_8);
            Matcher matcher = TEXTURE_URL.matcher(decoded);
            if (!matcher.find()) return;
            URL skin = URI.create(matcher.group(1)).toURL();

            Object profile = createProfile.invoke(null, UUID.randomUUID(), "AdvancedLobby");
            Class<?> profileClass = Class.forName("org.bukkit.profile.PlayerProfile");
            Object textures = profileClass.getMethod("getTextures").invoke(profile);
            Class<?> texturesClass = Class.forName("org.bukkit.profile.PlayerTextures");
            texturesClass.getMethod("setSkin", URL.class).invoke(textures, skin);
            profileClass.getMethod("setTextures", texturesClass).invoke(profile, textures);
            SkullMeta.class.getMethod("setOwnerProfile", profileClass).invoke(skullMeta, profile);
        } catch (NoSuchMethodException | ClassNotFoundException legacy) {
            applyLegacySkullTexture(skullMeta, texture);
        }
    }

    private static void applyLegacySkullTexture(SkullMeta skullMeta, String texture) throws Exception {
        Class<?> gameProfileClass = Class.forName("com.mojang.authlib.GameProfile");
        Class<?> propertyClass = Class.forName("com.mojang.authlib.properties.Property");
        Object profile = gameProfileClass.getConstructor(UUID.class, String.class).newInstance(UUID.randomUUID(), "AdvancedLobby");
        Object properties = gameProfileClass.getMethod("getProperties").invoke(profile);
        Object property = propertyClass.getConstructor(String.class, String.class).newInstance("textures", texture);
        properties.getClass().getMethod("put", Object.class, Object.class).invoke(properties, "textures", property);

        Field field = skullMeta.getClass().getDeclaredField("profile");
        field.setAccessible(true);
        field.set(skullMeta, profile);
    }

    @SuppressWarnings("unchecked")
    private static <T> T staticConstant(Class<T> type, String... names) {
        for (String name : names) {
            try {
                Object value = type.getField(name).get(null);
                if (type.isInstance(value)) return (T) value;
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return null;
    }
}
