package de.cyne.advancedlobby.crossversion;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Material constants used by the plugin. Since 1.13 (and thus on 26.x) every material has a
 * flattened name, so this enum simply maps to {@link Material} by name.
 */
public enum VMaterial {

    RED_DYE,
    PLAYER_HEAD,
    BLACK_STAINED_GLASS_PANE,
    RED_TERRACOTTA,
    YELLOW_TERRACOTTA,
    LIME_TERRACOTTA,
    LIGHT_BLUE_TERRACOTTA,
    LEAD,
    MELON,
    MUSIC_DISC_STRAD,
    FIRE_CHARGE,

    CRAFTING_TABLE,
    ENCHANTING_TABLE,
    DAYLIGHT_DETECTOR,
    COMPARATOR,
    REPEATER,

    BLACK_BED,
    BLUE_BED,
    BROWN_BED,
    CYAN_BED,
    GRAY_BED,
    GREEN_BED,
    LIGHT_BLUE_BED,
    LIGHT_GRAY_BED,
    LIME_BED,
    MAGENTA_BED,
    ORANGE_BED,
    PINK_BED,
    PURPLE_BED,
    RED_BED,
    WHITE_BED,
    YELLOW_BED,

    ACACIA_BUTTON,
    BIRCH_BUTTON,
    DARK_OAK_BUTTON,
    JUNGLE_BUTTON,
    OAK_BUTTON,
    SPRUCE_BUTTON,

    ACACIA_PRESSURE_PLATE,
    BIRCH_PRESSURE_PLATE,
    DARK_OAK_PRESSURE_PLATE,
    HEAVY_WEIGHTED_PRESSURE_PLATE,
    JUNGLE_PRESSURE_PLATE,
    LIGHT_WEIGHTED_PRESSURE_PLATE,
    OAK_PRESSURE_PLATE,
    SPRUCE_PRESSURE_PLATE,
    STONE_PRESSURE_PLATE;

    private final Material material;

    VMaterial() {
        this.material = Material.getMaterial(name());
    }

    public Material getType() {
        return material;
    }

    public short getSubId() {
        return 0;
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(this.getType(), amount);
    }

    public ItemStack toItemStack() {
        return toItemStack(1);
    }

    public boolean equals(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() == getType();
    }

}
