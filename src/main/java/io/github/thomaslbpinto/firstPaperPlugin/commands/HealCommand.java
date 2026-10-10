package io.github.thomaslbpinto.firstPaperPlugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class HealCommand implements BasicCommand {

  @Override
  public void execute(CommandSourceStack source, String[] arguments) {
    if (!(source.getExecutor() instanceof Player player)) {
      source
          .getSender()
          .sendMessage(
              Component.text("This command can only be executed by a player.", NamedTextColor.RED));
      return;
    }

    player.setHealth(20);
    player.setSaturation(20);
    player.setFoodLevel(20);
    player.setFireTicks(0);
    player.clearActivePotionEffects();
    player.sendMessage(Component.text("You have been healed.", NamedTextColor.GREEN));
  }
}
