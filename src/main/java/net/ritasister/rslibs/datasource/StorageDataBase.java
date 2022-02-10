package net.ritasister.rslibs.datasource;

import java.util.UUID;

public class StorageDataBase {

	private final int id;
	private String nickname;
	private UUID uniqueId;

	public StorageDataBase(final int id, 
			final String nickname, 
			final UUID uniqueId) {
		this.id=id;
		this.nickname=nickname;
		this.uniqueId=uniqueId;
	}
	public int getId() 
	{
		return this.id;
	}

	//Получение ника игрока.
	public String getNickName()
	{
		return this.nickname;
	}

	public UUID getUniqueID()
	{
		return this.uniqueId;
	}

	public UUID getUniqueIDFromName(String nickname)
	{
		return this.uniqueId;
	}
}