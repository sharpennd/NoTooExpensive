package dev.plugin.notooexpensive;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    private static final int MAX_COST_THRESHOLD = 40;

    private final NoTooExpensive plugin;

    public AnvilListener(NoTooExpensive plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack result = event.getResult();

        if (result == null) {
            return;
        }

        int cost = anvil.getRepairCost();

        if (cost >= MAX_COST_THRESHOLD) {
            anvil.setRepairCost(Math.min(cost, 39));

            ItemMeta meta = result.getItemMeta();
            if (meta != null) {
                meta.setRepairCost(0);
                result.setItemMeta(meta);
            }

            event.setResult(result);
        }
    }
}
