package io.github.larttyler.biggerbarrels.recipes;

import io.github.larttyler.biggerbarrels.BiggerBarrelsPlugin;
import io.github.larttyler.biggerbarrels.persistence.keys.ItemKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BarrelRecipe extends ShapelessRecipe {
	public BarrelRecipe() {
		super(new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.tier-1"), createResultItemStack());

		this.addIngredient(2, Material.BARREL);
	}

	protected static ItemStack createResultItemStack() {
		ItemStack result = new ItemStack(Material.BARREL);
		ItemMeta meta = result.getItemMeta();

		assert meta != null : "Newly created item stack had null meta";
		meta.setDisplayName("Tier 1 Barrel");
		meta.getPersistentDataContainer().set(ItemKeys.Tier, PersistentDataType.INTEGER, 1);

		result.setItemMeta(meta);

		return result;
	}
}
