package de.near.bingo.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.near.bingo.Bingo;
import de.near.bingo.listeners.events.GameStartEvent;
import de.near.bingo.managers.GamerManager;
import de.near.bingo.utils.Message;
import de.near.subcommand.SubCommand;

public class SubCommandStart extends SubCommand {
    private GamerManager gameManager;

    SubCommandStart(Bingo plugin) {
        this.gameManager = plugin.getGameManager();
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start the game manually";
    }

    @Override
    public String getUsage() {
        return "/bingo start";
    }

    @Override
    public String getPermission() {
        return "bingo.start";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"s"};
    }

    @Override
    public void handle(Player player, String[] args) {
        if (gameManager.isGameInProgress()) {
            player.sendMessage(Message.info("chat.gameStarted"));
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());
        gameManager.setGameInProgress(true);
        player.sendMessage(Message.success("chat.manualGameStart"));
    }

}
