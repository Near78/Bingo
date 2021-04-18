package de.near.bingo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import de.near.bingo.Bingo;
import de.near.bingo.guis.GUI;

public class EventInventoryClick implements Listener {
    public EventInventoryClick(Bingo plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        InventoryHolder holder = event.getInventory().getHolder();
        event.getInventory().getType();

        if (event.getClickedInventory() == null || itemStack == null)
            return;
        if (holder instanceof GUI) {
            event.setCancelled(true);
            GUI gui = (GUI) event.getInventory().getHolder();
            gui.handle(event);
        }
    }
}
