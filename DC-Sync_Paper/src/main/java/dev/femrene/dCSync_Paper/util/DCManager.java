package dev.femrene.dCSync_Paper.util;

import dev.femrene.dCSync_Paper.DCSync_Paper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DCManager extends ListenerAdapter {

    private static DCManager instance;
    private static LPManager lpManager;
    private static DCSync_Paper plugin;
    private static FileConfiguration config;
    private static JDA jda;
    private static MySqlManager mySqlManager;

    public DCManager() {
        lpManager = new LPManager();
        plugin = DCSync_Paper.getInstance();
        config = plugin.getConfig();
    }

    public static void startDCBot() {
        plugin = DCSync_Paper.getInstance();
        config = plugin.getConfig();
        String token = config.getString("discord.BotToken");

        jda = JDABuilder.createDefault(token)
                .addEventListeners(new DCManager()) // Your command listener
                .addEventListeners(new ListenerAdapter() {
                    @Override
                    public void onReady(ReadyEvent event) {
                        Guild guild = event.getJDA().getGuildById(config.getString("discord.serverID"));
                        if (guild == null) {
                            System.out.println("[BOT] ❌ Guild not found. Is the ID correct? Is the bot in the server?");
                            return;
                        }

                        System.out.println("[BOT] ✅ Connected to guild: " + guild.getName());

                        guild.updateCommands().addCommands(
                                Commands.slash("verify", "Verifiziert deinen Account")
                                        .addOption(OptionType.STRING, "code", "Dein Verifizierungscode", true)
                        ).queue();
                    }
                })
                .build(); // You don't need to call `awaitReady()` if you're using an event
    }

    public static void stopDCBot() {
        jda.shutdown();
        jda = null;
    }

    public static DCManager getInstance() {
        return instance;
    }

    public static JDA getJda() {
        return jda;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("verify") && event.getOption("code") != null) {
            plugin = DCSync_Paper.getInstance();
            config = plugin.getConfig();
            mySqlManager = plugin.getMySqlManager() != null ? plugin.getMySqlManager() : new MySqlManager();
            HashMap<UUID, String> codes = plugin.activeCodes;
            event.deferReply().queue();
            String code = event.getOption("code").getAsString();
            if (codes.containsValue(code)) {
                for (Map.Entry<UUID, String> entry : codes.entrySet()) {
                    if (entry.getValue().equals(code)) {
                        String playerUUID = String.valueOf(entry.getKey());
                        mySqlManager.VerieyedUser(playerUUID, event.getMember().getId());
                        plugin.activeCodes.remove(UUID.fromString(playerUUID));
                        Player bplayer = plugin.getServer().getPlayer(UUID.fromString(playerUUID));
                        String grp = lpManager.getPlayersGroup(bplayer);
                        codes.remove(UUID.fromString(playerUUID));
                        String dcGroupIDFromGroup = lpManager.getDcGroupIDFromGroup(grp);
                        String dcPrefixFromGroup = lpManager.getDcPrefixFromGroup(grp);
                        Guild guild = jda.getGuildById(config.getString("discord.serverID"));
                        assert guild != null;
                        System.out.println(guild.getRoleById(dcGroupIDFromGroup).getName());
                        guild.addRoleToMember(event.getMember(), guild.getRoleById(dcGroupIDFromGroup)).queue();
                        String grpP;
                        if(dcPrefixFromGroup == null)
                            grpP = "";
                        else
                            grpP = dcPrefixFromGroup;
                        event.getMember().modifyNickname(grpP + bplayer.getName()).queue();
                        event.getHook().sendMessage("Dein Account wurde erfolgreich verifiziert.").setEphemeral(true).queue();
                    }
                }
            } else {
                event.getHook().sendMessage("Der Code wurde nicht gefunden.").setEphemeral(true).queue();
            }
        }
        super.onSlashCommandInteraction(event);
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        super.onGuildMemberRoleAdd(event);
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        super.onGuildMemberRoleRemove(event);
    }
}