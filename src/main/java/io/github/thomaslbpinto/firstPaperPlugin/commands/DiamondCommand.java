package io.github.thomaslbpinto.firstPaperPlugin.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DiamondCommand {

  public static LiteralCommandNode<CommandSourceStack> execute() {
    return Commands.literal("diamond")
        .then(
            Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                .executes(
                    context -> {
                      return giveDiamondAmount(
                          context.getSource(), IntegerArgumentType.getInteger(context, "amount"));
                    }))
        .build();
  }

  private static int giveDiamondAmount(CommandSourceStack source, int amount) {
    if (!(source.getExecutor() instanceof Player player)) {
      source
          .getSender()
          .sendMessage(
              Component.text("This command can only be executed by a player.", NamedTextColor.RED));
      return 0;
    }

    player.give(new ItemStack(Material.DIAMOND, amount));
    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
    player.sendMessage(Component.text("You have been given " + amount + " diamond" + (amount > 1 ? "s" : "") + ".", NamedTextColor.GREEN));

    return 1;
  }
}
