package gameobjects;

import java.util.HashMap;

/**
 * A game object that extends this class is allowed to be controlled by a player.
 */
public abstract class Player extends GameObject {

    protected boolean UpPressed = false;
    protected boolean DownPressed = false;
    protected boolean LeftPressed = false;
    protected boolean RightPressed = false;
    protected boolean ActionPressed = false;

    protected final int MAX_HP = 5;
    protected final int MAX_LIVES = 5;

    protected int currentHP;
    protected int currentLives;

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleActionPressed() {
        this.ActionPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleActionPressed() {
        this.ActionPressed = false;
    }

    public float getHPRatio() {
        return Math.min(1, (float) this.currentHP / (float) this.MAX_HP);
    }

    public int getCurrentLives() {
        return this.currentLives;
    }

    public int getMaxLives() {
        return this.MAX_LIVES;
    }

    public abstract Weapon.Type getWeapon();

    public abstract float getCooldownRatio();

    public abstract HashMap<String, Integer> getStats();

}
