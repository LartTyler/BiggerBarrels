package io.github.larttyler.biggerbarrels.barrels;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BarrelData {
	private final int tier;
	private final int maxAmount;
	private Material type;
	private int amount;

	public BarrelData(int tier) {
		this(tier, null, 0);
	}

	public BarrelData(int tier, Material type, int amount) {
		this.tier = tier;
		this.type = type;
		this.amount = amount;

		// Vanilla barrels store 27 stacks, and each tier increases this size by an extra barrel's worth.
		this.maxAmount = 27 * 64 * tier;
	}

	public int getTier() {
		return tier;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isEmpty() {
		return getAmount() == 0;
	}

	public boolean isFull() {
		return getAmount() == getMaxAmount();
	}

	public static BarrelData fromItemStack(ItemStack item) {
		PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();

		if (!container.has(BarrelItemStack.TIER_KEY, PersistentDataType.INTEGER))
			return null;

		BarrelData data = new BarrelData(container.get(BarrelItemStack.TIER_KEY, PersistentDataType.INTEGER));

		if (container.has(BarrelItemStack.CONTENT_TYPE_KEY, PersistentDataType.STRING)) {
			Validate.isTrue(
				container.has(BarrelItemStack.CONTENT_AMOUNT_KEY, PersistentDataType.INTEGER),
				"Item is tagged with a barrel content type, but has no amount"
			);

			Material type;

			try {
				type = Material.valueOf(container.get(BarrelItemStack.CONTENT_TYPE_KEY, PersistentDataType.STRING));
			} catch (IllegalArgumentException exception) {
				throw new IllegalArgumentException("Barrel ContentType is not a valid material", exception);
			}

			data.setType(type);
			data.setAmount(container.get(BarrelItemStack.CONTENT_AMOUNT_KEY, PersistentDataType.INTEGER));
		}

		return data;
	}
}
