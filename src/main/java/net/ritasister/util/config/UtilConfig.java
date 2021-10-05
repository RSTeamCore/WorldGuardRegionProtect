package net.ritasister.util.config;

import java.io.*;
import java.util.*;

import org.bukkit.World;
import org.bukkit.configuration.file.*;

import net.ritasister.srp.ServerRegionProtect;

public final class UtilConfig
{
	private FileConfiguration config;

	public List<String> regionProtect;
	public List<String> regionProtectAllow;
	public List<String> regionProtectOnlyBreakAllow;
	public List<String> spawnEntityType;
	public List<String> interactType;
	public List<String> CmdsWE;
	public List<String> cmdweC;
	public List<String> cmdweP;
	public List<String> cmdweS;
	public List<String> cmdweU;
	public List<String> cmdweCP;
	public List<String> cmdweB;
	public boolean regionMessageProtect;
	public boolean regionMessageProtectWe;
	
	public UtilConfig() 
	{
		ServerRegionProtect.instance.saveDefaultConfig();
		ServerRegionProtect.instance.reloadConfig();
		this.config = ServerRegionProtect.instance.getConfig();

		this.regionProtect=(List<String>)this.config.getList("server_region_protect.region_protect",(List)new ArrayList());
		this.regionProtectAllow=(List<String>)this.config.getList("server_region_protect.region_protect_allow",(List)new ArrayList());
		this.regionProtectOnlyBreakAllow=(List<String>)this.config.getList("server_region_protect.region_protect_only_break_allow",(List)new ArrayList());
		this.spawnEntityType=(List<String>)this.config.getList("server_region_protect.spawn_entity_type",(List)new ArrayList());
		this.interactType=(List<String>)this.config.getList("server_region_protect.interact_type",(List)new ArrayList());
		this.regionMessageProtect=this.config.getBoolean("server_region_protect.protect_message");
		this.regionMessageProtectWe=this.config.getBoolean("server_region_protect.protect_we_message");
		this.CmdsWE=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_we",(List)new ArrayList());
		this.cmdweC=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_c",(List)new ArrayList());
		this.cmdweP=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_p",(List)new ArrayList());
		this.cmdweS=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_s",(List)new ArrayList());
		this.cmdweU=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_u",(List)new ArrayList());
		this.cmdweCP=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_cp",(List)new ArrayList());
		this.cmdweB=(List<String>)this.config.getList("server_region_protect.no_protect_cmd.command_b",(List)new ArrayList());
	}
}