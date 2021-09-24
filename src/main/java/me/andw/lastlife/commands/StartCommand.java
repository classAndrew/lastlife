package me.andw.lastlife.commands;

import java.util.stream.Stream;

import javax.swing.text.html.parser.Entity;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import me.andw.lastlife.GameManager.GameState;
import me.andw.lastlife.util.Prefix;
import net.md_5.bungee.api.ChatColor;

public class StartCommand implements CommandExecutor {

    private LastLife pl;

    public StartCommand(LastLife pl) {
        super();
        this.pl = pl;
    }

    /**
     * Roll all currently online players asynchronously
     * Set the game state to RUNNING
     * When the game is RUNNING, any new joins will get rolled
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GameManager.gm().state = GameState.STARTED;
        sender.sendMessage(Prefix.STARTING_SENDER.s);
        Object[] onPlayers = Bukkit.getOnlinePlayers().stream().toArray();
        // roll ticks
        for (int i = 0; i < 100; i++) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
                public void run() {
                    for (Object _p : onPlayers) {
                        Player p = (Player)_p;
                        int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
                        p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 1, 500);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.BLOCKS, 100, 5.0f);
                    }
                }
            }, i);
        }

        // then slow it down a little bit
        for (int i = 0; i < 25; i++) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
                public void run() {
                    for (Object _p : onPlayers) {
                        Player p = (Player)_p;
                        int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
                        p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 4, 500);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.BLOCKS, 100, 5.0f);
                    }
                }
            }, 100+i*4);
        }

        for (int i = 0; i < 20; i++) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
                public void run() {
                    for (Object _p : onPlayers) {
                        Player p = (Player)_p;
                        int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
                        p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 8, 500);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 5.0f);
                    }
                }
            }, 200+i*8);
        }
        
        // and then one final one
        int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
        Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
            public void run() {
                for (Object _p : onPlayers) {
                    Player p = (Player)_p;
                    p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 200, 100);
                    p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, SoundCategory.MASTER, 100, 0.5f);
                    p.sendMessage(String.format(Prefix.ON_ROLL_LIFE.s, Prefix.toColored(lives, String.valueOf(lives))));
                    GameManager.gm().setPlayer(p.getUniqueId().toString(), lives);

                    // set gamemode to survival
                    p.setGameMode(GameMode.SURVIVAL);

                    p.setScoreboard(pl.sb);
                    p.setPlayerListName(Prefix.toColored(lives, p.getName()));
                    // pl.sb.getTeam("Awesome").addEntry(p.getName());
                }
            }
        }, 360+5);

        return false;
    }
}
