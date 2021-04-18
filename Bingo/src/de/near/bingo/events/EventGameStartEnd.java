package de.near.bingo.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import de.near.bingo.Bingo;
import de.near.bingo.listeners.events.GameEndEvent;
import de.near.bingo.listeners.events.GameStartEvent;
import de.near.bingo.managers.GamerManager;
import de.near.bingo.utils.ItemBuilder;
import de.near.bingo.utils.Utils;

public class EventGameStartEnd implements Listener {
    private Bingo plugin;
    private GamerManager gameManager;

    public EventGameStartEnd(Bingo plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) { 
        gameManager.generateCards();
        gameManager.setGameInProgress(true);
        gameManager.startGameCountdown();
        // Set game scoreboards to everyone
        plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
            bingoPlayer.setScoreboard(Utils.getGameScoreboard(Bukkit.getPlayer(uuid)));
            bingoPlayer.getPlayer().getInventory().clear();
            bingoPlayer.getPlayer().getInventory().addItem(new ItemBuilder(Material.PAPER)
                    .setDisplayName("&aYour Bingo card")
                    .addLoreLine("&7Right-click to open your Bingo card")
                    .setPersistentData(plugin, "bingoCardOpener", PersistentDataType.BYTE, (byte) 1)
                    .build());
        });
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        gameManager.setGameInProgress(false);
        gameManager.getGameCountdown().cancel();
        Player winner = event.getWinner();
        switch (event.getOutcome()) {
            case DEFAULT:
                plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                    // Handle everyone
                    if (winner.getUniqueId().equals(uuid)) {
                        // Handle winner
                        winner.playSound(winner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
                        winner.spawnParticle(Particle.FIREWORKS_SPARK, winner.getLocation(), 150);
                        winner.sendTitle(Utils.color(plugin.getLang().getString("other.victoryTitle")), Utils.color(plugin.getLang().getString("other.victorySubtitle")), 20, 100, 20);
                    } else {
                        // Handle loser
                        Player loser = Bukkit.getPlayer(uuid);
                        loser.sendTitle(Utils.color(plugin.getLang().getString("other.gameOverTitle")), Utils.color(plugin.getLang().getString("other.gameOverSubtitle").replace("$winner", winner.getDisplayName())), 20, 100, 20);
                        loser.playSound(loser.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);

                    }
                });
                break;
            case TIMEOUT:
                plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
                    Player player = Bukkit.getPlayer(uuid);
                    player.sendTitle(Utils.color(plugin.getLang().getString("other.gameOverTitle")), Utils.color(plugin.getLang().getString("other.gameOverSubtitle2")), 20, 100, 20);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
                });
                break;
            case NO_PLAYERS_LEFT:
                winner.playSound(winner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
                winner.spawnParticle(Particle.FIREWORKS_SPARK, winner.getLocation(), 150);
                winner.sendTitle(Utils.color(plugin.getLang().getString("other.victoryTitle")), Utils.color(plugin.getLang().getString("other.victorySubtitle2")), 20, 100, 20);
                break;
        }
        // Handle all players - UI
        plugin.getPlayers().forEach((uuid, bingoPlayer) -> {
            Player player = Bukkit.getPlayer(uuid);
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20d);
            player.setFoodLevel(20);
            bingoPlayer.getScoreboard().delete();
        });
        // Kick everyone and restart the server
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            plugin.getServer().getOnlinePlayers().forEach(o -> o.kickPlayer("This server is restarting!"));
            plugin.getServer().reload();
        }, 10 * 20);
    }

}
