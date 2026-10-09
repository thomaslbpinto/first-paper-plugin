package io.github.thomaslbpinto.firstPaperPlugin;

import io.github.thomaslbpinto.firstPaperPlugin.commands.HealCommand;
import io.github.thomaslbpinto.firstPaperPlugin.commands.PotatoCommand;
import io.github.thomaslbpinto.firstPaperPlugin.listeners.ConnectionListener;
import io.github.thomaslbpinto.firstPaperPlugin.listeners.EatingListener;
import io.github.thomaslbpinto.firstPaperPlugin.listeners.PotatoListener;
import io.papermc.paper.command.brigadier.BasicCommand;
import java.util.List;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirstPaperPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    System.out.println("Plugin enabled!");

    Listener[] listeners = {
      new ConnectionListener(), new EatingListener(), new PotatoListener()
    };

    for (Listener listener : listeners) {
      getServer().getPluginManager().registerEvents(listener, this);
    }

    record CommandInfo(String name, List<String> aliases, BasicCommand command) {}

    List<CommandInfo> commands =
        List.of(
            new CommandInfo("heal", List.of("h"), new HealCommand()),
            new CommandInfo("potato", List.of(), new PotatoCommand()));

    for (var command: commands) {
      registerCommand(command.name, command.aliases, command.command);
    }
  }

  @Override
  public void onDisable() {
    System.out.println("Plugin disabled!");
  }
}
