import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class TankController implements KeyListener {

    private Controllable gameObject;
    private HashMap<Integer, Key> controls;

    public TankController(Controllable obj, HashMap<Integer, Key> controls) {
        this.gameObject = obj;
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
            this.gameObject.toggleUpPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.down) {
            this.gameObject.toggleDownPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.left) {
            this.gameObject.toggleLeftPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.right) {
            this.gameObject.toggleRightPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.action) {
            this.gameObject.toggleActionPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Print key released
        System.out.println(e.paramString());

        if (controls.get(e.getKeyCode()) == Key.up) {
            this.gameObject.unToggleUpPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.down) {
            this.gameObject.unToggleDownPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.left) {
            this.gameObject.unToggleLeftPressed();
        }
        if (controls.get(e.getKeyCode()) == Key.right) {
            this.gameObject.unToggleRightPressed();
        }

        if (controls.get(e.getKeyCode()) == Key.action) {
            this.gameObject.unToggleActionPressed();
        }
    }

}
