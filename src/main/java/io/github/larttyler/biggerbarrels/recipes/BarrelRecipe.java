package io.github.larttyler.biggerbarrels.recipes;

import io.github.larttyler.biggerbarrels.BiggerBarrelsPlugin;
import io.github.larttyler.biggerbarrels.barrels.BarrelItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class BarrelRecipe extends ShapelessRecipe {
	public BarrelRecipe() {
		super(new NamespacedKey(BiggerBarrelsPlugin.instance(), "barrel.tier-1"), new BarrelItemStack(1));

		this.addIngredient(2, Material.BARREL);
	}
}
