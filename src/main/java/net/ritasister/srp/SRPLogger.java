package net.ritasister.srp;

import java.io.*;
import java.util.logging.*;

import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;

public class SRPLogger 
{
    private static final ConsoleCommandSender log = ServerRegionProtect.instance.getServer().getConsoleSender();

    public static final void info(final String msg) 
    {
        SRPLogger.log.sendMessage(m("&2"+msg).replace("&", "�"));
    }
    public static final void warn(final String msg) 
    {
        SRPLogger.log.sendMessage(m("&e"+msg).replace("&", "�"));
    }
    public static final void err(final String msg) 
    {
        SRPLogger.log.sendMessage(m("&c"+msg).replace("&", "�"));
    }
    private static final String m(final String msg) 
    {
        return "&8[&cServerRegionProtect&8] ".replaceAll("&", "�") + msg.replaceAll("&", "�");
    }
	public static final void LoadConfigMsgSuccess(final File f)
    {
        SRPLogger.info("Config: &6<config> &2loaded success!".replace("<config>", String.valueOf(f)));
    }
	public static final void LoadConfigMsgError(final String s)
    {
        SRPLogger.err("Config: &6<msg_error> &ccould not load!".replace("<msg_error>", String.valueOf(s)));
    }
}