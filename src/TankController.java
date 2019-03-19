import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class TankController implements KeyListener {

    private Tank tank1;
    private HashMap<Integer, Key> controls;

    public TankController(Tank tank, HashMap<Integer, Key> controls) {
        this.tank1 = tank;
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (controls.get(e.getKeyCode()) == Key.up) {
            this.tank1.toggleUpPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.down) {
            this.tank1.toggleDownPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.left) {
            this.tank1.toggleLeftPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.right) {
            this.tank1.toggleRightPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.action) {
            this.tank1.toggleActionPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controls.get(e.getKeyCode()) == Key.up) {
            this.tank1.unToggleUpPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.down) {
            this.tank1.unToggleDownPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.left) {
            this.tank1.unToggleLeftPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.right) {
            this.tank1.unToggleRightPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.action) {
            this.tank1.unToggleActionPressed();
        }
    }

}
