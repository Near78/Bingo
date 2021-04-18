package de.near.bingo.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import de.near.bingo.Bingo;
import de.near.bingo.guis.GuiBingo;

public class EventPlayerInteract implements Listener {
    private Bingo plugin;

    public EventPlayerInteract(Bingo plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getMaterial().equals(Material.PAPER) &&
                (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) &&
                event.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "bingoCardOpener"), PersistentDataType.BYTE)) {
            new GuiBingo(plugin, event.getPlayer(), null).show();
        }
    }

}
