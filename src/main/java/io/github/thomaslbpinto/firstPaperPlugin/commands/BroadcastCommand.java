package io.github.thomaslbpinto.firstPaperPlugin.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class BroadcastCommand {

  public static LiteralCommandNode<CommandSourceStack> execute() {
    return Commands.literal("broadcast")
        .then(
            Commands.argument("message", StringArgumentType.greedyString())
                .executes(
                    context -> {
                      Bukkit.getServer()
                          .broadcast(
                              Component.text("[BROADCAST] ", NamedTextColor.YELLOW)
                                  .append(
                                      Component.text(
                                          StringArgumentType.getString(context, "message"),
                                          NamedTextColor.GRAY)));
                      return 1;
                    }))
        .build();
  }
}
