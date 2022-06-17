package Model;

import Model.Physics.Body;
import Util.PostLoadable;

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
