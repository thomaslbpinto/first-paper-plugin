package io.github.thomaslbpinto.firstPaperPlugin;

import io.github.thomaslbpinto.firstPaperPlugin.listeners.ConnectionListener;
import io.github.thomaslbpinto.firstPaperPlugin.listeners.EatingListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirstPaperPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    System.out.println("Plugin enabled!");

    Listener[] listeners = {new ConnectionListener(), new EatingListener()};

    for (Listener listener: listeners) {
      getServer().getPluginManager().registerEvents(listener, this);
    }
  }

  @Override
  public void onDisable() {
    System.out.println("Plugin disabled!");
  }
}
