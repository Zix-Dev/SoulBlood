package Graphics.Sprite;

import java.awt.image.BufferedImage;

public class SpriteSheet extends Sprite {

    private final Sprite[][] sprites;

    public SpriteSheet(BufferedImage bi, int spritesWidth, int spriteHeights) {
        super(bi);
        this.sprites = trim(spritesWidth, spriteHeights);
    }

    public int spriteCount() {
        return sprites.length * sprites[0].length;
    }

    public Sprite[] getSpriteArray() {
        var array = new Sprite[spriteCount()];
        var i = 0;
        for (var row : sprites) for (var sprite : row) {
            array[i] = sprite;
            i++;
        }
        return array;
    }

    public Sprite[][] getSprites() {
        return sprites;
    }

}
