package io.github.larttyler.biggerbarrels.event.listeners;

import io.github.larttyler.biggerbarrels.BiggerBarrelsPlugin;
import io.github.larttyler.biggerbarrels.barrels.BarrelManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import java.util.List;

public class BarrelBlockChangeListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled() || !BarrelManager.has(event.getBlock()))
			return;

		event.setDropItems(false);
		BarrelManager.breakNaturally(event.getBlock());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBurn(BlockBurnEvent event) {
		BarrelManager.destroy(event.getBlock());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPistonExtendEvent(BlockPistonExtendEvent event) {
		deferBlockMoveUpdates(event.getDirection(), event.getBlocks());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPistonRetractEvent(BlockPistonRetractEvent event) {
		if (!event.isSticky())
			return;

		deferBlockMoveUpdates(event.getDirection(), event.getBlocks());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		BarrelManager.place(event.getItemInHand(), event.getBlock());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
		if (!BarrelManager.has(event.getBlock()))
			return;

		event.setCancelled(true);
	}

	private void deferBlockMoveUpdates(BlockFace direction, List<Block> blocks) {
		for (Block block : blocks) {
			Bukkit.getScheduler().runTask(BiggerBarrelsPlugin.instance(), () -> {
				if (!BarrelManager.has(block))
					return;

				Location location = block.getLocation();

				Block newBlock = block.getWorld().getBlockAt(
					location.getBlockX() + direction.getModX(),
					location.getBlockY() + direction.getModY(),
					location.getBlockZ() + direction.getModZ()
				);

				BarrelManager.move(block, newBlock);
			});
		}
	}
}
