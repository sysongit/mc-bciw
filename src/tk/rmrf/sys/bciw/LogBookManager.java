package tk.rmrf.sys.bciw;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class LogBookManager {

    Main plugin;

    public final NamespacedKey key_recipe;
    public final NamespacedKey key_owner;

    public LogBookManager(Main plugin){
        this.plugin = plugin;
        key_recipe  = new NamespacedKey(plugin, "recipe");
        key_owner   = new NamespacedKey(plugin, "owner");

        registerLogBookRecipe();
    }

    public Recipe getLogBookRecipe(){
        ShapelessRecipe recipe = new ShapelessRecipe(key_recipe, getLogBookItem());

        recipe.addIngredient(Material.WRITABLE_BOOK);
        recipe.addIngredient(Material.ENDER_EYE);
        recipe.addIngredient(Material.TRIPWIRE_HOOK);

        return recipe;
    }

    private ArrayList<String> getLore(String name){
        ArrayList<String> lore = new ArrayList<>();

        lore.add(ChatColor.WHITE + "Owner: " + ChatColor.RED + name);
        lore.add("");
        lore.add(ChatColor.YELLOW + "Sneak + left-click to set owner");
        lore.add(ChatColor.YELLOW + "Sneak + double left-click to clear");
        lore.add(ChatColor.YELLOW + "Sneak + triple left-click to remove owner");
        lore.add("");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Place in chest for best results");
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Item added by BigChestIsWatching");
        return (lore);
    }

    public ItemStack getLogBookItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Log Book");
        meta.getPersistentDataContainer().set(key_owner, PersistentDataType.STRING, "");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        ArrayList<String> lore = getLore("Uninitialized");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private void registerLogBookRecipe(){
        Bukkit.addRecipe(getLogBookRecipe());
    }

    public boolean isLogBook(ItemStack item){
        return item.getItemMeta().getPersistentDataContainer().has(key_owner, PersistentDataType.STRING);
    }

    public void setLogBookOwner(ItemStack item, Player player){
        String player_uuid = player.getUniqueId().toString();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key_owner, PersistentDataType.STRING, player_uuid);
        item.setItemMeta(meta);
    }

    public void clearLogBook(ItemStack item){
        //todo
    }

    public void resetLogBook(ItemStack item){
        clearLogBook(item);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key_owner, PersistentDataType.STRING, "");
        item.setItemMeta(meta);
    }

    public boolean isOwner(ItemStack item, Player player){
        if(!isLogBook(item)){
            return false;
        }

        String player_uuid = player.getUniqueId().toString();
        String owner_uuid = item.getItemMeta().getPersistentDataContainer().get(key_owner, PersistentDataType.STRING);

        return owner_uuid.isEmpty() || (player_uuid.equals(owner_uuid));
    }
}
