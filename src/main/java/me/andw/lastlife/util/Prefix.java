package me.andw.lastlife.util;

import net.md_5.bungee.api.ChatColor;

/**
 * Last Life

Command
/lastlife start
	Admin locked
/givelife <targetPlayer>
	Check if sender and target are participating players
/lastlife roll <targetPlayer>
	Admin Locked
/lastlife set <targetPlayer> <lives>
	Admin Locked
/lastlife boogeyman <random | choose> <timer=0>
	Admin Locked
/lastlife stop
	Admin locked
/lastlife getlife <player?>
    Admin Locked
/lastlife setlife <player?>
    Admin Locked

EVERYTIME a gamemanager state changes, write it to a file
	so /givelife, player joins, player deaths, new roll

Need to figure out how to save timer (maybe not save timer)
Read constants from config
 */
public enum Prefix {
    STARTING_SENDER("[LastLife] You are starting the game. Good luck :^)"),
    STARTING("[LastLife] The game is starting! Good luck :^)"),
    STOPPING("[LastLife] The game is stopping! Hope you had fun!"),

    ON_JOIN("[LastLife] Welcome back, you have %s lives left"),
    ON_ROLL_LIFE("[LastLife] You have rolled a %s! Good luck!"),
    ON_JOIN_NEW("[LastLife] Welcome to LastLife. Your lives will be given to you now."),
    ON_RESPAWN_OUT("[LastLife] You have lost ALL your lives. You are out of the game and may spectate."),

    GET_LIVES("[LastLife] You have %s lives left"),
    GET_LIVES_TARGET("[LastLife] %s has %s lives left"),
    GIVE_LIFE("[LastLife] You have given 1 life to %s"),
    GIVE_LIFE_FAIL("[LastLife] You do not have enough lives to give!"),
    BOOGEY_CMD_FAIL("[LastLife] '%s' is not a recognized parameter."),
    BOOGEY_INFO(ChatColor.GRAY+"You are the Boogeyman. You must kill a "+ChatColor.GREEN+"green"+ChatColor.GRAY+" or " +
    ChatColor.YELLOW + "yellow"+ChatColor.GRAY+" name to be cured. If you fail to do this in "+ChatColor.WHITE+"%s minutes"+
    ChatColor.GRAY+", then you will be set to "+ChatColor.RED+"your LAST life!"),
    BOOGEY_ALL_OUT("[LastLife] No one has more than one life to be choosen"),
    BOOGEY_SB_INFO("/boogey random <seconds> to assign"),
    BOOGEY_MISSION_F("[LastLife] You have failed your job as the boogeyman! You now have 1 remaining life."),
    BOOGEY_MISSION_S("[LastLife] You have claimed a life to rid yourself of the curse."),

    ENABLING("[LastLife] (Not Official) by classAndrew! https://andw.me ENABLING!"),
    DISABLING("[LastLife] (Not Official) by classAndrew! https://andw.me Disabling!");

    public final String s;
    private Prefix(String s) {
        this.s = s;
    }

    public static String toColored(int n, String s) {
        switch (n) {
            case 0:
            return ChatColor.STRIKETHROUGH+""+ChatColor.GRAY+s+ChatColor.RESET;
            case 1:
            return ChatColor.RED+s+ChatColor.RESET;
            case 2:
            return ChatColor.YELLOW+s+ChatColor.RESET;
            case 3:
            return ChatColor.GREEN+s+ChatColor.RESET;
        }

        return ChatColor.DARK_GREEN+s+ChatColor.RESET;
    }
}
