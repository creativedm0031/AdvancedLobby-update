package de.cyne.advancedlobby.itembuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder extends ItemStack {

    private static final Pattern TEXTURE_URL = Pattern.compile("\"url\"\\s*:\\s*\"([^\"]+)\"");

    private ItemMeta meta;

    public ItemBuilder(Material material, int amount, short durability) {
        // Must use a public ItemStack constructor: on Paper the ItemStack delegates to a server-backed
        // stack that is only created there (the protected no-arg constructor leaves it null).
        super(material == null ? Material.BARRIER : material, amount);
        if (durability != 0) {
            this.setDurability(durability);
        }
        this.meta = this.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    public ItemBuilder(Material material) {
        this(material, 1, (short) 0);
    }

    public ItemBuilder(ItemStack itemStack) {
        this(itemStack.getType(), itemStack.getAmount(), itemStack.getDurability());
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.meta.setDisplayName(displayName);
        return this.build();
    }

    public ItemBuilder setLore(String... lore) {
        this.meta.setLore(Arrays.asList(lore));
        return this.build();
    }

    public ItemBuilder addItemFlags(ItemFlag... flag) {
        this.meta.addItemFlags(flag);
        return this.build();
    }

    public ItemBuilder setLore(List<String> lore) {
        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        this.meta.setLore(lore);
        return this.build();
    }

    /**
     * Applies a base64 encoded skin texture (as used by e.g. minecraft-heads.com) to a player head.
     */
    public ItemBuilder setSkullOwner(String texture) {
        try {
            SkullMeta skullMeta = (SkullMeta) this.meta;
            String decoded = new String(Base64.getDecoder().decode(texture), StandardCharsets.UTF_8);
            Matcher matcher = TEXTURE_URL.matcher(decoded);
            if (!matcher.find()) {
                return this.build();
            }
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "AdvancedLobby");
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(URI.create(matcher.group(1)).toURL());
            profile.setTextures(textures);
            skullMeta.setOwnerProfile(profile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.build();
    }

    public ItemBuilder build() {
        this.setItemMeta(this.meta);
        return this;
    }
}
