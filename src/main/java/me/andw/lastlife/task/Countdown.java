package me.andw.lastlife.task;

public abstract class Countdown implements Runnable {

    protected int n;
    public Countdown(int n) {
        this.n = n;
    }
}
