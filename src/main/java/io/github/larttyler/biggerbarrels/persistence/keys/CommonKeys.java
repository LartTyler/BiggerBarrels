package io.github.larttyler.biggerbarrels.persistence.keys;

import io.github.larttyler.biggerbarrels.BiggerBarrelsPlugin;
import org.bukkit.NamespacedKey;

public class CommonKeys {
	public static final NamespacedKey Tier;
	public static final NamespacedKey ContentType;
	public static final NamespacedKey ContentAmount;

	static {
		Tier = new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.tier");
		ContentType = new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.content-type");
		ContentAmount = new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.content-amount");
	}
}
