package io.github.larttyler.biggerbarrels.barrels;

public enum BarrelUpdateResult {
	SUCCESS,
	FAILED_BARREL_EMPTY,
	FAILED_BARREL_FULL,
	FAILED_ITEM_NOT_ALLOWED,
	FAILED_NOT_A_BARREL,
	FAILED_PLAYER_INVENTORY_FULL,
	FAILED_WRONG_TYPE;

	public boolean isFailure() {
		return name().startsWith("FAILED_");
	}
}
