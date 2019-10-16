package io.github.larttyler.biggerbarrels.barrels;

import org.bukkit.Material;

public class BarrelDataSnapshot {
	private final int tier;
	private final Material type;
	private final int amount;
	private final int maxAmount;

	public BarrelDataSnapshot(BarrelData data) {
		tier = data.getTier();
		type = data.getType();
		amount = data.getAmount();
		maxAmount = data.getMaxAmount();
	}

	public int getTier() {
		return tier;
	}

	public Material getType() {
		return type;
	}

	public int getAmount() {
		return amount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public boolean isEmpty() {
		return getAmount() == 0;
	}

	public boolean isFull() {
		return getAmount() == getMaxAmount();
	}
}
