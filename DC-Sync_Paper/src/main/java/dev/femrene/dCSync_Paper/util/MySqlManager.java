package dev.femrene.dCSync_Paper.util;

import dev.femrene.dCSync_Paper.DCSync_Paper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySqlManager {

    private static MySqlManager instance;
    private static DCSync_Paper plugin;
    private static FileConfiguration config;
    private static Connection conn;

    public MySqlManager() {
        plugin = DCSync_Paper.getInstance();
        config = plugin.getConfig();
        instance = this;

        String url = "jdbc:mysql://" +
                config.getString("mysql.host") + ":" +
                config.getString("mysql.port") + "/" +
                config.getString("mysql.database") +
                "?useSSL=false&serverTimezone=UTC";

        String user = config.getString("mysql.user");
        String password = config.getString("mysql.password");

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the MySQL database!");
                MySqlManager.conn = conn;

                // Create table if it doesn't exist
                String sql = "CREATE TABLE IF NOT EXISTS verifiedUsers (" +
                        "uuid VARCHAR(36) PRIMARY KEY, " +
                        "dcID VARCHAR(100) NOT NULL UNIQUE, " +
                        "verifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.execute();
                    System.out.println("verifiedUsers table ensured.");
                }
            }
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public boolean isUserVerified(Player p) {
        return isUserVerifiedByUUID(p.getUniqueId().toString());
    }

    public boolean isUserVerifiedByUUID(String uuid) {
        String sql = "SELECT * FROM verifiedUsers WHERE uuid = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean isUserVerifiedByDcID(String dcID) {
        String sql = "SELECT * FROM verifiedUsers WHERE dcid = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, dcID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void VerieyedUser(String uuid, String dcID) {
        String sql = "INSERT INTO verifiedUsers (uuid, dcID) VALUES (?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, dcID);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MySqlManager getInstance() {
        return instance;
    }
}