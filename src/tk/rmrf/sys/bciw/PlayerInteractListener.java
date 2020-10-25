package tk.rmrf.sys.bciw;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    Main plugin;

    final int click_delay = 500; //Milliseconds, higher values may cause unintentional actions

    HashMap<Player, Long> player_click_time_map; //Maps players to the last time they right clicked with the LogBook
    HashMap<Player, Integer> player_click_streak_map; //Maps players to their "click streak"

    public PlayerInteractListener(Main plugin){
        this.plugin = plugin;
        player_click_time_map = new HashMap<>();
        player_click_streak_map = new HashMap<>();
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        //We want the player to sneak...
        if(!player.isSneaking()){
            return;
        }
        //... and left-click
        if(event.getAction() != Action.LEFT_CLICK_AIR){
            if(event.getAction() == Action.RIGHT_CLICK_AIR){
                plugin.getManager().dumpData(item);
            }
        }

        //Verify that the item is a log book
        if(!plugin.getManager().isLogBook(item)){
            return;
        }

        // Only the owner can use his log book
        if(!plugin.getManager().isOwner(item, player)){
            return;
        }

        //Init maps if the player never clicked before
        if(!player_click_time_map.containsKey(player)){
            player_click_time_map.put(player, System.currentTimeMillis());
        }
        if(!player_click_streak_map.containsKey(player)){
            player_click_streak_map.put(player, 0);
        }

        //Count
        if((System.currentTimeMillis() - player_click_time_map.get(player)) <= click_delay){
            player_click_streak_map.put(player, player_click_streak_map.get(player) + 1);
        }else{
            player_click_streak_map.put(player, 1);
        }

        // Actualise the "last click" timestamp
        player_click_time_map.put(player, System.currentTimeMillis());

        switch (player_click_streak_map.get(player)){
            case 1:
                plugin.getManager().setLogBookOwner(item, player);
                player.playNote(player.getLocation(), Instrument.BIT, Note.natural(1, Note.Tone.C));
                break;
            case 2:
                plugin.getManager().clearLogBook(item);
                player.playNote(player.getLocation(), Instrument.BIT, Note.natural(1, Note.Tone.D));
                break;
            case 3:
                plugin.getManager().resetLogBook(item);
                player.playNote(player.getLocation(), Instrument.BIT, Note.natural(1, Note.Tone.E));
                break;
            default:
                break;
        }
    }
}
