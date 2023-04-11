package me.andw.lastlife.commands;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBoogeyTime implements CommandExecutor {
    private LastLife pl;
    public SetBoogeyTime(LastLife pl) {
        super();
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int Mins = Integer.parseInt(args[0]);
        GameManager.gm().setBoogeyMins(Mins);
        return false;
    }

}
