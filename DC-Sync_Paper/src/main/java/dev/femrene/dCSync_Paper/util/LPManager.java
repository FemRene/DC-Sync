package dev.femrene.dCSync_Paper.util;

import dev.femrene.dCSync_Paper.DCSync_Paper;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;


public class LPManager {

    private static LuckPerms api;
    private static DCSync_Paper plugin;

    public LPManager() {
        plugin = DCSync_Paper.getInstance();
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
    }

    public @NonNull String getPlayersGroup(Player player) {
        User user = api.getPlayerAdapter(Player.class).getUser(player);
        return user.getPrimaryGroup();
    }

    public String getDcPrefixFromGroup(String groupName) {
        String metaKey = plugin.getConfig().getString("luckperms.nameMeta");
        Group group = api.getGroupManager().getGroup(groupName);
        if (group != null) {
            for (Node node : group.getNodes()) {
                if (node.getKey().startsWith(metaKey+".") && node.getValue()) {
                    String a = metaKey+".";
                    return node.getKey().substring(a.length());
                }
            }
        }
        return null;
    }

    public String getDcGroupIDFromGroup(String groupName) {
        String metaKey = plugin.getConfig().getString("luckperms.roleMeta");
        Group group = api.getGroupManager().getGroup(groupName);
        if (group != null) {
            for (Node node : group.getNodes()) {
                if (node.getKey().startsWith(metaKey+".") && node.getValue()) {
                    String a = metaKey+".";
                    return node.getKey().substring(a.length());
                }
            }
        }
        return null;
    }

}