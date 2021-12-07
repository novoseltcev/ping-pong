package com.jgame.interfaces;

import com.jgame.Sprite;

import java.util.Collection;


public interface IGameEngine {
    void pause();

    void resume();
    void restart();

    void stop();

    default void activateSpites(Collection<Sprite> sprites) {
        for (Sprite sprite: sprites) {
            sprite.activate();
        }
    }

    default void deactivateSprites(Collection<Sprite> sprites) {
        for (Sprite sprite: sprites) {
            sprite.deactivate();
        }
    }

    default void respawnSprites(Collection<Sprite> sprites){
        for (Sprite sprite: sprites) {
            sprite.respawn();
        }
    }

    default void updateSprites(Collection<Sprite> sprites){
        for (Sprite sprite: sprites) {
            sprite.updateView();
        }
    }
}
