package net.ritasister.wgrp.command;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    public AbstractCommand(String command, @NotNull WorldGuardRegionProtect wgRegionProtect) {
        PluginCommand pluginCommand = wgRegionProtect.getWgrpBukkitPlugin().getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    public List<String> complete() {
        ArrayList<String> listOfSubCommands = new ArrayList<>();
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);
                listOfSubCommands.add(sub.name());
                listOfSubCommands.addAll(Arrays.asList(sub.aliases()));
            }
        }
        listOfSubCommands.add("help");
        return listOfSubCommands;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            ArrayList<String> messages = new ArrayList<>(Message.usage_title.replace("%command%", command.getName()).toList());
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (m.isAnnotationPresent(SubCommand.class)) {
                    SubCommand sub = m.getAnnotation(SubCommand.class);
                    messages.addAll(Message.usage_format.replace("%command%", command.getName()).replace("%alias%", sub.name()).
                            replace("%description%", sub.description().toString()).toList());
                }
            } for (String message : messages) {
                sender.sendMessage(message);
            }
        } else for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);

                boolean isMustBeProcessed = false;

                if (args[0].equalsIgnoreCase(sub.name())) {
                    isMustBeProcessed = true;
                } else {
                    for (String alias : sub.aliases()) {
                        if (args[0].equalsIgnoreCase(alias)) {
                            isMustBeProcessed = true;
                            break;
                        }
                    }
                }
                String[] subArgs = {};
                if (args.length > 1) subArgs = Arrays.copyOfRange(args, 1, args.length - 1);
                if (isMustBeProcessed) {
                    if (!sub.permission().equals(UtilPermissions.NULL_PERM)) {
                        if (sender.hasPermission(sub.permission().getPermissionName()))  {
                            try {
                                m.invoke(this, sender, subArgs);
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        } else Message.ServerMsg_noPerm.send(sender);
                    } else try {
                        m.invoke(this, sender, subArgs);
                        break;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return filter(complete(), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        if(list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for(String arg : list) {
            if(arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }
}