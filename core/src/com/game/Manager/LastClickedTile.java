package com.game.Manager;

public class LastClickedTile {
    private int x;
    private int y;
    private String name;

    public LastClickedTile() {
        this.x = -1;
        this.y = -1;
        this.name = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
