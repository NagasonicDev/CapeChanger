package au.nagasonic.capechanger.commands;

import au.nagasonic.capechanger.Cape;
import au.nagasonic.capechanger.CapeChanger;
import au.nagasonic.capechanger.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CapeCommands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player;
        if (args[1] != null){
            player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                Util.sendColMsg(sender, "&cThere is no player named &e" + args[1]);
                return false;
            }
        }else player = (Player) sender;
        if (Cape.capes.containsKey(args[0])){
            Cape cape = Cape.capes.get(args[0]);
            if (CapeChanger.setCape(player, cape) == false){
                Util.sendColMsg(sender, "&eThat player already has that cape equiped.");
            }
        }else{
            Util.sendColMsg(sender, "&cThere is not cape with key &e" + args[0]);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Cape.capes.keySet().stream().toList();
    }
}
