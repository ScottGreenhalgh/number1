package me.cazmik.number1.utils;

public class BlockBreakState {
    private static BlockBreakState instance;
    private boolean allowBlockBreak = false;

    private BlockBreakState() {}

    public static BlockBreakState getInstance() {
        if (instance == null) {
            instance = new BlockBreakState();
        }
        return instance;
    }

    public boolean isAllowBlockBreak() {
        return allowBlockBreak;
    }

    public void toggleAllowBlockBreak() {
        allowBlockBreak = !allowBlockBreak;
    }
}
