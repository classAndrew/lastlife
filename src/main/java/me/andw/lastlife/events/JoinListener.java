package me.andw.lastlife.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import me.andw.lastlife.GameManager.GameState;
import me.andw.lastlife.commands.BoogeyCommand;
import me.andw.lastlife.commands.RollCommand;
import me.andw.lastlife.util.Prefix;
import me.andw.lastlife.util.ScoreboardUtil;

public class JoinListener implements Listener {

    public LastLife pl;

    public JoinListener(LastLife pl) {
        this.pl = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (GameManager.gm().state == GameState.NOT_STARTED) return;
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        if (GameManager.gm().playerLives.containsKey(uuid)) {
            int lives = GameManager.gm().playerLives.get(uuid);
            p.sendMessage(String.format(Prefix.ON_JOIN.s, String.valueOf(lives)));
            // set tablist and display name
            p.setScoreboard(pl.sb);
            p.setPlayerListName(Prefix.toColored(lives, p.getName()));
        } else {
            p.sendMessage(Prefix.ON_JOIN_NEW.s);
            RollCommand.roll(p, pl);
        }


    }
}
