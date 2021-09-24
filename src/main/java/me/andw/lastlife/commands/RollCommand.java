package me.andw.lastlife.commands;

import java.util.stream.Stream;

import javax.swing.text.html.parser.Entity;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import me.andw.lastlife.util.Prefix;

public class RollCommand implements CommandExecutor {

    private LastLife pl;

    public RollCommand(LastLife pl) {
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
        sender.sendMessage(Prefix.STARTING_SENDER.s);
        Player p = Bukkit.getPlayer(args[0]);
        // roll ticks

        RollCommand.roll(p, pl);

        return false;
    }

    public static void roll(Player p, LastLife pl) {
        for (int i = 0; i < 100; i++) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
                public void run() {
                    int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
                    p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 1, 500);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.BLOCKS, 100, 5.0f);
                }
            }, i);
        }

        // then slow it down a little bit
        for (int i = 0; i < 25; i++) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
                public void run() {
                    int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
                    p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 4, 500);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.BLOCKS, 100, 5.0f);
                }
            }, 100+i*4);
        }

        for (int i = 0; i < 20; i++) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
                public void run() {
                    int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
                    p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 8, 500);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 5.0f);
                }
            }, 200+i*8);
        }
        
        // and then one final one
        int lives = (int)(GameManager.gm().minLives+Math.random()*(GameManager.gm().maxLives-GameManager.gm().minLives+1));
        Bukkit.getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
            public void run() {
                p.sendTitle("Your Lives:", Prefix.toColored(lives, String.valueOf(lives)), 0, 200, 100);
                p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, SoundCategory.MASTER, 100, 0.5f);
                p.sendMessage(String.format(Prefix.ON_ROLL_LIFE.s, Prefix.toColored(lives, String.valueOf(lives))));
            }
        }, 360+5);

        GameManager.gm().setPlayer(p.getUniqueId().toString(), lives);

        p.setScoreboard(pl.sb);
        p.setPlayerListName(Prefix.toColored(lives, p.getName()));
    }
}
