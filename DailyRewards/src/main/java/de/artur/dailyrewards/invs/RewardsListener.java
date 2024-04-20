package de.artur.dailyrewards.invs;

import de.artur.dailyrewards.DailyRewards;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class RewardsListener implements Listener {
    //Get the Prefix
    String Prefix = DailyRewards.getInstance().getConfig().getString("Prefix");

    //get the day
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String today = simpleDateFormat.format(new Date());

    //Get the config
    File f = new File("plugins/DailyRewards", "claimed.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);


    //The Join Message
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);

        if(!today.equals(cfg.getString("claimed." + p.getUniqueId()))) {
            p.playSound(p, Sound.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            p.sendMessage(Prefix + "§7you can claim your dailyreward use §a/daily§7!");
        }

    }

}
