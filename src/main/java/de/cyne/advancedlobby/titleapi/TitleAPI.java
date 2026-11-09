package de.cyne.advancedlobby.titleapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Title / actionbar / tablist helper based on the Bukkit API (no NMS reflection anymore).
 */
public class TitleAPI {

    public static void sendActionBar(Player player, String message) {
        if (message == null) return;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null)
            header = "";
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null)
            footer = "";
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        player.setPlayerListHeaderFooter(header, footer);
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                                 String subtitle) {
        player.sendTitle(title == null ? "" : title, subtitle == null ? "" : subtitle, fadeIn, stay, fadeOut);
    }

}
