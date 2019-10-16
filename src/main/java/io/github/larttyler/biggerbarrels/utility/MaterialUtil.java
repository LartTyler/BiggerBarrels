package io.github.larttyler.biggerbarrels.utility;

import org.bukkit.Material;

public final class MaterialUtil {
	public static boolean isTool(Material type) {
		String name = type.name();

		switch (name.substring(name.lastIndexOf("_") + 1)) {
			case "_PICKAXE":
			case "_AXE":
			case "_SHOVEL":
			case "_HOE":
				return true;

			default:
				return false;
		}
	}
}
