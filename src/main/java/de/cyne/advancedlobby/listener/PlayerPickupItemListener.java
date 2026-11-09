package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (!AdvancedLobby.multiWorld_mode | AdvancedLobby.lobbyWorlds.contains(p.getWorld())) {
            if (!AdvancedLobby.build.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

}
