package Model;

import Graphics.Sprite.Sprite;
import Graphics.TileSet;
import Model.Physics.Body;
import Util.PostLoadable;
import View.Camera;

import java.awt.*;

public class TileMap implements PostLoadable {

    public int[][][] layers;
    public Body[] colliders;
    public transient int layerCount, width, height;

    public TileMap(int[][][] layers, Body[] colliders) {
        this.layers = layers;
        this.colliders = colliders;
    }

    @Override
    public void load() {
        layerCount = layers.length;
        height = layers[0].length;
        width = layers[0][0].length;
    }
}
