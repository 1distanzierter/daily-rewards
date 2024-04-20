package de.artur.dailyrewards.commands;


import de.artur.dailyrewards.CoinAPI;
import de.artur.dailyrewards.DailyRewards;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DailyCommand implements CommandExecutor {

    String Prefix = DailyRewards.getInstance().getConfig().getString("Prefix");

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String l, String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = simpleDateFormat.format(new Date());
        File f = new File("plugins/DailyRewards", "claimed.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);


        if(s instanceof Player) {
            Player p = (Player) s;
            if(today.equals(cfg.getString("claimed." + p.getUniqueId()))) {
                p.playSound(p, Sound.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                p.sendMessage(Prefix + "§cyou already claimed your reward today! §7please try again tomorrow,");
                return true;
            }
            p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            p.sendMessage(Prefix + "§aYou succesfully claimed your daily reward! §7you can claim it again tomorrow.");
            Random random = new Random();
            Integer coins = random.nextInt(10000);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§7+§6" + coins + "§6 Coins"));
            CoinAPI.addCoins(p.getUniqueId(), coins);
            cfg.set("claimed." + p.getUniqueId(), today);
            try {
                cfg.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(Prefix + "§6" + p.getName() + "§7 used the §a/daily §7command and got §6" + coins + "§7 Coins!");
            }
            return true;
        }

        return false;
    }
}
