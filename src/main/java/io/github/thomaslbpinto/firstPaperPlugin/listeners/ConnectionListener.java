package io.github.thomaslbpinto.firstPaperPlugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    event.joinMessage(createMessage("Welcome ", event.getPlayer(), NamedTextColor.GREEN));
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    event.quitMessage(createMessage("Goodbye ", event.getPlayer(), NamedTextColor.RED));
  }

  private Component createMessage(String prefix, Player player, NamedTextColor playerNameColor) {
    NamedTextColor defaultColor = NamedTextColor.GRAY;

    return Component.text(prefix, defaultColor)
        .append(Component.text(player.getName(), playerNameColor).decorate(TextDecoration.BOLD))
        .append(Component.text("!", defaultColor));
  }
}
