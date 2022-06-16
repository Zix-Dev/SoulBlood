package Model;

import Graphics.TileSet;
import Util.PostLoadable;

import java.awt.*;

public class TileMap implements PostLoadable {

    public int[][][] layers;
    public TileSet tileSet;

    public TileMap(int[][][] layers, TileSet tileSet) {
        this.layers = layers;
        this.tileSet = tileSet;
    }

    public void load() {
        tileSet.load();
    }

    public void draw(Graphics g) {
        int tile;
        for (int z = 0; z < layers.length; z++) {
            for (int y = 0; y < layers[z].length; y++) {
                for (int x = 0; x < layers[z][y].length; x++) {
                    tile = layers[z][y][x];
                    if (tile == -1) continue;
                    g.drawImage(tileSet.tiles[tile], x * tileSet.tileSize, y * tileSet.tileSize, null);
                }
            }
        }
        g.dispose();
    }

}
