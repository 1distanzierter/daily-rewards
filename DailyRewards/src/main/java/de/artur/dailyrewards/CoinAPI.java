package de.artur.dailyrewards;


import de.artur.dailyrewards.DailyRewards;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CoinAPI{



    public static Integer getCoins(UUID uuid) {
        int i = 0;

        createPlayer(String.valueOf(uuid));

        try {
            ResultSet rs = DailyRewards.mysql.query("SELECT * FROM coinapi WHERE UUID= '" + uuid + "'");

            if(rs.next())
                i = rs.getInt("COINS");

        } catch (SQLException var3) {
            var3.printStackTrace();
        }
        return i;
    }


    public static void addCoins(UUID uuid, int amount) {
        if (playerExists(String.valueOf(uuid))) {
            setCoins(uuid, getCoins(uuid) + amount);
        } else {
            createPlayer(String.valueOf(uuid));
            addCoins(uuid, amount);
        }
    }


    public static  void removeCoins(UUID uuid, int amount) {
        if (playerExists(String.valueOf(uuid))) {
            setCoins(uuid, getCoins(uuid) - amount);
        } else {
            createPlayer(String.valueOf(uuid));
            removeCoins(uuid, amount);
        }
    }


    public static void setCoins(UUID uuid, int amount) {
        if (playerExists(String.valueOf(uuid))) {
            DailyRewards.mysql.update("UPDATE coinapi SET COINS= '" + amount + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(String.valueOf(uuid));
            setCoins(uuid, amount);
        }
    }

    public static boolean playerExists(String uuid) {
        try {
            ResultSet rs = DailyRewards.mysql.query("SELECT * FROM coinapi WHERE UUID= '" + uuid + "'");
            return rs.next();
        } catch (SQLException var2) {
            var2.printStackTrace();
            return false;
        }
    }
    public static void createPlayer(String uuid) {
        if (!playerExists(uuid)) {
            DailyRewards.mysql.update("INSERT INTO coinapi(UUID, COINS) VALUES ('" + uuid + "', '1000');");
        }

    }
}

