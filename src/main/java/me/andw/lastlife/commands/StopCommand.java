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
import me.andw.lastlife.GameManager.GameState;
import me.andw.lastlife.util.Prefix;

public class StopCommand implements CommandExecutor {

    private LastLife pl;

    public StopCommand(LastLife pl) {
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
        GameManager.gm().state = GameState.NOT_STARTED;
        GameManager.save();
        sender.sendMessage(Prefix.STOPPING.s);
        return false;
    }
}
