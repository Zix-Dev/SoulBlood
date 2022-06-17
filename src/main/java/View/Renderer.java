package View;

import Model.TileMap;
import Graphics.TileSet;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer extends Canvas {

    public TileMap map;
    public TileSet tileSet;
    private final Camera camera = new Camera(0, 0, 17, 10);
    boolean renderColliders = true;
    boolean renderMapCoordinates = true;
    boolean renderCamera = true;
    private float scale = 1.75f;

    public Renderer(TileMap map, TileSet tileSet) {
        this.map = map;
        this.tileSet = tileSet;
    }

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

    public void render() {
        this.setSize((int) (camera.width * scale * tileSet.size), (int) (camera.height * scale * tileSet.size));
        var g = getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        renderMap(g);
        if (renderMapCoordinates) renderMapCoordinates(g);
        if (renderColliders) renderMapColliders(g);
        if (renderCamera) renderCamera(g);
        g.dispose();
        try {
            getBufferStrategy().show();
        } catch (Exception ignored) {
        }
    }

    private void renderCamera(Graphics2D g) {
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(5));
        var s = scale*tileSet.size;
        g.drawRect(0,0, (int) (camera.width*s)-1, (int) (camera.height*s)-1);
        g.setStroke(new BasicStroke(1));
        g.drawLine((int) (camera.width/2*s+s/7), (int) (camera.height/2*s), (int) (camera.width/2*s-s/7), (int) (camera.height/2*s));
        g.drawLine((int) (camera.width/2*s),(int) (camera.height/2*s+s/7), (int) (camera.width/2*s), (int) (camera.height/2*s-s/7));
    }

    private void renderMap(Graphics2D g) {
        int posX, posY, tile;
        float s = scale * tileSet.size;
        for (int x = (int) (camera.x - camera.width / 2); x < map.width && x < camera.x + camera.width / 2; x++) {
            if (x < 0) continue;
            for (int y = (int) (camera.y - camera.height / 2); y < map.height && y < camera.y + camera.height / 2; y++) {
                if (y < 0) continue;
                for (int z = 0; z < map.layerCount; z++) {
                    tile = map.layers[z][y][x];
                    if (tile < 0) continue;
                    posX = (int) ((x - camera.x + camera.width / 2) * s);
                    posY = (int) ((y - camera.y + camera.height / 2) * s);
                    g.drawImage(tileSet.tiles[tile], posX, posY, (int) s, (int) s, null);
                }
            }
        }
    }

    private void renderMapCoordinates(Graphics2D g) {
        int posX, posY;
        var c = new Color(0,255,0,10);
        float s = scale * tileSet.size;
        for (int x = (int) (camera.x - camera.width / 2); x < map.width && x < camera.x + camera.width / 2; x++) {
            if (x < 0) continue;
            for (int y = (int) (camera.y - camera.height / 2); y < map.height && y < camera.y + camera.height / 2; y++) {
                if (y < 0) continue;
                for (int z = 0; z < map.layerCount; z++) {
                    posX = (int) ((x - camera.x + camera.width / 2) * s);
                    posY = (int) ((y - camera.y + camera.height / 2) * s);
                    g.setColor(c);
                    g.drawRect(posX, posY, (int) s, (int) s);
                    g.setColor(Color.green);
                    g.drawString(x+", "+y, posX+5, posY+14);
                }
            }
        }
    }

    public void renderMapColliders(Graphics2D g) {
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(2));
        var s = scale*tileSet.size;
        for (var c : map.colliders) {
            var posX = (int) ((c.x - camera.x + camera.width / 2) * s);
            var posY = (int) ((c.y - camera.y + camera.height / 2) * s);
            g.fillOval(posX-3, posY-3, 6,6);
            g.drawRect((int) (posX-c.width/2*s), (int) (posY-c.height/2*s), (int) (c.width*s), (int) (c.height*s));
        }
    }

    public void setCameraPosition(float x, float y) {
        this.camera.x = x;
        this.camera.y = y;
    }

    public float[] getCameraPosition() {
        return new float[]{camera.x, camera.y};
    }
}
