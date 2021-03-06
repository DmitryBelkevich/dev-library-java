package com.hard.game_states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
    private List<GameState> gameStates;
    private GameState currentGameState;

    public GameStateManager() {
        this.gameStates = new ArrayList<>();
    }

    public void addGameState(GameState gameState) {
        gameStates.add(gameState);
    }

    public GameState getGameState(int i) {
        return gameStates.get(i);
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public void keyPressed(KeyEvent e) {
        currentGameState.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        currentGameState.keyReleased(e);
    }

    public void update(double time) {
        currentGameState.update(time);
    }

    public void draw(Graphics graphics) {
        currentGameState.draw(graphics);
    }
}
