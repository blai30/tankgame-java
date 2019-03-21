import GameObjects.Tank;
import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class TankController implements KeyListener {

    private Tank tank;
    private HashMap<Integer, Key> controls;

    public TankController(Tank obj, HashMap<Integer, Key> controls) {
        this.tank = obj;
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Print key pressed
        System.out.println(e.paramString());

        if (controls.get(e.getKeyCode()) == Key.up) {
            this.tank.toggleUpPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.down) {
            this.tank.toggleDownPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.left) {
            this.tank.toggleLeftPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.right) {
            this.tank.toggleRightPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.action) {
            this.tank.toggleActionPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Print key released
        System.out.println(e.paramString());

        if (controls.get(e.getKeyCode()) == Key.up) {
            this.tank.unToggleUpPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.down) {
            this.tank.unToggleDownPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.left) {
            this.tank.unToggleLeftPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.right) {
            this.tank.unToggleRightPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.action) {
            this.tank.unToggleActionPressed();
        }
    }

}
