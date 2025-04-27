package dev.femrene.dCSync_Paper.commands;

import dev.femrene.dCSync_Paper.DCSync_Paper;
import dev.femrene.dCSync_Paper.util.MySqlManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.sql.Connection;

public class VerifyCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    private static MySqlManager mySqlManager;
    private static DCSync_Paper plugin;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        plugin = DCSync_Paper.getInstance();
        mySqlManager = DCSync_Paper.getInstance().getMySqlManager() != null ? DCSync_Paper.getInstance().getMySqlManager() : new MySqlManager();
        if (mySqlManager.isUserVerified(p)) return false;
        String rnd = generateRandom(6);
        plugin.activeCodes.put(p.getUniqueId(), rnd);
        p.sendMessage("Nutze /verify " + rnd + " in Discord um deinen Account zu verifizieren.");
        return true;
    }

        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static final SecureRandom random = new SecureRandom();

        private static String generateRandom(int length) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            return sb.toString();
        }
}