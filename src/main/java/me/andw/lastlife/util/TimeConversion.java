package me.andw.lastlife.util;

public class TimeConversion {
    public static String minuteConversion(int seconds) {
        int minutes = seconds/60;
        int remain = seconds - minutes*60;
        return ((minutes < 10) ? "0" : "") + String.valueOf(minutes) + ":"  + (remain < 10 ? "0" : "") + String.valueOf(remain);
    }
}
