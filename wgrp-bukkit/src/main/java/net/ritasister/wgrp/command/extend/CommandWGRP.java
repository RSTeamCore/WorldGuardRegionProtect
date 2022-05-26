package net.ritasister.wgrp.command;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.UUID;

public class CommandWGRP extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandWGRP(@NotNull WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.toString(), wgRegionProtect.getWgrpBukkitPlugin());
        this.wgRegionProtect = wgRegionProtect;
    }

    @SubCommand(
            name = "reload",
            description = Message.subCommands_reload,
            permission = "wgrp.command.reload")
    public void wgrpReload(@NotNull CommandSender sender, String[] args) {
        long timeInitStart = System.currentTimeMillis();

        wgRegionProtect.getUtilConfig().getConfig().reload();
        wgRegionProtect.getUtilConfig().loadMessages(wgRegionProtect);

        long timeReload = (System.currentTimeMillis() - timeInitStart);
        Message.Configs_configReloaded.replace("<time>", String.valueOf(timeReload)).send(sender);
    }

    @SubCommand(
            name = "about",
            aliases = {"credits", "authors"},
            description = Message.subCommands_about,
            permission = "wgrp.command.wgrpbase")
    public void wgrpAbout(@NotNull CommandSender sender, String[] args) {
        sender.sendMessage(wgRegionProtect.getChatApi().getColorCode("""
									&b=======================================================================
									&aHi! If you need help from this plugin, you can contact with me on:
									&e https://www.spigotmc.org/resources/wgRegionProtect-1-12.81333/
									&b=======================================================================
									&aBut if you find any error or you want to send me any idea for this plugin&b,
									&aso you can create issues on github:&e https://github.com/RSTeamCore/WorldGuardRegionProtect
									&6your welcome!
									"""));
    }

    @SubCommand(
            name = "spy",
            description = Message.subCommands_spy,
            permission = "wgrp.command.spy.admin")
    public void wgrpSpy(@NotNull CommandSender sender, String[] args) {
        @NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
        if (wgRegionProtect.spyLog.contains(uniqueId)) {
            wgRegionProtect.spyLog.remove(uniqueId);
            sender.sendMessage("You are disable spy logging!");
        } else {
            wgRegionProtect.spyLog.add(uniqueId);
            sender.sendMessage("You are enable spy logging!");
        }
    }

    /*
    @Override //рефлексия - такое дерьмо :)
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
            ArrayList<String> messages = new ArrayList<>(Message.usage_title.replace("%command%", command.getName()).toList());
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (m.isAnnotationPresent(SubCommand.class)) {
                    SubCommand sub = m.getAnnotation(SubCommand.class);
                    messages.addAll(Message.usage_format.replace("%command%", command.getName()).replace("%alias%", sub.name()).
                            replace("%description%", sub.description().toString()).toList());
                }
            }
            for(String message : messages) {
                sender.sendMessage(message);
            }
        } else for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);

                boolean isMustBeProcessed = false;

                if(args[0].equalsIgnoreCase(sub.name())) {
                    isMustBeProcessed = true;
                } else {
                    for(String alias : sub.aliases()) {
                        if (args[0].equalsIgnoreCase(alias)) {
                            isMustBeProcessed = true;
                            break;
                        }
                    }
                }

                String[] subArgs = {};
                if(args.length > 1) subArgs = Arrays.copyOfRange(args, 1, args.length-1);


                if(isMustBeProcessed) {
                    if(!Objects.equals(sub.permission(), "")) {
                        if(sender.hasPermission(sub.permission())) {
                            try {
                                m.invoke(this, sender, subArgs);
                                break;
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        } else Message.ServerMsg_noPerm.send(sender);
                    }
                    try {
                        m.invoke(sender, (Object) subArgs);
                        break;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return true;
    }

     */
}