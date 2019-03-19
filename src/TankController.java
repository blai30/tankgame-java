import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class TankController implements KeyListener {

    private Tank tank1;
    private final HashMap<Integer, Key> controls;

    public TankController(Tank tank, HashMap<Integer, Key> controls) {
        this.tank1 = tank;
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
