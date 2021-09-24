package me.andw.lastlife.commands;

import java.util.UUID;
import java.util.stream.Stream;

import javax.swing.text.html.parser.Entity;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import me.andw.lastlife.task.Countdown;
import me.andw.lastlife.task.ScoreboardTimer;
import me.andw.lastlife.util.Prefix;
import me.andw.lastlife.util.ScoreboardUtil;
import me.andw.lastlife.util.TimeConversion;
import net.md_5.bungee.api.ChatColor;

public class BoogeyCommand implements CommandExecutor {

    private LastLife pl;

    public static BukkitRunnable br;

    public BoogeyCommand(LastLife pl) {
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
        int secondsToChoice = 0;
        Player target;
        if (args[0].equals("random")) {
            secondsToChoice = Integer.parseInt(args[1]);
            Object[] players = Bukkit.getOnlinePlayers().toArray();
            Bukkit.getScheduler().runTaskLaterAsynchronously(pl, () -> {

            }, secondsToChoice*20);

            for (int sec = 0; sec < secondsToChoice; sec++) {
                Bukkit.getScheduler().runTaskLater(pl, new Countdown(secondsToChoice-sec) {
                    public void run() {
                        for (Object _p : players) {
                            Player p = (Player)_p;
                            p.sendTitle(ChatColor.RED+"Boogeyman in "+
                                ChatColor.BOLD+String.valueOf(this.n), "seconds", 5, 10, 5);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 100, 0.5f);
                        }
                    }
                }, sec*20);
            }

            Bukkit.getScheduler().runTaskLater(pl, () -> {
                    for (Object _p : players) {
                        Player p = (Player)_p;
                        p.sendTitle(ChatColor.YELLOW+"You are... ", "", 5, 50, 5);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 100, 0.5f);

                    }
                }, (secondsToChoice)*20);

            // filter out the players who are already out or at last life
            Object[] playersFiltered = GameManager.gm().playerLives.keySet().parallelStream().filter(
                (String s) -> {
                    return GameManager.gm().getLives(s) > 1;
                }
            ).toArray();

            if (playersFiltered.length == 0) {
                sender.sendMessage(Prefix.BOOGEY_ALL_OUT.s);
                return false;
            }
            
            int boogeyIdx = (int)(Math.random()*playersFiltered.length);
            Bukkit.getScheduler().runTaskLater(pl, () -> {
                for (int i = 0; i < playersFiltered.length; i++) {
                    if (boogeyIdx == i) continue;
                    Player p = Bukkit.getPlayer((String)playersFiltered[i]);
                    if (GameManager.gm().playerLives.get(playersFiltered[i]) <= 0) {
                        p.sendTitle(ChatColor.GREEN+"You are spectating.", "You lost all your lives.", 5, 50, 5);
                    }
                    else {
                        p.sendTitle(ChatColor.GREEN+"NOT the Boogeyman!", "", 5, 50, 5);
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 100, 2.0f);

                }
            }, (secondsToChoice+3)*20);   

            target = Bukkit.getPlayer(UUID.fromString((String)playersFiltered[boogeyIdx]));

            Bukkit.getScheduler().runTaskLater(pl, () -> {  
                target.sendTitle(ChatColor.RED+"The Boogeyman!", "", 5, 50, 5);
                target.playSound(target.getLocation(), Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, 100, 1.f);
                target.sendMessage(String.format(Prefix.BOOGEY_INFO.s, String.valueOf(GameManager.gm().boogeyMins)));
                GameManager.gm().setBoogey(target.getUniqueId().toString());
            }, (secondsToChoice+4)*20);
            
        }
        else if (args[0].equals("choose")) {
            target = Bukkit.getPlayer(args[1]);
            
            target.sendTitle(ChatColor.RED+"The Boogeyman!", "", 5, 50, 5);
            target.playSound(target.getLocation(), Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, 100, 1.f);
            target.sendMessage(String.format(Prefix.BOOGEY_INFO.s, String.valueOf(GameManager.gm().boogeyMins)));

            GameManager.gm().setBoogey(target.getUniqueId().toString());
        }
        else {
            sender.sendMessage(String.format(Prefix.BOOGEY_CMD_FAIL.s, args[0]));
            return false;
        }

        ScoreboardUtil.clearBoard(pl.sb);
        Objective obj = pl.sb.getObjective("boogey");
        br = new ScoreboardTimer(GameManager.gm().boogeyMins*60, target) {
            public void run() {
                if (this.seconds <= 0) {
                    this.cancel();
                    for (Object _p : Bukkit.getOnlinePlayers().stream().toArray()) {
                        Player p = (Player)_p;
                        p.sendTitle("The Boogeyman has failed!", "", 10, 20, 10);
                        ScoreboardUtil.clearBoard(pl.sb);
                        ScoreboardUtil.writeBoard(pl.sb, "The Boogeyman has failed!");
                        this.target.sendMessage(Prefix.BOOGEY_MISSION_F.s);
                        GameManager.gm().setPlayer(this.target.getUniqueId().toString(), 1);
                    }
                }

                pl.sb.resetScores(this.lastString);
                this.lastString = "The boogeyman has "+ChatColor.RED+TimeConversion.minuteConversion(this.seconds)+ChatColor.WHITE+" mins left";
                obj.getScore(this.lastString).setScore(0);
                this.seconds--;
            }
        };
        br.runTaskTimer(pl, (secondsToChoice+4)*20, 20);

        return false;
    }
}
