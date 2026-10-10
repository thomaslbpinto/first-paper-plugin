package io.github.thomaslbpinto.firstPaperPlugin.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class SpawnerCommand {

  public static LiteralCommandNode<CommandSourceStack> execute() {
    return Commands.literal("spawner")
        .then(
            Commands.literal("set")
                .then(
                    Commands.literal("zombie")
                        .executes(
                            context -> changeSpawnerMob(context.getSource(), EntityType.ZOMBIE)))
                .then(
                    Commands.literal("creeper")
                        .executes(
                            context -> changeSpawnerMob(context.getSource(), EntityType.CREEPER))))
        .then(Commands.literal("clear").executes(context -> clearSpawnerMob(context.getSource())))
        .build();
  }

  private static int changeSpawnerMob(CommandSourceStack source, EntityType entityType) {
    return processSpawner(
        source,
        (target, player) -> {
          target.spawner().setSpawnedType(entityType);
          target.saveAction().run();
          player.sendMessage(
              Component.text(
                  "Spawner in "
                      + target.location()
                      + " had its mob changed to "
                      + entityType.key().value().toUpperCase()
                      + ".",
                  NamedTextColor.GREEN));
        });
  }

  private static int processSpawner(CommandSourceStack source, SpawnerAction action) {
    if (!(source.getExecutor() instanceof Player player)) {
      source
          .getSender()
          .sendMessage(
              Component.text("This command can only be executed by a player.", NamedTextColor.RED));
      return 0;
    }

    SpawnerTarget target = getSpawnerTarget(player);

    if (target == null) {
      player.sendMessage(
          Component.text(
              "You need to look at or hold a spawner to change/clear its mob.", NamedTextColor.RED));
      return 0;
    }

    action.run(target, player);

    return 1;
  }

  private static SpawnerTarget getSpawnerTarget(Player player) {
    Material spawnerMaterial = Material.SPAWNER;

    ItemStack mainHand = player.getInventory().getItemInMainHand();
    if (mainHand.getType() == spawnerMaterial
        && mainHand.getItemMeta() instanceof BlockStateMeta meta) {
      if (meta.getBlockState() instanceof CreatureSpawner spawner) {
        return new SpawnerTarget(
            spawner,
            "main hand",
            () -> {
              meta.setBlockState(spawner);
              mainHand.setItemMeta(meta);
            });
      }
    }

    ItemStack offHand = player.getInventory().getItemInOffHand();
    if (offHand.getType() == spawnerMaterial
        && offHand.getItemMeta() instanceof BlockStateMeta meta) {
      if (meta.getBlockState() instanceof CreatureSpawner spawner) {
        return new SpawnerTarget(
            spawner,
            "off hand",
            () -> {
              meta.setBlockState(spawner);
              offHand.setItemMeta(meta);
            });
      }
    }

    Block facingBlock = player.getTargetBlockExact(5);
    if (facingBlock != null && facingBlock.getType() == spawnerMaterial) {
      if (facingBlock.getState() instanceof CreatureSpawner spawner) {
        return new SpawnerTarget(spawner, "sight", spawner::update);
      }
    }

    return null;
  }

  private static int clearSpawnerMob(CommandSourceStack source) {
    return processSpawner(
        source,
        (target, player) -> {
          target.spawner().setSpawnedType(null);
          target.saveAction().run();
          player.sendMessage(
              Component.text(
                  "Spawner in " + target.location() + " had its mob cleared.",
                  NamedTextColor.GREEN));
        });
  }

  @FunctionalInterface
  private interface SpawnerAction {
    void run(SpawnerTarget target, Player player);
  }

  private record SpawnerTarget(CreatureSpawner spawner, String location, Runnable saveAction) {}
}
