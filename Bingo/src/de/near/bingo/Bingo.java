package de.near.bingo;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.near.bingo.commands.CommandBingo;
import de.near.bingo.events.EventAsyncPreLogin;
import de.near.bingo.events.EventGameStartEnd;
import de.near.bingo.events.EventInventoryClick;
import de.near.bingo.events.EventItemFound;
import de.near.bingo.events.EventPlayerInteract;
import de.near.bingo.events.EventPlayerJoinQuit;
import de.near.bingo.events.EventsItemAcquire;
import de.near.bingo.managers.BingoPlayer;
import de.near.bingo.managers.ConfigurationManager;
import de.near.bingo.managers.GamerManager;

public class Bingo extends JavaPlugin {
    private static Bingo instance;
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private GamerManager gameManager;

    private HashMap<UUID, BingoPlayer> players;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        configurationManager.setup();

        this.players = new HashMap<>();
        this.gameManager = new GamerManager(this);

        init();

        // Verify configuration for items
        if (getConfig().getString("itemMode") == null) throw new NullPointerException("Item mode is null");
        if (getConfig().getString("itemMode").equalsIgnoreCase("whitelist") && getConfig().getStringList("whitelist").size() < 25)
            throw new IllegalArgumentException("Item mode is set to Whitelist, but there are less than 25 items specified.");
    }

    @Override
    public void onDisable() {
        players.clear();
    }

    public HashMap<UUID, BingoPlayer> getPlayers() {
        return players;
    }

    public FileConfiguration getLang() {
        return configurationManager.getLang();
    }

    public BingoPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public GamerManager getGameManager() {
        return gameManager;
    }

    public static Bingo getInstance() {
        return instance;
    }

    private void init() {
        new CommandBingo(this);

        new EventPlayerJoinQuit(this);
        new EventInventoryClick(this);
        new EventsItemAcquire(this);
        new EventAsyncPreLogin(this);
        new EventGameStartEnd(this);
        new EventPlayerInteract(this);
        new EventItemFound(this);
    }

}
