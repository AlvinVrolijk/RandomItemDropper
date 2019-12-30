package nl.alvinvrolijk.randomitemdropper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wood;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class RandomItemDropper extends JavaPlugin implements Listener {

    @Getter
    private static RandomItemDropper instance;

    @Getter @Setter
    public Boolean toggle = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        RandomItemDropper.instance = this;

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("togglerandomdrops").setExecutor(new ToggleCommand());

        getServer().getConsoleSender().sendMessage("[RandomItemDropper] Plugin enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        RandomItemDropper.instance = null;
        getServer().getConsoleSender().sendMessage("[RandomItemDropper] Plugin disabled");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.getToggle()) {
            for (ItemStack ignored : event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand())) {
                dropRandomItem(event.getBlock().getLocation());
            }
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (this.getToggle()) {
            for (ItemStack ignored : event.getDrops()) {
                dropRandomItem(event.getEntity().getLocation());
            }
            event.getDrops().clear();
        }
    }

    public void dropRandomItem(Location location) {
        try {
            location.getWorld().dropItemNaturally(location, new ItemStack(getRandomMaterial(), 1));
        } catch (IllegalArgumentException ex) {
            dropRandomItem(location); // Try again
        }
    }

    public static Material getRandomMaterial() {
        Material[] material = Material.values();
        int rnd = new Random().nextInt(material.length);
        return material[rnd];
    }
}
