package View;

import GraphicObjects.ParallaxBackground;
import GraphicObjects.Sprite.Sprite;
import GraphicObjects.TileSet;
import Model.Camera;
import Model.Level;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Renderer extends Canvas {

    //Attributes

    public Level level;
    public TileSet tileSet;
    public BufferedImage ratesCard = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
    public final Camera camera;
    private boolean renderColliders = true;
    private boolean renderMapCoordinates = true;
    private boolean renderCamera = true;
    private boolean renderRates = true;
    public ParallaxBackground parallaxBackground = null;
    private float scale = 1.75f;
    private Graphics2D g;

    //Constructor

    public Renderer(Level level, TileSet tileSet, Camera camera) {
        this.level = level;
        this.tileSet = tileSet;
        this.camera = camera;
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                adaptScale();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                adaptScale();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                adaptScale();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                adaptScale();
            }
        });
    }

    //Overriden Mehods

    @Override
    public void update(Graphics g) {
        if (isShowing()) paint(g);
    }

    @Override
    public BufferStrategy getBufferStrategy() {
        var bufferStrategy = super.getBufferStrategy();
        if (bufferStrategy == null) createBufferStrategy(2);
        return bufferStrategy;
    }

    @Override
    public Graphics2D getGraphics() {
        try {
            var bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(2);
                bs = getBufferStrategy();
            }
            return (Graphics2D) bs.getDrawGraphics();
        } catch (Exception ignored) {
            return getGraphics();
        }
    }

    private void adaptScale() {
        int w = getWidth(), h = getHeight();
        scale = w / (camera.width * tileSet.size);
    }

    //Render

    public void render() {
        this.setSize((int) (camera.width * scale * tileSet.size), (int) (camera.height * scale * tileSet.size));
        g = getGraphics();
        fill(Color.DARK_GRAY);
        if (parallaxBackground != null) renderBackground();
        renderMap();
        renderObjects();
        if (renderMapCoordinates) renderMapCoordinates();
        if (renderColliders) {
            renderMapColliders();
            renderObjectColliders();
        }
        if (renderCamera) renderCamera();
        if (renderRates && ratesCard != null) renderRates();
        g.dispose();
        try {
            getBufferStrategy().show();
        } catch (Exception ignored) {
        }
    }

    private void renderRates() {
        g.drawImage(ratesCard, 0, 0, null);
    }

    //Render methods

    private void renderBackground() {
        parallaxBackground.setPosition(level.map.width-camera.right(), level.map.height-camera.bottom());
        g.drawImage(parallaxBackground,0,0, (int) relSize(camera.width), (int) relSize(camera.height), null);
    }

    private void renderObjects() {
        for (var o : level.getAllObjects()) {
            drawImage(tileSet.tiles[o.sprite], o.body.getX(), o.body.getY());
        }
    }

    private void renderCamera() {
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(5));
        var s = scale * tileSet.size;
        g.drawRect(0, 0, (int) (camera.width * s) - 1, (int) (camera.height * s) - 1);
        g.setStroke(new BasicStroke(1));
        g.drawLine((int) (camera.width / 2 * s + s / 7), (int) (camera.height / 2 * s), (int) (camera.width / 2 * s - s / 7), (int) (camera.height / 2 * s));
        g.drawLine((int) (camera.width / 2 * s), (int) (camera.height / 2 * s + s / 7), (int) (camera.width / 2 * s), (int) (camera.height / 2 * s - s / 7));
    }

    private void renderMap() {
        int tile;
        for (int x = 0; x < level.map.width; x++) {
            for (int y = 0; y < level.map.height; y++) {
                for (int z = 0; z < level.map.layerCount; z++) {
                    tile = level.map.layers[z][y][x];
                    if (tile < 0) continue;
                    drawTile(tile, x, y);
                }
            }
        }
    }

    private void renderMapCoordinates() {
        g.setColor(new Color(50, 110, 50));
        for (int x = 0; x < level.map.width; x++) {
            drawLine(x, 0, x, level.map.height);
            for (int y = 0; y < level.map.height; y++) {
                g.drawString(x + ", " + y, relX(x) + scale * 2, relY(y) + scale * 8);
            }
        }
        for (int x = 0; x < level.map.width + 1; x++) drawLine(x, 0, x, level.map.height);
        for (int y = 0; y < level.map.height + 1; y++) drawLine(0, y, level.map.width, y);
    }

    public void renderObjectColliders() {
        g.setColor(Color.magenta);
        for (var o : level.getAllObjects()) {
            drawBox(o.body.getX(), o.body.getY(), o.body.width, o.body.height);
        }
    }

    public void renderMapColliders() {
        g.setColor(Color.red);
        for (var c : level.map.colliders) {
            drawBox(c.getX(), c.getY(), c.width, c.height);
        }
    }

    //Draw(relative to camera) methods

    public void fill(Color c) {
        g.setColor(c);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void drawImage(Sprite sprite, float x, float y) {
        g.drawImage(sprite, (int) (relX(x) - relSize(0.5f)), (int) (relY(y) - relSize(0.5f)), (int) (sprite.width * scale), (int) (sprite.height * scale), null);
    }

    public void drawTile(int tile, float x, float y) {
        g.drawImage(tileSet.tiles[tile], (int) relX(x), (int) relY(y), (int) (relSize(1)), (int) (relSize(1)), null);
    }

    public void drawRect(float x, float y, float w, float h) {
        g.drawRect((int) (relX(x) - relSize(w) / 2f), (int) (relY(y) - relSize(h) / 2f), (int) relSize(w), (int) relSize(h));
    }

    public void drawLine(float x, float y, float x2, float y2) {
        g.drawLine((int) relX(x), (int) relY(y), (int) relX(x2), (int) relY(y2));
    }

    public void drawBox(float x, float y, float w, float h) {
        drawRect(x, y, w, h);
        drawLine(x - w / 2f, y - h / 2f, x + w / 2f, y + h / 2f);
        drawLine(x - w / 2f, y + h / 2f, x + w / 2f, y - h / 2f);
    }

    //Relative units parsers

    private float relX(float x) {
        return ((x - camera.left()) * scale * tileSet.size);
    }

    private float relY(float y) {
        return ((y - camera.top()) * scale * tileSet.size);
    }

    private float relSize(float w) {
        return w * scale * tileSet.size;
    }

}
