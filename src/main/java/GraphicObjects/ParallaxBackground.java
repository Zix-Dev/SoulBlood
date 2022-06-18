package GraphicObjects;

import GraphicObjects.Sprite.Sprite;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class ParallaxBackground extends Sprite {

    private final Sprite bottomLayer;
    private final LinkedList<ParallaxLayer> layers = new LinkedList<>();
    private float x = 0;
    private float y = 0;

    public ParallaxBackground(Sprite bottomLayer, Sprite[] sprites, float[] distances) {
        super(bottomLayer);
        if (sprites.length != distances.length)
            throw new IllegalArgumentException("Sprites and distances arrays must have same length");
        this.bottomLayer = bottomLayer;
        for (int i = 0; i < sprites.length; i++)
            layers.add(new ParallaxLayer(sprites[i], distances[i]));
        layers.sort((a, b) -> (int) (b.distance - a.distance));
        update();
    }

    public void update() {
        var g = getGraphics();
        g.drawImage(bottomLayer, 0, 0, null);
        float xOffset, yOffset;
        for (var layer : layers) {
            xOffset = x / layer.distance % layer.width;
            yOffset = (y < 0) ? 0 : y / layer.distance;
            for (float i = xOffset - layer.width; i < this.width; i += layer.width)
                g.drawImage(layer, (int) i, (int) yOffset, null);
        }
        g.dispose();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        update();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static class ParallaxLayer extends Sprite {

        public float distance;

        public ParallaxLayer(BufferedImage bi, float distance) {
            super(bi);
            this.distance = distance;
        }
    }
}
