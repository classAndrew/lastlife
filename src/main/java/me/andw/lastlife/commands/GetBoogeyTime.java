package me.andw.lastlife.commands;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetBoogeyTime implements CommandExecutor {
    private LastLife pl;

    public GetBoogeyTime(LastLife pl) {
        super();
        this.pl = pl;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        p.sendMessage(String.valueOf(GameManager.gm().getBoogeyMins()));
        return false;
    }

}
