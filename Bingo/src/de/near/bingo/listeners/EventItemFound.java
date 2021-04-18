package de.near.bingo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.near.bingo.Bingo;
import de.near.bingo.listeners.events.ItemFoundEvent;
import de.near.bingo.managers.GamerManager;

public class EventItemFound implements Listener {
    private Bingo plugin;
    private GamerManager gameManager;

    public EventItemFound(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemFound(ItemFoundEvent event) {
        if (!gameManager.isGameInProgress()) return;
        plugin.getPlayer(event.getPlayer().getUniqueId()).getCard().checkItem(event.getItemType());
    }

}
