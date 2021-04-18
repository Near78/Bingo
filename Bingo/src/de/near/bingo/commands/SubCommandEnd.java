package de.near.bingo.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.near.bingo.Bingo;
import de.near.bingo.listeners.events.GameEndEvent;
import de.near.bingo.managers.GamerManager;
import de.near.bingo.utils.Message;
import de.near.subcommand.SubCommand;

public class SubCommandEnd extends SubCommand {
	private Bingo plugin;
    private GamerManager gameManager;

    SubCommandEnd(Bingo plugin) {
        this.gameManager = plugin.getGameManager();
    }

    @Override
    public String getName() {
        return "end";
    }

    @Override
    public String getDescription() {
        return "End the game manually";
    }

    @Override
    public String getUsage() {
        return "/bingo end";
    }

    @Override
    public String getPermission() {
        return "bingo.end";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"e"};
    }

    @Override
    public void handle(Player player, String[] args) {
        if (!gameManager.isGameInProgress()) {
            player.sendMessage(Message.info("chat.gameNotStarted"));
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameEndEvent.Outcome.DEFAULT, player));
        player.sendMessage(Message.success("chat.manualGameStop"));
    }

}
