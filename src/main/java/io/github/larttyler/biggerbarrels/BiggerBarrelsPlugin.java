package io.github.larttyler.biggerbarrels;

import io.github.larttyler.biggerbarrels.event.listeners.BarrelBlockChangeListener;
import io.github.larttyler.biggerbarrels.event.listeners.BarrelInteractListener;
import io.github.larttyler.biggerbarrels.event.listeners.BarrelRecipeListener;
import io.github.larttyler.biggerbarrels.recipes.BarrelRecipe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiggerBarrelsPlugin extends JavaPlugin {
	private static BiggerBarrelsPlugin instance;

	public static BiggerBarrelsPlugin instance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		Bukkit.getServer().addRecipe(new BarrelRecipe());

		Bukkit.getPluginManager().registerEvents(new BarrelRecipeListener(), this);
		Bukkit.getPluginManager().registerEvents(new BarrelInteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new BarrelBlockChangeListener(), this);
	}
}
