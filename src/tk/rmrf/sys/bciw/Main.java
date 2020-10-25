package tk.rmrf.sys.bciw;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    LogBookManager manager;

    @Override
    public void onEnable() {
        manager = new LogBookManager(this);

        this.getServer().getWorlds().get(0).getBlockAt(0,4,0).setType(Material.REDSTONE_BLOCK);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public LogBookManager getManager() {
        return manager;
    }
}
