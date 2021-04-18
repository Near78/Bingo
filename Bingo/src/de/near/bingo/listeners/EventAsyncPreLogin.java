package de.near.bingo.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import de.near.bingo.Bingo;
import de.near.bingo.managers.GamerManager;

public class EventAsyncPreLogin implements Listener {
    private Bingo plugin;
    private GamerManager gameManager;

    public EventAsyncPreLogin(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        if (gameManager.isGameInProgress())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "The game has already started.");
    }

}
