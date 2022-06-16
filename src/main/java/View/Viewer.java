package View;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Viewer extends Canvas {

    public void update() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();
        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public BufferStrategy getBufferStrategy() {
        var bufferStrategy = super.getBufferStrategy();
        if (bufferStrategy == null) createBufferStrategy(2);
        return bufferStrategy;
    }

    @Override
    public Graphics getGraphics() {
        try {
            var bs = getBufferStrategy();
            return bs == null ? null : bs.getDrawGraphics();
        } catch (Exception ignored) {
            return null;
        }
    }

    public void draw(BufferedImage img, int x, int y) {
        getGraphics().drawImage(img, x, y, null);
    }
}
