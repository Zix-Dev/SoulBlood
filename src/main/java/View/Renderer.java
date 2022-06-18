package View;

import Graphics.Sprite.Sprite;
import Graphics.ParallaxBackground;
import Model.Camera;
import Model.Level;
import Graphics.TileSet;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer extends Canvas {

    //Attributes

    public Level level;
    public TileSet tileSet;
    public final Camera camera = new Camera(17, 10);
    private boolean renderColliders = false;
    private boolean renderMapCoordinates = false;
    private boolean renderCamera = false;
    public ParallaxBackground parallaxBackground = null;
    private float scale = 1.75f;
    private Graphics2D g;

    //Constructor

    public Renderer(Level level, TileSet tileSet) {
        this.level = level;
        this.tileSet = tileSet;
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
        g.dispose();
        try {
            getBufferStrategy().show();
        } catch (Exception ignored) {
        }
    }

    //Render methods

    private void renderBackground() {
        parallaxBackground.setPosition(camera.x(), camera.y());
        g.drawImage(parallaxBackground,0,0, (int) relSize(camera.width), (int) relSize(camera.height), null);
    }

    private void renderObjects() {
        for (var o : level.getAllObjects()) {
            drawImage(tileSet.tiles[6], o.body.x, o.body.y);
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
            drawBox(o.body.x, o.body.y, o.body.width, o.body.height);
        }
    }

    public void renderMapColliders() {
        g.setColor(Color.red);
        for (var c : level.map.colliders) {
            drawBox(c.x, c.y, c.width, c.height);
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
        g.drawImage(tileSet.tiles[tile], (int) relX(x), (int) relY(y), (int) (tileSet.size * scale), (int) (tileSet.size * scale), null);
    }

    public void drawRect(float x, float y, float w, float h) {
        g.drawRect((int) (relX(x) - relSize(w) / 2f), (int) (relY(y) - relSize(h) / 2f), (int) relSize(w), (int) relSize(h));
    }

    public void drawLine(float x, float y, float x2, float y2) {
        g.drawLine((int) relX(x), (int) relY(y), (int) relX(x2), (int) relY(y2));
    }

    public void drawBox(float x, float y, float w, float h) {
        g.setStroke(new BasicStroke(2));
        drawRect(x, y, w, h);
        g.setStroke(new BasicStroke(1));
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
