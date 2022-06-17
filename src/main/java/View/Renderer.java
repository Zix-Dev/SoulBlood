package View;

import Model.TileMap;
import Graphics.TileSet;

import java.awt.*;
import java.awt.image.BufferStrategy;

import static java.lang.Thread.sleep;

public class Renderer extends Canvas {

    public TileMap map;
    public TileSet tileSet;
    public float viewportWidth = 17;
    public float viewportHeight = 10;
    private float camX = 0;
    private float camY = 0;
    private boolean showColliders = true;
    private float scaleFactor = 1.75f;

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
    public Graphics getGraphics() {
        try {
            var bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(2);
                bs = getBufferStrategy();
            }
            return bs.getDrawGraphics();
        } catch (Exception ignored) {
            return getGraphics();
        }
    }

    public void draw() {
        this.setSize((int) (viewportWidth*scaleFactor*tileSet.size), (int) (viewportHeight*scaleFactor*tileSet.size));
        var g = getGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,getWidth(),getHeight());
        drawMap(g);
        if (showColliders) drawColliders(g);
        g.dispose();
        try {
            getBufferStrategy().show();
        } catch (Exception ignored) {}
    }

    private void drawColliders(Graphics g) {
        g.setColor(Color.red);
        var s = scaleFactor*tileSet.size;
        if (map.colliders == null) return;
        for (var c : map.colliders) {
            g.drawRect((int) ((c.x-camX)*s), (int) ((c.y-camY)*s), (int) (c.width*s), (int) (c.height*s));
        }
    }

    private void drawMap(Graphics g) {
        int tile, posX, posY;
        int s = (int) (scaleFactor*tileSet.size);
        for (int z = 0; z < map.layerCount; z++) {
            for (int y = (int) camY; y < map.height && y < camY+viewportHeight; y++) {
                for (int x = (int) camX; x < map.width && x < camX+viewportWidth; x++) {
                    tile = map.layers[z][y][x];
                    if (tile == -1) continue;
                    posX = (int) ((x-camX)*s);
                    posY = (int) ((y-camY)*s);
                    g.drawImage(tileSet.tiles[tile], posX, posY,s,s,null);
                }
            }
        }
    }

    public void setCamX(float posX) {
        if (posX < 0) posX = 0;
        if (posX > map.layers[0][0].length - viewportWidth) posX = map.layers[0][0].length - viewportWidth;
        camX = posX;
    }

    public void setCamY(float posY) {
        if (posY < 0) posY = 0;
        if (posY > map.layers[0].length - viewportHeight) posY = map.layers[0].length - viewportHeight;
        camY = posY;
    }

    public float getCamX() {
        return camX;
    }

    public float getCamY() {
        return camY;
    }
}
