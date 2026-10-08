package io.github.thomaslbpinto.firstPaperPlugin;

import io.github.thomaslbpinto.firstPaperPlugin.listeners.ConnectionListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirstPaperPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    System.out.println("Plugin enabled!");

    getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
  }

  @Override
  public void onDisable() {
    System.out.println("Plugin disabled!");
  }
}
