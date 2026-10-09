package io.github.thomaslbpinto.firstPaperPlugin.commands;

import io.github.thomaslbpinto.firstPaperPlugin.FirstPaperPlugin;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class PotatoCommand implements BasicCommand {

  @Override
  public void execute(CommandSourceStack source, String[] arguments) {
    if (!(source.getExecutor() instanceof Player player)) {
      source
          .getSender()
          .sendMessage(
              Component.text("This command can only be executed by a player.", NamedTextColor.RED));
      return;
    }

    var playerLocation = player.getLocation();
    var targetLocation =
        playerLocation.add(playerLocation.getDirection().setY(0).normalize().multiply(3));
    var dropLocation = targetLocation.getBlock().getLocation().add(0.5, 0, 0.5);

    var potato = ItemStack.of(Material.POTATO);

    potato.editPersistentDataContainer(
        pdc -> {
          NamespacedKey key =
              new NamespacedKey(JavaPlugin.getPlugin(FirstPaperPlugin.class), "cursed-potato");
          pdc.set(key, PersistentDataType.BOOLEAN, true);
        });

    player
        .getWorld()
        .dropItem(
            dropLocation,
            potato,
            item -> {
              item.setVelocity(new Vector(0, 0, 0));
              item.setGlowing(true);
              item.getWorld()
                  .playSound(item.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
            });

    player.sendMessage(
        Component.text("A ", NamedTextColor.YELLOW)
            .append(Component.text("potato", NamedTextColor.GOLD))
            .append(Component.text("! What could go wrong?", NamedTextColor.YELLOW)));
  }
}
