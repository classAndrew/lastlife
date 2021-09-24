package me.andw.lastlife;

import me.andw.lastlife.commands.BoogeyCommand;
import me.andw.lastlife.commands.GetlifeCommand;
import me.andw.lastlife.commands.GivelifeCommand;
import me.andw.lastlife.commands.RollCommand;
import me.andw.lastlife.commands.SetlifeCommand;
import me.andw.lastlife.commands.StartCommand;
import me.andw.lastlife.commands.StopCommand;
import me.andw.lastlife.events.DeathListener;
import me.andw.lastlife.events.JoinListener;
import me.andw.lastlife.util.Prefix;
import net.md_5.bungee.api.ChatColor;

import java.io.File;

import com.google.gson.GsonBuilder;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;


public class LastLife extends JavaPlugin {

    public Scoreboard sb;

    @Override
    public void onEnable() {
        getLogger().info(Prefix.ENABLING.s);
        // check for existing save file
        GameManager.p = this;
        GameManager.gm();
        this.getCommand("start").setExecutor(new StartCommand(this));
        this.getCommand("roll").setExecutor(new RollCommand(this));
        this.getCommand("givelife").setExecutor(new GivelifeCommand(this));
        this.getCommand("boogey").setExecutor(new BoogeyCommand(this));
        this.getCommand("getlife").setExecutor(new GetlifeCommand(this));
        this.getCommand("setlife").setExecutor(new SetlifeCommand(this));
        this.getCommand("stop").setExecutor(new StopCommand(this));

        new DeathListener(this);
        new JoinListener(this);

        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        sb.registerNewTeam("Awesome");
        // sb.getTeam("Awesome").setPrefix(ChatColor.GREEN+"Yeet");
        sb.registerNewObjective("boogey", "boogey", "Boogeyman");
        sb.getObjective("boogey").getScore(Prefix.BOOGEY_SB_INFO.s).setScore(0);
        sb.getObjective("boogey").setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public void onDisable() {
        getLogger().info(Prefix.DISABLING.s);

    }
}