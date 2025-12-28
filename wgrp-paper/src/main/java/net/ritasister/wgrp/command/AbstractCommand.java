package net.ritasister.wgrp.command;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.provider.MessageProvider;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.files.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    private final MessageProvider<?, Messages> configLoader;

    public AbstractCommand(String command, @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        final PluginCommand pluginCommand = wgrpPlugin.getWgrpPaperBase().getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        this.configLoader = wgrpPlugin.getMessageProvider();
    }

    public List<String> complete() {
        final ArrayList<String> listOfSubCommands = new ArrayList<>();
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                final SubCommand sub = m.getAnnotation(SubCommand.class);
                listOfSubCommands.add(sub.name());
                listOfSubCommands.addAll(Arrays.asList(sub.aliases()));
            }
        }
        listOfSubCommands.add("help");
        return listOfSubCommands;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (args.length == 0) {
            configLoader.getMessages().get("messages.usage.wgrpUseHelp").send(sender);
        } else {
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (m.isAnnotationPresent(SubCommand.class)) {
                    final SubCommand sub = m.getAnnotation(SubCommand.class);

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
                    if (args.length > 1) {
                        subArgs = Arrays.copyOfRange(args, 1, args.length);
                    }

                    if (isMustBeProcessed) {
                        if (!sub.permission().equals(UtilPermissions.NULL_PERM)) {
                            if(sender instanceof ConsoleCommandSender) {
                                run(sender, m, sub, subArgs);
                                break;
                            }
                            if (sender.hasPermission(sub.permission().getPermissionName())) {
                                run(sender, m, sub, subArgs);
                                break;
                            } else {
                                configLoader.getMessages().get("messages.ServerMsg.noPerm").send(sender);
                            }
                        } else {
                            run(sender, m, sub, subArgs);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void run(@NotNull CommandSender sender, @NotNull Method m, SubCommand sub, String[] subArgs) {
        try {
            if (m.getParameterCount() == 2) {
                m.invoke(this, sender, subArgs);
            } else if (m.getParameterCount() == 1) {
                m.invoke(this, sender);
            } else {
                throw new IllegalStateException("Incompatible number of parameters for method " + m.getName());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Command invocation error: " + sub.name(), e);
        }
    }

    private @NotNull List<String> filter(List<String> list, String @NotNull [] args) {
        final String last = args[args.length - 1];
        if (args.length > 1) {
            final String subCmdStr = args[0];
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (m.isAnnotationPresent(SubCommand.class)) {
                    final SubCommand subCommand = m.getAnnotation(SubCommand.class);
                    if (subCommand.name().equalsIgnoreCase(subCmdStr) || Arrays.asList(subCommand.aliases()).contains(subCmdStr)) {
                        try {
                            return List.of(subCommand.tabArgs()[args.length - 2]);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            return Collections.emptyList();
                        }
                    }
                }
            }
            return Collections.emptyList();
        }

        final List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase())) {
                result.add(arg);
            }
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        return filter(complete(), args);
    }

}
