import GameObjects.Player;
import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class PlayerController implements KeyListener {

    private Player player;
    private HashMap<Integer, Key> controls;

    public PlayerController(Player obj, HashMap<Integer, Key> controls) {
        this.player = obj;
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Print key pressed
        System.out.println(e.paramString());

        if (this.controls.get(e.getKeyCode()) == Key.up) {
            this.player.toggleUpPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.down) {
            this.player.toggleDownPressed();
        }

        if (this.controls.get(e.getKeyCode()) == Key.left) {
            this.player.toggleLeftPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.right) {
            this.player.toggleRightPressed();
        }

        if (this.controls.get(e.getKeyCode()) == Key.action) {
            this.player.toggleActionPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Print key released
        System.out.println(e.paramString());

        if (this.controls.get(e.getKeyCode()) == Key.up) {
            this.player.unToggleUpPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.down) {
            this.player.unToggleDownPressed();
        }

        if (this.controls.get(e.getKeyCode()) == Key.left) {
            this.player.unToggleLeftPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.right) {
            this.player.unToggleRightPressed();
        }

        if (this.controls.get(e.getKeyCode()) == Key.action) {
            this.player.unToggleActionPressed();
        }
    }

}
