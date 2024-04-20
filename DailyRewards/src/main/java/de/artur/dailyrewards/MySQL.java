package de.artur.dailyrewards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;

public class MySQL {
    private String HOST = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";
    private Connection con;

    public MySQL(String host, String database, String user, String password) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        this.connect();
    }

    public void connect() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
            Bukkit.getConsoleSender().sendMessage("§r§6MySQL §7erfolgreich verbunden!");
        } catch (SQLException var2) {
            Bukkit.getConsoleSender().sendMessage("§r§cBeim verbinden mit MySQL ist ein Fehler aufgetreten!");
            Bukkit.getConsoleSender().sendMessage("§r§cBitte überprüfe deine Logindaten!");
        }

    }

    public void close() {
        try {
            if (this.con != null) {
                this.con.close();
                Bukkit.getConsoleSender().sendMessage(" §r§6MySQL §7erfolgreich getrennt!");
            }
        } catch (SQLException var2) {
            Bukkit.getConsoleSender().sendMessage("§r§c Es gab einen Fehler beim beenden der Verbindung zu der MySQL Datenbank!");
        }

    }

    public void update(String qry) {
        try {
            Statement st = this.con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException var3) {
            this.connect();
            System.err.println(var3);
        }

    }
    public ResultSet query(String qry) {
        ResultSet rs = null;

        try {
            Statement st = this.con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException var4) {
            this.connect();
            System.err.println(var4);
        }

        return rs;
    }
}


