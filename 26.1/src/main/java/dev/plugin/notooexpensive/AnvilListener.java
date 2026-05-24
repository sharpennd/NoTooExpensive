package dev.plugin.notooexpensive;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    private static final int MAX_COST_THRESHOLD = 40; // vanilla "Too Expensive" kicks in at >= 40

    private final NoTooExpensive plugin;

    public AnvilListener(NoTooExpensive plugin) {
        this.plugin = plugin;
    }

    /**
     * Fires when the anvil calculates a result item.
     * If the repair cost would normally be blocked by "Too Expensive" (>= 40 levels),
     * we force the result through and cap the displayed cost at 39 so the slot is usable.
     *
     * We also strip the accumulated repair-cost penalty from the output item so the item
     * doesn't keep growing more expensive each time it is repaired.
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack result = event.getResult();

        // Nothing to do if there's no valid result
        if (result == null) {
            return;
        }

        int cost = anvil.getRepairCost();

        // Only intervene when vanilla would show "Too Expensive"
        if (cost >= MAX_COST_THRESHOLD) {
            // Set the repair cost shown in the anvil to a valid level so the
            // player can actually take the item out of the slot.
            anvil.setRepairCost(Math.min(cost, 39));

            // Reset the repair-penalty on the output item to 0 so it never
            // accumulates and becomes permanently "Too Expensive" in future uses.
            ItemMeta meta = result.getItemMeta();
            if (meta != null) {
                meta.setRepairCost(0);
                result.setItemMeta(meta);
            }

            event.setResult(result);

            plugin.getLogger().fine(() ->
                "Bypassed Too Expensive: original cost=" + cost + ", adjusted to " + anvil.getRepairCost());
        }
    }
}
