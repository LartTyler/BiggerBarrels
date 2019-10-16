package io.github.larttyler.biggerbarrels.barrels;

import com.sun.istack.internal.Nullable;
import io.github.larttyler.biggerbarrels.BiggerBarrelsPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.Collections;

public class BarrelItemStack extends ItemStack {
	public static final NamespacedKey TIER_KEY;
	public static final NamespacedKey CONTENT_TYPE_KEY;
	public static final NamespacedKey CONTENT_AMOUNT_KEY;

	static {
		TIER_KEY = new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.tier");
		CONTENT_TYPE_KEY = new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.content-type");
		CONTENT_AMOUNT_KEY = new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.content-amount");
	}

	public BarrelItemStack(int tier) {
		this(tier, null, 0);
	}

	public BarrelItemStack(int tier, @Nullable Material contentType, int amount) {
		super(Material.BARREL);

		ItemMeta meta = getItemMeta();
		assert meta != null : "Newly created item stack had null meta";

		meta.setDisplayName("Tier " + tier + " Barrel");
		meta.setLore(Collections.singletonList("Can hold " + (27 * (tier + 1)) + " stacks of an item."));

		PersistentDataContainer container = meta.getPersistentDataContainer();

		container.set(TIER_KEY, PersistentDataType.INTEGER, tier);

		if (contentType != null && amount > 0) {
			container.set(CONTENT_TYPE_KEY, PersistentDataType.STRING, contentType.name());
			container.set(CONTENT_AMOUNT_KEY, PersistentDataType.INTEGER, amount);
		}

		setItemMeta(meta);
	}
}
