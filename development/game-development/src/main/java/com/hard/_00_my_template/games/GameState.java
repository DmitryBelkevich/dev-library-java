package com.hard._00_my_template.games;

import java.awt.*;

public abstract class GameState {
    protected GameStateManager gameStateManager;

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public abstract void update();

    public abstract void draw(Graphics graphics);

    public abstract void keyPressed(int key);

    public abstract void keyReleased(int key);
}
