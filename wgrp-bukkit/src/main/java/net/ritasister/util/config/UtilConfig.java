package net.ritasister.util.config;

import net.ritasister.rslibs.utils.annotatedyaml.Annotations;
import net.ritasister.rslibs.utils.annotatedyaml.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UtilConfig extends Configuration {

	@Annotations.Final
	@Annotations.Comment("Version of config, do not touch!")
	@Annotations.Key("config-version")
	private final String configVersion = "1.0";

	public String getConfigVersion() {
		return configVersion;
	}

	@Annotations.Comment({"""
			#-------------------------------------------------------------
			# List of protected regions.
			# If regions are not needed, specify an empty parameter:
			# region_protect: []
			#-------------------------------------------------------------
			# Список защищенных регионов.
			# Если регионы не нужны, укажите пустой параметр:
			# region_protect: []
			#-------------------------------------------------------------"""})
	@Annotations.Key("region-protect")
	private final List<String> regionProtect = Arrays.asList("spawn", "pvp");

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# List of protected regions where breaking is allowed with the 'build allow'flag.
			# If regions are not needed, specify an empty parameter:
			# region_protect_allow: []
			#--------------------------------------------------------------------------------------
			# Список защищенных регионов в которых разрешено ломать с флагом 'build allow'.
			# Если регионы не нужны, укажите пустой параметр:
			# region_protect_allow: []
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("region-protect-allow")
	private final List<String> regionProtectAllow = new ArrayList<>(0);

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# List of protected regions where only breaking is allowed with the 'build allow'flag.
			# If regions are not needed, specify an empty parameter:
			# region_protect_only_break_allow: []
			#-------------------------------------------------------------
			# Список защищенных регионов в которых разрешено только ломать с флагом 'build allow'.
			# Если регионы не нужны, укажите пустой параметр:
			# region_protect_only_break_allow: []
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("region-protect-only-break-allow")
	private final List<String> regionProtectOnlyBreakAllow = new ArrayList<>(0);

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# List of protected regions where only breaking is allowed with the 'build allow'flag.
			# If regions are not needed, specify an empty parameter:
			# spawn_entity_type: []
			#-------------------------------------------------------------
			# Список защищенных регионов в которых разрешено только ломать с флагом 'build allow'.
			# Если регионы не нужны, укажите пустой параметр:
			# spawn_entity_type: []
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("spawn_entity_type")
	private final List<String> spawnEntityType = new ArrayList<>(0);


	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# List of prohibited entity / block for interaction in the protected region.
			# If you don't want to block it, specify an empty parameter:
			#
			# Attention! In interact_type, the block IDs\\items are different from Minecraft!
			# list of blocks prohibited by default\\items:
			# - explosive_minecart
			# - command_minecart
			# - hopper_minecart
			# - storage_minecart
			# - powered_minecart
			# - boat_spruce
			# - boat_birch
			# - boat_jungle
			# - boat_acacia
			# - boat_dark_oak
			#
			# interact_type: []
			#--------------------------------------------------------------------------------------
			# Список запрещенных entity/block для взаимодействия в защищенном регионе.
			# Если не нужно блокировать, то укажите пустой параметр:
			# interact_type: []
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("interact-type")
	private final List<String> interactType = Arrays.asList("candle", "armor_stand", "end_crystal", "minecart",
			"tnt_minecart", "command_block_minecart", "hopper_minecart", "chest_minecart",
			"furnace_minecart", "spruce_boat", "birch_boat", "jungle_boat", "acacia_boat",
			"dark_oak_boat", "bucket", "water_bucket", "lava_bucket");

	@Annotations.Key("command_we")
	private final List<String> cmdWe = Arrays.asList("//set", "//replace", "//overlay", "//walls",
			"//deform", "//fill", "//fillr", "//fixlava",
			"//hollow", "//move", "//stack", "//smooth", "//cut",
			"//replacenear");

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# List of forbidden commands from WE / FAWE.
			# Список запрещенных команд от WE / FAWE.
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("no-protect-cmd-command_c")
	private final List<String> cmdWeC = Arrays.asList("//cyl", "//hcyl", "//drain", "//rep");

	@Annotations.Key("no-protect-cmd-command_p")
	private final List<String> cmdWeP = Arrays.asList("//pyramid", "//hpyramid");

	@Annotations.Key("no-protect-cmd-command_s")
	private final List<String> cmdWeS = Arrays.asList("//sphere", "//hsphere");

	@Annotations.Key("no-protect-cmd-command_u")
	private final List<String> cmdWeU = Arrays.asList("//up", "/up");

	@Annotations.Key("no-protect-cmd-command_cp")
	private final List<String> cmdWeCP = Arrays.asList("//paste", "//place", "//replacenear", "//hollow");

	@Annotations.Key("no-protect-cmd-command_b")
	private final List<String> cmdWeB = Arrays.asList("//set", "//replace", "//overlay", "//walls",
			"//deform", "//fill", "//fillr", "//fixlava",
			"//hollow", "//move", "//stack", "//smooth", "//cut",
			"//replacenear");


	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# Turn Enable/Disable the protected region message.
			# Включить/Выключить сообщение о защищенном регионе.
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("region-protect-message")
	private final boolean regionMessageProtect = true;

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# Turn Enable/Disable the protected region message when using the WE\\FAWE commands.
			# Включить/Выключить сообщение о защищенном регионе при использовании команд WE\\FAWE.
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("protect-we-message")
	private final boolean regionMessageProtectWe = true;

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# Enable notify to console and admin.
			# Включение оповещения в консоль и администратору.
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("spy-command-notify-console")
	private final boolean spyCommandNotifyConsole = true;

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# Enable notify to console and admin.
			# Включение оповещения в консоль и администратору.
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("spy-command-notify-admin")
	private final boolean spyCommandNotifyAdmin = true;

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# Sound notify to admin.
			# Звуковое оповещение администратора.
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("spy-command-notify-sound")
	private final boolean spyCommandNotifyAdminPlaySoundEnable = true;

	@Annotations.Comment("""
			#--------------------------------------------------------------------------------------
			# All sounds can be found here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html
			# Все звуки из 1.17.1 могут быть найдены тут: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html
			#--------------------------------------------------------------------------------------""")
	@Annotations.Key("spy-command-notify-sound-type")
	private final String spyCommandNotifyAdminPlaySound = "BLOCK_ANVIL_PLACE";

	@Annotations.Key("spy-command-command-list")
	private final List<String> spyCommand = Arrays.asList("//set", "//replace", "//overlay", "//walls",
			"//deform", "//fill", "//fillr", "//fixlava",
			"//hollow", "//move", "//stack", "//smooth", "//cut",
			"//replacenear");

	//DataBase start
	@Annotations.Key("dataSource-enable")
	private final boolean databaseEnable = false;

	@Annotations.Key("dataSource-database")
	private final String database = "database";

	@Annotations.Key("dataSource-jdbcDriver")
	private final String jdbcDriver = "jdbc:mariadb://";

	@Annotations.Key("dataSource-host")
	private final String host = "127.0.0.1";

	@Annotations.Key("dataSource-port")
	private final String port = "3306";

	@Annotations.Key("dataSource-user")
	private final String user = "root";

	@Annotations.Key("dataSource-password")
	private final String password = "root";

	@Annotations.Key("dataSource-tables-tableName")
	private final String tables = "tableName";

	@Annotations.Key("dataSource-useSsl")
	private final boolean useSsl = true;

	//Pool settings
	@Annotations.Key("dataSource-poolSettings-maxPoolSize")
	private final int maxPoolSize = 10;

	@Annotations.Key("dataSource-poolSettings-maxLifetime")
	private final int maxLifetime = 1800;

	@Annotations.Key("dataSource-poolSettings-connectionTimeout")
	private final int connectionTimeout = 5000;

	@Annotations.Key("dataSource-interval-asyncReload")
	private final int intervalReload = 60;
	//DataBase end

	public List<String> regionProtect() {
		return  regionProtect;
	}

	public List<String> regionProtectAllow() {
		return regionProtectAllow;
	}

	public List<String> regionProtectOnlyBreakAllow() {
		return regionProtectOnlyBreakAllow;
	}

	public List<String> spawnEntityType() {
		return spawnEntityType;
	}

	public List<String> interactType() {
		return interactType;
	}

	public List<String> spyCommand() {
		return spyCommand;
	}

	public List<String> cmdWe() {
		return cmdWe;
	}

	public List<String> cmdWeC() {
		return cmdWeC;
	}

	public List<String> cmdWeP() {
		return cmdWeP;
	}

	public List<String> cmdWeS() {
		return cmdWeS;
	}

	public List<String> cmdWeU() {
		return cmdWeU;
	}

	public List<String> cmdWeCP() {
		return cmdWeCP;
	}

	public List<String> cmdWeB() {
		return cmdWeB;
	}
	public boolean regionMessageProtect() {
		return regionMessageProtect;
	}

	public boolean regionMessageProtectWe() {
		return regionMessageProtectWe;
	}

	public boolean spyCommandNotifyConsole() {
		return spyCommandNotifyConsole;
	}

	public boolean spyCommandNotifyAdmin() {
		return spyCommandNotifyAdmin;
	}

	public String spyCommandNotifyAdminPlaySound() {
		return spyCommandNotifyAdminPlaySound;
	}

	public boolean spyCommandNotifyAdminPlaySoundEnable() {
		return spyCommandNotifyAdminPlaySoundEnable;
	}

	//DataBase start
	public String database() {
		return database;
	}

	public boolean databaseEnable() {
		return databaseEnable;
	}

	public String jdbcDriver() {
		return jdbcDriver;
	}

	public String host() {
		return host;
	}

	public String port() {
		return port;
	}

	public String user() {
		return user;
	}

	public String password() {
		return password;
	}

	public String tables() {
		return tables;
	}

	public boolean useSsl() {
		return useSsl;
	}

	public int maxPoolSize() {
		return maxPoolSize;
	}

	public int maxLifetime() {
		return maxLifetime;
	}

	public int connectionTimeout() {
		return connectionTimeout;
	}

	public int intervalReload() {
		return intervalReload;
	}
	//DataBase end

	@Override
	public String toString() {
		return "Config{" +
				"configVersion='" + configVersion + '\'' +
				"regionProtect='" + regionProtect + '\'' +
				"regionProtectAllow='" + regionProtectAllow + '\'' +
				"regionProtectOnlyBreakAllow='" + regionProtectOnlyBreakAllow + '\'' +
				"spawnEntityType='" + spawnEntityType + '\'' +
				"interactType='" + interactType + '\'' +
				"cmdWe='" + cmdWe + '\'' +
				"cmdWeC='" + cmdWeC + '\'' +
				"cmdWeP='" + cmdWeP + '\'' +
				"cmdWeS='" + cmdWeS + '\'' +
				"cmdWeU='" + cmdWeU + '\'' +
				"cmdWeCP='" + cmdWeCP + '\'' +
				"cmdWeB='" + cmdWeB + '\'' +
				"spyCommand='" + spyCommand + '\'' +
				"regionMessageProtect='" + regionMessageProtect + '\'' +
				"regionMessageProtectWe='" + regionMessageProtectWe + '\'' +
				"spyCommandNotifyConsole='" + spyCommandNotifyConsole + '\'' +
				"spyCommandNotifyAdmin='" + spyCommandNotifyAdmin + '\'' +
				"spyCommandNotifyAdminPlaySoundEnable='" + spyCommandNotifyAdminPlaySoundEnable + '\'' +
				"spyCommandNotifyAdminPlaySound='" + spyCommandNotifyAdminPlaySound + '\'' +
				//DataBase start
				"database='" + database + '\'' +
				"databaseEnable='" + databaseEnable + '\'' +
				"jdbcDriver='" + jdbcDriver + '\'' +
				"host='" + host + '\'' +
				"port='" + port + '\'' +
				"user='" + user + '\'' +
				"password='" + password + '\'' +
				"tables='" + tables + '\'' +
				"useSsl='" + useSsl + '\'' +
				"maxPoolSize='" + maxPoolSize + '\'' +
				"maxLifetime='" + maxLifetime + '\'' +
				"connectionTimeout='" + connectionTimeout + '\'' +
				"intervalReload='" + intervalReload + '\'' +
				//DataBase end
				'}';
	}
}