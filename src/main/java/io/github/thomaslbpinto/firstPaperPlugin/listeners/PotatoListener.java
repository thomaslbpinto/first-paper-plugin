package io.github.thomaslbpinto.firstPaperPlugin.listeners;

import io.github.thomaslbpinto.firstPaperPlugin.FirstPaperPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotatoListener implements Listener {

  @EventHandler
  public void onPickup(EntityPickupItemEvent event) {
    if (!(event.getEntity() instanceof Player player)) {
      return;
    }

    var item = event.getItem();

    if (item.getItemStack().getType() != Material.POTATO) {
      return;
    }

    NamespacedKey key =
        new NamespacedKey(JavaPlugin.getPlugin(FirstPaperPlugin.class), "cursed-potato");

    if (!(item.getItemStack().getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false))) {
      return;
    }

    item.remove();
    event.setCancelled(true);
    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 0.5F);
    player.getWorld().strikeLightning(player.getLocation());
    player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20 * 10, 0));
    player.sendMessage(Component.text("You're now cursed!", NamedTextColor.RED));
  }
}
