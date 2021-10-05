package net.ritasister.register;

import java.util.HashMap;

import net.ritasister.command.CommandReload;
import net.ritasister.srp.SRPLogger;
import net.ritasister.srp.ServerRegionProtect;
import net.ritasister.util.UtilCommandList;

public class SRPRegisterCommand 
{
	public static void RegisterCommands(UtilCommandList ucl)
	{
		try 
		{
			ServerRegionProtect.instance.getCommand(ucl.serverregionprotect).setExecutor(new CommandReload(ucl));
			SRPLogger.info("Все команды плагина были успешно зарегистрированы.");
		}catch(NullPointerException e){
			SRPLogger.err("Команда не может быть &4null."); 
			SRPLogger.err("Взможны несколько причин ошибки:");
			SRPLogger.err("- команда не указана в &4getCommand(ucl.cmd_name).");
			SRPLogger.err("- команда не указана в &4UtilCommandList.");
			SRPLogger.err("- команда не указана в &4plugin.yml.");
		}
	}
}