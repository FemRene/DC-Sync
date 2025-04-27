package dev.femrene.dCSync_Paper;

import dev.femrene.dCSync_Paper.commands.VerifyCommand;
import dev.femrene.dCSync_Paper.util.DCManager;
import dev.femrene.dCSync_Paper.util.MySqlManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

public final class DCSync_Paper extends JavaPlugin {

    public HashMap<UUID, String> activeCodes;
    private static DCSync_Paper instance;
    private static Connection conn;
    private static MySqlManager mySqlManager;

    @Override
    public void onEnable() {
        instance = this;
        activeCodes = new HashMap<>();
        saveDefaultConfig();
        DCManager.startDCBot();
        mySqlManager = new MySqlManager().getInstance();
        conn = mySqlManager.getConnection();
        registerCommands();
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        DCManager.stopDCBot();
        mySqlManager.closeConnection();
        activeCodes.clear();
        activeCodes = null;
        conn = null;
        mySqlManager = null;
        instance = null;
        // Plugin shutdown logic
    }

    private static void registerCommands() {
        getInstance().getCommand("verify").setExecutor(new VerifyCommand());
    }

    private static void registerEvents() {

    }

    public static DCSync_Paper getInstance() {
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    public MySqlManager getMySqlManager() {
        return mySqlManager;
    }
}