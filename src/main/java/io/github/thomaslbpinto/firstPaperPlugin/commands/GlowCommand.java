package io.github.thomaslbpinto.firstPaperPlugin.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GlowCommand {

  public static LiteralCommandNode<CommandSourceStack> execute() {
    return Commands.literal("glow")
        .executes(
            context -> {
              return makePlayerGlow(context.getSource(), "white");
            })
        .then(
            Commands.literal("off")
                .executes(
                    context -> {
                      return removePlayGlow(context.getSource());
                    }))
        .then(
            Commands.argument("color", StringArgumentType.word())
                .executes(
                    context -> {
                      return makePlayerGlow(
                          context.getSource(), StringArgumentType.getString(context, "color"));
                    }))
        .build();
  }

  private static int makePlayerGlow(CommandSourceStack source, String colorName) {
    if (!(source.getExecutor() instanceof Player player)) {
      source
          .getSender()
          .sendMessage(
              Component.text("This command can only be executed by a player.", NamedTextColor.RED));
      return 0;
    }

    NamedTextColor textColor = NamedTextColor.NAMES.value(colorName.toLowerCase());

    if (textColor == null) {
      player.sendMessage(
          Component.text("Invalid color! Try red, blue, green, yellow, etc.", NamedTextColor.RED));
      return 0;
    }

    setPlayerGlowColor(player, textColor);

    player.setGlowing(true);

    player.sendMessage(
        Component.text("You are now glowing in ", NamedTextColor.GRAY)
            .append(Component.text(colorName, textColor))
            .append(Component.text(".", NamedTextColor.GRAY)));
    return 1;
  }

  private static void setPlayerGlowColor(Player player, NamedTextColor color) {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    String teamName = "glow_" + color.toString();

    Team team = scoreboard.getTeam(teamName);
    if (team == null) {
      team = scoreboard.registerNewTeam(teamName);
      team.color(color);
    }

    removeFromGlowTeams(player);
    team.addEntry(player.getName());
  }

  private static int removePlayGlow(CommandSourceStack source) {
    if (!(source.getExecutor() instanceof Player player)) {
      source
          .getSender()
          .sendMessage(
              Component.text(
                  "This command can only be executed by a player.", NamedTextColor.GRAY));
      return 0;
    }

    player.setGlowing(false);
    removeFromGlowTeams(player);

    player.sendMessage(Component.text("You are not glowing anymore.", NamedTextColor.GREEN));
    return 1;
  }

  private static void removeFromGlowTeams(Player player) {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    for (Team team : scoreboard.getTeams()) {
      if (team.getName().startsWith("glow_")) {
        team.removeEntry(player.getName());
      }
    }
  }
}
