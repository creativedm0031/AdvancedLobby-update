package de.cyne.advancedlobby.itembuilder;

import de.cyne.advancedlobby.crossversion.VCompat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder extends ItemStack {

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
            VCompat.applySkullTexture((SkullMeta) this.meta, texture);
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
