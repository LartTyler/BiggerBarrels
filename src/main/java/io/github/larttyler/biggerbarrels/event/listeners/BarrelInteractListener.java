package io.github.larttyler.biggerbarrels.event.listeners;

import io.github.larttyler.biggerbarrels.barrels.BarrelDataSnapshot;
import io.github.larttyler.biggerbarrels.barrels.BarrelManager;
import io.github.larttyler.biggerbarrels.barrels.BarrelUpdateResult;
import io.github.larttyler.biggerbarrels.utility.MaterialUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BarrelInteractListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.hasBlock())
			return;

		Block block = event.getClickedBlock();

		if (block.getType() != Material.BARREL || !BarrelManager.has(block))
			return;

		// Possible actions:
		//   Right Click         -> Store one of held item
		//   Shift + Right Click -> Store all of held item
		//   Left Click          -> Retrieve one item from barrel
		//   Shift + Left Click  -> Retrieve a stack of items from barrel
		//
		// For both right click actions, if there are no items in-hand, info on barrel size and contents will instead
		// be output to chat.
		//
		// For both left click actions, if there is currently an item in-hand, no items will be retrieved, unless the
		// item in-hand is the same as the stored type.

		Player player = event.getPlayer();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = event.getItem();

			if (item == null) {
				BarrelDataSnapshot data = BarrelManager.getSnapshot(block);

				player.sendMessage(String.format(
					"%sTier %d Barrel: %s%d / %d items",
					ChatColor.GOLD,
					data.getTier(),
					ChatColor.RESET,
					data.getAmount(),
					data.getMaxAmount()
				));

				return;
			}

			if (BarrelManager.store(block, item) == BarrelUpdateResult.FAILED_ITEM_NOT_ALLOWED)
				player.sendMessage("This item cannot be stored in a barrel.");
		} else {
			if (event.hasItem() && MaterialUtil.isTool(event.getItem().getType()))
				return;

			BarrelManager.retrieve(block, player, player.isSneaking());
		}
	}
}
