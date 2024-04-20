package de.artur.dailyrewards;

import de.artur.dailyrewards.commands.DailyCommand;
import de.artur.dailyrewards.invs.RewardsListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class DailyRewards extends JavaPlugin {

    public static MySQL mysql;
    public static DailyRewards instance;
    @Override
    public void onEnable() {
        instance = this;
        System.out.println("Daily Rewards started");
        getServer().getPluginManager().registerEvents(new RewardsListener(), this);
        getCommand("daily").setExecutor(new DailyCommand());
        loadConfig();
        connectMySQL();

    }


    public void loadConfig() {
        getConfig().addDefault("Prefix", "§x§f§b§0§2§e§2D§x§f§b§1§b§e§5a§x§f§b§3§4§e§7i§x§f§c§4§d§e§al§x§f§c§6§6§e§dy§x§f§c§8§0§f§0r§x§f§c§9§9§f§2e§x§f§c§b§2§f§5w§x§f§d§c§b§f§8a§x§f§d§e§4§f§ar§x§f§d§f§d§f§dd §8× ");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        mysql.close();
    }

    public static DailyRewards getInstance() {
        return instance;
    }

    public void connectMySQL() {
        File mysqlf = new File("plugins/DailyRewards", "mysql.yml");
        YamlConfiguration mysqly = YamlConfiguration.loadConfiguration(mysqlf);
        if(mysqly.get("host") == null)
            mysqly.set("host", "host");
        if(mysqly.get("database") == null)
            mysqly.set("database", "database");
        if(mysqly.get("user") == null)
            mysqly.set("user", "user");
        if(mysqly.get("password") == null)
            mysqly.set("password", "password");
        try {
            mysqly.save(mysqlf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String host = mysqly.getString("host");
        String database = mysqly.getString("database");
        String user = mysqly.getString("user");
        String password = mysqly.getString("password");
        if (host.equalsIgnoreCase("host") && database.equalsIgnoreCase("database") && user.equalsIgnoreCase("user") && password.equalsIgnoreCase("password")) {
            Bukkit.getConsoleSender().sendMessage("§r§cBitte trage deine MySQL-Daten in der Config ein!");
            Bukkit.getPluginManager().disablePlugin(getInstance());
        } else {
            mysql = new MySQL(host, database, user, password);
            mysql.connect();
            mysql.update("CREATE TABLE IF NOT EXISTS coinapi(UUID varchar(64), COINS int);");
        }
    }
}
