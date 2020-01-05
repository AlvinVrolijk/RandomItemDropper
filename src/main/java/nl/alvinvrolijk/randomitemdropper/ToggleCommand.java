package nl.alvinvrolijk.randomitemdropper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            RandomItemDropper instance = RandomItemDropper.getInstance();
            boolean state = instance.getToggle();

            if (state) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lRandom Item Drops: &r&cOFF"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lRandom Item Drops: &r&aON"));
            }
            instance.setToggle(!state);

            return true;
        }

        return false;
    }
}
