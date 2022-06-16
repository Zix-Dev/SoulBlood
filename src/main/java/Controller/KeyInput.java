package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyInput implements KeyListener {

    private final HashMap<Integer, Boolean> keys = new HashMap<>();

    @Override
    public void keyTyped(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
    }

    public boolean get(int k) {
        var pressed = keys.get(k);
        return pressed != null && pressed;
    }
}
