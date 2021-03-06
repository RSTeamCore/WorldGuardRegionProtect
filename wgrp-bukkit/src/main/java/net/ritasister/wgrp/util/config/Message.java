package net.ritasister.wgrp.util.config;

import com.google.common.collect.Lists;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Message {
    ServerMsg_wgrpMsg, ServerMsg_wgrpMsgWe, ServerMsg_wgrpUseHelp, ServerMsg_noPerm,
    Configs_configReloaded,

    Notify_sendAdminInfoIfUsedCommandInRG, Notify_sendAdminInfoIfActionInRegion,

    subCommands_reload, subCommands_about, subCommands_spy, subCommands_addRegion, subCommands_removeRegion,

    subCommands_help,

    usage_format, usage_title, usage_wgrpUseHelp, usage_addRegionFromConsole, usage_removeRegionFromConsole,

    regionManagement_add, regionManagement_remove, regionManagement_notContains, regionManagement_invalidWorld,

    regionManagement_invalidRegion
    ;

    private List<String> msg;

    private static LangProperties langProperties;

    private static boolean PAPI;

    public static void load(WGRPBukkitPlugin wgrpBukkitPlugin, String lang, boolean PAPIEnabled) {
        FileConfiguration c = initLang(wgrpBukkitPlugin, lang);
        try {
            String language = c.getString("langTitle.language");
            String author = c.getString("langTitle.author");
            String version = c.getString("langTitle.version");
            langProperties = new LangProperties(language, author, version);
            if(langProperties.language() == null) lang = "en";
            wgrpBukkitPlugin.getLogger().info("Loaded language: " + lang);
            wgrpBukkitPlugin.getLogger().info("Author of language: " + author);
            wgrpBukkitPlugin.getLogger().info("language version: " + version);
        } catch (Throwable e) {
            lang = "en";
        }
        PAPI = PAPIEnabled;
        if(updateValues(c)) recover(wgrpBukkitPlugin, lang);
    }
    public static @NotNull FileConfiguration initLang(@NotNull WGRPBukkitPlugin plugin, String lang) {
        File folder = new File(
                plugin.getDataFolder() + File.separator + "lang" + File.separator);
        if(!folder.exists()) {
            try {
                folder.mkdirs();
                folder.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        File langFile = new File(
                plugin.getDataFolder() + File.separator + "lang" + File.separator + lang + ".yml");
        if(!langFile.exists()) {
            try {
                langFile.createNewFile();
                InputStream ddlStream = WGRPBukkitPlugin.class.getClassLoader().getResourceAsStream(lang + ".yml");
                try (FileOutputStream fos = new FileOutputStream(langFile)) {
                    byte[] buf = new byte[2048];
                    int r;
                    while(-1 != (r = ddlStream.read(buf))) {
                        fos.write(buf, 0, r);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return YamlConfiguration.loadConfiguration(langFile);
    }
    public static void recover(@NotNull WGRPBukkitPlugin plugin, String lang) {
        File langFile = new File(
                plugin.getDataFolder() + File.separator + "lang" + File.separator + lang + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(langFile);
        for(Message message : Message.values()) {
            boolean recover = false;
            String path = "messages." + message.name().replace("_", ".");
            try {
                Object obj = c.get(path);
                if(obj == null) recover = true;
            } catch (Throwable e) {
                recover = true;
            }
            if(recover) {
                Object value;
                File tempFile = new File("cache_file");
                try {
                    tempFile.createNewFile();
                } catch (Throwable e) {
                    e.printStackTrace();
                } try {
                    InputStream ddlStream = WGRPBukkitPlugin.class.getClassLoader().getResourceAsStream(lang + ".yml");
                    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                        byte[] buf = new byte[2048];
                        int r;
                        if (ddlStream != null) {
                            while(-1 != (r = ddlStream.read(buf))) {
                                fos.write(buf, 0, r);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FileConfiguration defaultMsgFile = YamlConfiguration.loadConfiguration(tempFile);
                    value = defaultMsgFile.get(path);
                } catch (Throwable e) {
                    value = 0;
                    e.printStackTrace();
                } finally {
                    tempFile.delete();
                }
                c.set(path, value);
            }

        } try {
            c.save(langFile);
            updateValues(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean updateValues(FileConfiguration c) {
        boolean result = false;
            for(Message message : Message.values()) {
                try {
                    Object obj = c.get("messages." + message.name().replace("_", "."));
                    if (obj instanceof List) {
                        message.msg = (((List<String>) obj)).stream().map(m -> ChatColor.translateAlternateColorCodes('&', m)).collect(Collectors.toList());
                    } else {
                        message.msg = Lists.newArrayList(obj == null ? "" : ChatColor.translateAlternateColorCodes('&', obj.toString()));
                    }
                    result = message.msg == null || message.msg.equals("") || obj.equals(null);

                } catch (NullPointerException e) {
                    result = true;
                }
            }
        return result;
    }

    public Sender replace(String from, String to) {
        Sender sender = new Sender();
        return sender.replace(from, to);
    }
    public void send(CommandSender player) {
        new Sender().send(player);
    }

    @Override
    public @NotNull String toString() {
        StringBuilder str = new StringBuilder();
        for(String s : Message.this.msg) {
            str.append(" ").append(s);
        }
        return ChatColor.translateAlternateColorCodes('&', str.toString());
    }

    public @NotNull List<String> toList() {
        ArrayList<String> result = new ArrayList<>();
        for(String m : msg) {
            result.add(ChatColor.translateAlternateColorCodes('&',m));
        }
        return result;
    }
    public class Sender {
        private final Map<String, String> placeholders = new HashMap<>();
        public void send(CommandSender player) {
            if (PAPI && player instanceof Player) {
                for (String message : Message.this.msg) {
                    Player p = ((Player) player).getPlayer();
                    sendMessage(player, PlaceholderAPI.setPlaceholders(p, replacePlaceholders(message)));
                }
            } else {
                for (String message : Message.this.msg) {
                    sendMessage(player, replacePlaceholders(message));
                }
            }
        }
        public Sender replace(String from, String to) {
            placeholders.put(from, to);
            return this;
        }
        private void sendMessage(CommandSender player, @NotNull String message) {
            if(message.startsWith("json:")) {
                final TextComponent textComponent = Component.text(message.substring(5));
                player.sendMessage(textComponent.toString());
            } else {
                player.sendMessage(message);
            }
        }
        private String replacePlaceholders(String message) {
            for(Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
            return message;
        }
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            for(String s : Message.this.msg) {
                str.append(" ").append(replacePlaceholders(s));
            }
            return ChatColor.translateAlternateColorCodes('&', str.toString());
        }
        public List<String> toList() {
            ArrayList<String> list = new ArrayList<>();
            for(String s : Message.this.msg) {
                list.add(ChatColor.translateAlternateColorCodes('&', replacePlaceholders(s)));
            }
            return list;
        }
    }
    public static LangProperties getLangProperties() {
        return langProperties;
    }

    public record LangProperties(String language, String author, String version) { }
}