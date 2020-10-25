package tk.rmrf.sys.bciw;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    Main plugin;

    public PlayerJoinListener(Main plugin){
        this.plugin = plugin;
    }
    @EventHandler
    void onPlayerJoinEvent(PlayerJoinEvent event){
        event.getPlayer().discoverRecipe(plugin.getManager().key_recipe);
    }
}
