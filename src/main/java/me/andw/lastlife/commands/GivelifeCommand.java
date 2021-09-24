package me.andw.lastlife.commands;

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

import me.andw.lastlife.GameManager;
import me.andw.lastlife.LastLife;
import me.andw.lastlife.util.Prefix;

public class GivelifeCommand implements CommandExecutor {

    private LastLife pl;

    public GivelifeCommand(LastLife pl) {
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
        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        int lives = GameManager.gm().getLives(p.getUniqueId().toString());
        if (lives <= 1) {
            p.sendMessage(Prefix.GIVE_LIFE_FAIL.s);
            return false;
        }

        p.sendMessage(String.format(Prefix.GIVE_LIFE.s, p.getName()));
        Player target = Bukkit.getPlayer(args[0]);
        p.playEffect(EntityEffect.TOTEM_RESURRECT);
        GameManager.gm().giveLife(p.getUniqueId().toString(), target.getUniqueId().toString());
        
        // spawn particles in a cylinder around the target
        Location pLoc = p.getLocation();
        for (int y = 0; y < 3; y++) {
            for (int t = 0; t < 20; t++) {
                Location sLoc = pLoc.clone().add(1.2*Math.cos(Math.PI*t/10), y, 1.2*Math.sin(Math.PI*t/10));
                target.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, sLoc, 20);
            }
        }

        target.sendTitle("You have received one life.", "from "+p.getName(), 10, 40, 10);
        return false;
    }


}
