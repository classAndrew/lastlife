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
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import me.andw.lastlife.commands.BoogeyCommand;
import me.andw.lastlife.util.Prefix;
import me.andw.lastlife.util.ScoreboardUtil;

public class DeathListener implements Listener {

    public LastLife pl;

    public DeathListener(LastLife pl) {
        this.pl = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        handleDeath(e.getEntity());
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            p = (Player)e.getEntity();
            Player dmger = p.getKiller();
            if (GameManager.gm().boogeyman.equals(dmger.getUniqueId().toString())) {
                dmger.sendMessage(Prefix.BOOGEY_MISSION_S.s);
                GameManager.gm().boogeyman = null;
                BoogeyCommand.br.cancel();
                ScoreboardUtil.clearBoard(pl.sb);
                ScoreboardUtil.writeBoard(pl.sb, "The Boogeyman has succeeded!");
            }
        }
    }

    // respawn when player is out of the game
    @EventHandler
    public void onRespawnOut(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        int lives = GameManager.gm().getLives(p.getUniqueId().toString());
        if (lives <= 0) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(Prefix.ON_RESPAWN_OUT.s);

        } else {
            p.sendMessage(String.format(Prefix.GET_LIVES.s, Prefix.toColored(lives, String.valueOf(lives))));
        }
        
    }

    public static void handleDeath(Entity e) {
        if (e instanceof Player) {
            Player p = (Player)e;
            if (p.getHealth() > 0.0) return;
            GameManager.gm().removeLife(p.getUniqueId().toString());
            p.setPlayerListName(Prefix.toColored(GameManager.gm().getLives(p.getUniqueId().toString()), p.getName()));
        }
    }
}
