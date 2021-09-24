package me.andw.lastlife.util;

import java.io.Console;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtil {

    public static void clearBoard(Scoreboard sb) {
        for (String entry : sb.getEntries()) {
            sb.resetScores(entry);
        }
    }
    
    public static void writeBoard(Scoreboard sb, String s) {
        clearBoard(sb);
        sb.getObjective("boogey").getScore(s).setScore(0);
    }
}
