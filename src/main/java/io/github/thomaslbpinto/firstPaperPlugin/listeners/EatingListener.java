package io.github.thomaslbpinto.firstPaperPlugin.listeners;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EatingListener implements Listener {

  @EventHandler
  public void onTeleport(PlayerTeleportEvent event) {
    Player player = event.getPlayer();
    boolean isSneaking = player.isSneaking();

    if (!isSneaking) {
      return;
    }

    if (event.getCause() != PlayerTeleportEvent.TeleportCause.CONSUMABLE_EFFECT) {
      return;
    }

    player.sendMessage(Component.text("Event canceled", NamedTextColor.RED));
    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 0.5F);
    event.setCancelled(true);
  }
}
