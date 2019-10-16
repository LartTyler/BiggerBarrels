package io.github.larttyler.biggerbarrels.barrels;

import org.bukkit.Material;

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
}
