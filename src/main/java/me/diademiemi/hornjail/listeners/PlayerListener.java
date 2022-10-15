package me.diademiemi.hornjail.listeners;

import me.diademiemi.hornjail.area.Area;
import me.diademiemi.hornjail.area.AreaList;
import me.diademiemi.hornjail.command.Format;
import me.diademiemi.hornjail.jail.Jail;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.GOAT_HORN) {
                if (e.getPlayer().hasPermission("hornjail.bypass")) {
                    return;
                }
                Area area = AreaList.getAreaByLocation(e.getPlayer().getLocation());
                if (area != null && !Jail.isWhitelist() || area == null && Jail.isWhitelist()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Format.format(Jail.getDenyMessage()));
                    if (Jail.isJailEnabled()) {
                        e.getPlayer().teleport(Jail.getJail().getLocation());
                        e.getPlayer().sendMessage(Format.format(Jail.getJailMessage()));
                    }
                }
            }
        }
    }

}
