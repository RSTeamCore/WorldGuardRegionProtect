package net.ritasister.wgrp.command;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    private final WorldGuardRegionProtect wgRegionProtect;

    public AbstractCommand(String command, @NotNull WorldGuardRegionProtect wgRegionProtect) {
        PluginCommand pluginCommand = wgRegionProtect.getWGRPBukkitPlugin().getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        this.wgRegionProtect=wgRegionProtect;
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
        if (args.length == 0) {
            wgRegionProtect.getWgrpContainer().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
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
                if (args.length > 1) subArgs = Arrays.copyOfRange(args, 1, args.length);
                if (isMustBeProcessed) {
                    if (!sub.permission().equals(UtilPermissions.NULL_PERM)) {
                        if (sender.hasPermission(sub.permission().getPermissionName()))  {
                            try {
                                m.invoke(this, sender, subArgs);
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        } else wgRegionProtect.getWgrpContainer().getMessages().get("messages.ServerMsg.noPerm").send(sender);
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
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        return filter(complete(), args);
    }

    private @NotNull List<String> filter(List<String> list, String @NotNull [] args) {
        String last = args[args.length - 1];
        if(args.length - 1 != 0) {
            String subCmdStr = args[0];
            for(Method m : this.getClass().getDeclaredMethods()) {
                if(m.isAnnotationPresent(SubCommand.class)) {
                    SubCommand subCommand = m.getAnnotation(SubCommand.class);
                    if(subCommand.name().equalsIgnoreCase(subCmdStr)
                            || Arrays.stream(subCommand.aliases()).toList().contains(subCmdStr)) {
                        try{
                            return List.of(subCommand.tabArgs()[args.length - 2]);
                        }catch (ArrayIndexOutOfBoundsException ex) {
                            return Collections.emptyList();
                        }
                    }
                }
            }
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for(String arg : list) {
            if(arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }
}
