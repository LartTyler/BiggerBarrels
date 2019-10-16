package io.github.larttyler.biggerbarrels.barrels;

import io.github.larttyler.biggerbarrels.BiggerBarrelsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import java.util.Map;

public class BarrelManager {
	private static final Map<Block, BarrelData> tracked = new HashMap<>();

	public static BarrelDataSnapshot getSnapshot(Block block) {
		BarrelData data = tracked.get(block);

		if (data == null)
			return null;

		return new BarrelDataSnapshot(data);
	}

	public static boolean has(Block block) {
		return tracked.containsKey(block);
	}

	public static BarrelUpdateResult store(Block block, ItemStack item) {
		BarrelData data = tracked.get(block);

		if (data == null)
			return BarrelUpdateResult.FAILED_NOT_A_BARREL;

		if (!isItemAllowed(item))
			return BarrelUpdateResult.FAILED_ITEM_NOT_ALLOWED;

		if (data.isFull())
			return BarrelUpdateResult.FAILED_BARREL_FULL;

		if (data.getType() == null)
			data.setType(item.getType());
		else if (data.getType() != item.getType())
			return BarrelUpdateResult.FAILED_WRONG_TYPE;

		int amount = Math.min(item.getAmount(), data.getMaxAmount() - data.getAmount());

		data.setAmount(data.getAmount() + amount);
		item.setAmount(item.getAmount() - amount);

		return BarrelUpdateResult.SUCCESS;
	}

	public static BarrelUpdateResult retrieve(Block block, Player player, boolean stack) {
		BarrelData data = tracked.get(block);

		if (data == null)
			return BarrelUpdateResult.FAILED_NOT_A_BARREL;

		if (data.isEmpty())
			return BarrelUpdateResult.FAILED_BARREL_EMPTY;

		int amount = stack ? Math.min(data.getAmount(), 64) : 1;
		ItemStack item = new ItemStack(data.getType(), amount);

		HashMap<Integer, ItemStack> result = player.getInventory().addItem(item);

		int newAmount = data.getAmount() - amount;

		// When partial stacks are filled, the amount remaining in the item stack indicates the amount that could not
		// be added to the inventory; such amounts should be added back to the new amount.
		if (!result.isEmpty())
			newAmount += result.get(0).getAmount();

		if (newAmount == data.getAmount())
			return BarrelUpdateResult.FAILED_PLAYER_INVENTORY_FULL;

		data.setAmount(newAmount);

		if (newAmount == 0)
			data.setType(null);

		return BarrelUpdateResult.SUCCESS;
	}

	public static void place(ItemStack item, Block block) {
		BarrelData data = BarrelData.fromItemStack(item);

		if (data == null)
			return;

		tracked.put(block, data);
	}

	public static void breakNaturally(Block block) {
		BarrelData data = tracked.remove(block);

		if (data == null)
			return;

		Bukkit.getScheduler().runTask(BiggerBarrelsPlugin.instance(), () -> {
			BarrelItemStack drop;

			if (data.getType() == null)
				drop = new BarrelItemStack(data.getTier());
			else
				drop = new BarrelItemStack(data.getTier(), data.getType(), data.getAmount());

			block.getWorld().dropItemNaturally(block.getLocation(), drop);
		});
	}

	public static void destroy(Block block) {
		tracked.remove(block);
	}

	public static void move(Block oldBlock, Block newBlock) {
		BarrelData data = tracked.remove(oldBlock);

		if (data == null)
			return;

		tracked.put(newBlock, data);
	}

	public static boolean isItemAllowed(ItemStack item) {
		if (item.getMaxStackSize() == 1)
			return false;

		ItemMeta meta = item.getItemMeta();

		return meta.getClass() == ItemMeta.class && !(meta.hasLore() && meta.hasEnchants() && meta.hasDisplayName() &&
			meta.hasCustomModelData() && meta.hasAttributeModifiers());
	}
}
