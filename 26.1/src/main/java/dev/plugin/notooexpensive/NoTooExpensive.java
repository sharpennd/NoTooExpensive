package dev.plugin.notooexpensive;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoTooExpensive extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AnvilListener(this), this);
        getLogger().info("NoTooExpensive enabled – 'Too Expensive' cost cap removed.");
    }

    @Override
    public void onDisable() {
        getLogger().info("NoTooExpensive disabled.");
    }
}
