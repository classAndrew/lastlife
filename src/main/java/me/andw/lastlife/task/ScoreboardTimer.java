package me.andw.lastlife.task;

import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.commands.BoogeyCommand;
import me.andw.lastlife.util.TimeConversion;
import net.md_5.bungee.api.ChatColor;

public abstract class ScoreboardTimer extends BukkitRunnable {

    protected int seconds;
    protected String lastString = "The boogeyman has "+ChatColor.RED+TimeConversion.minuteConversion(GameManager.gm().boogeyMins*60)+ChatColor.WHITE+" mins left";
    protected Player target;
    public ScoreboardTimer(int seconds, Player target) {
        this.seconds = seconds;
        this.target = target;
    }
}
