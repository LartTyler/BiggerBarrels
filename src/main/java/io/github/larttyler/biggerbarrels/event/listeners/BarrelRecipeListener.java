package io.github.larttyler.biggerbarrels.event.listeners;

import io.github.larttyler.biggerbarrels.persistence.keys.ItemKeys;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BarrelRecipeListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getClickedInventory() instanceof CraftingInventory))
			return;

		CraftingInventory inventory = (CraftingInventory)event.getClickedInventory();

		ItemMeta resultMeta = null;
		int count = 0;
		int tier = 0;

		for (ItemStack item : inventory.getMatrix()) {
			if (item == null)
				continue;
			else if (++count > 2 || item.getType() != Material.BARREL)
				return;

			ItemMeta meta = item.getItemMeta();
			PersistentDataContainer data = meta.getPersistentDataContainer();

			if (meta == null || !data.has(ItemKeys.Tier, PersistentDataType.INTEGER))
				return;

			int currentTier = data.get(ItemKeys.Tier, PersistentDataType.INTEGER);

			if (tier == 0)
				tier = currentTier;
			else if (currentTier != tier)
				return;

			if (data.has(ItemKeys.ContentType, PersistentDataType.STRING)) {
				// Cannot upgrade using two barrels that already contain items
				if (resultMeta != null)
					return;

				resultMeta = meta;
			}
		}

		ItemStack result = new ItemStack(Material.BARREL);

		if (resultMeta != null)
			resultMeta = result.getItemMeta();

		resultMeta.setDisplayName("Tier " + (tier + 1) + " Barrel");
		resultMeta.getPersistentDataContainer().set(ItemKeys.Tier, PersistentDataType.INTEGER, tier + 1);

		result.setItemMeta(resultMeta);

		inventory.setResult(result);
	}
}
