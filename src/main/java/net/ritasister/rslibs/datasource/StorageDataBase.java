package net.ritasister.rslibs.datasource;

import java.util.UUID;

public class StorageDataBase {

	public final int id;
	public final String nickname;
	public final UUID uniqueId;

	public StorageDataBase(final int id,
			final String nickname,
			final UUID uniqueId) {
		this.id=id;
		this.nickname=nickname;
		this.uniqueId=uniqueId;
	}
	public int getId() {
		return this.id;
	}

	public String getNickName() {
		return this.nickname;
	}

	public UUID getUniqueID() {
		return this.uniqueId;
	}
}