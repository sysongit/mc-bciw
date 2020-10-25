package tk.rmrf.sys.bciw;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftItemListener implements Listener {

    Main plugin;

    public CraftItemListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    void onCraftItemEvent(CraftItemEvent event){
        if(plugin.getManager().isLogBook(event.getCurrentItem())){

        }
    }
}
