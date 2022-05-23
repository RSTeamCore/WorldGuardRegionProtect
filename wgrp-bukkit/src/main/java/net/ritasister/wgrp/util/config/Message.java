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
    wgrpMsg, wgrpMsgWe, wgrpUseHelp, noPerm,

    configReloaded, configNotFound, configMsgReloaded,
    configMsgNotFound, sendAdminInfoIfUsedCommandInRG, sendAdminInfoIfActionInRegion,

    pluralDay1, pluralDay2, pluralDay3,
    pluralHour1, pluralHour2, pluralHour3,
    pluralMinute1, pluralMinute2, pluralMinute3, pluralMinute4,
    pluralSecond1, pluralSecond2, pluralSecond3, pluralSecond4, pluralTimeEmpty,

    dbConnectSuccessful, dbConnectError, dbConnectFailed, dbReconnect,

    dbClosePSTError, dbCloseRSError, dbCloseDBError, dbLoadError, dbLoadAsyncError;

    private List<String> msg;

    private boolean PAPI;

    @SuppressWarnings("unchecked")
    public static void load(File file, boolean PAPIEnabled) {
        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
        for(Message message : Message.values()) {
            message.PAPI = PAPIEnabled;
            boolean needRecover = false;
            try {
                Object obj = c.get("messages." + message.name().replace("_", "."));
                if(obj instanceof List) {
                    message.msg = (((List<String>) obj)).stream().map(m -> ChatColor.translateAlternateColorCodes('&', m)).collect(Collectors.toList());
                } else {
                    message.msg = Lists.newArrayList(obj == null ? "" : ChatColor.translateAlternateColorCodes('&', obj.toString()));
                }
            } catch (NullPointerException e) {
                needRecover = true;
            }
            if(needRecover) recover(file);

        }
    }

    public static void recover(File file) {
        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
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
                try {
                    InputStream inputStream = WGRPBukkitPlugin.class.getResourceAsStream("messages.yml");
                    assert inputStream != null;
                    Reader reader = new BufferedReader(new InputStreamReader(inputStream));
                    FileConfiguration defaultMsgFile = YamlConfiguration.loadConfiguration(reader);
                    value = defaultMsgFile.get(path);
                } catch (Throwable e) {
                    value = 0;
                }
                c.set(path, value);
            }
        } try {
            c.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            if(PAPI && player instanceof Player) {
                for(String message : Message.this.msg) {
                    Player p = ((Player) player).getPlayer();
                    sendMessage(player, PlaceholderAPI.setPlaceholders(p, replacePlaceholders(message)));
                }
            } else {
                for(String message : Message.this.msg) {
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
                player.sendMessage(textComponent);
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
}