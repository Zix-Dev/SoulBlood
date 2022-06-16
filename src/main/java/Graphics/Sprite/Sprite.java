package Graphics.Sprite;

import java.awt.image.BufferedImage;

public class Sprite extends BufferedImage {

    public final int width, height;

    //Constructors

    public Sprite(BufferedImage bi) {
        super(bi.getWidth(), bi.getHeight(), TYPE_INT_ARGB);
        width = bi.getWidth();
        height = bi.getHeight();
        copy(bi);
    }

    //Methods

    public void copy(BufferedImage bi) {
        var g = getGraphics();
        g.drawImage(bi, 0,0,null);
        g.dispose();
    }

    public Sprite[][] trim(int width, int height) {
        int rows = (getHeight() + height - 1) / height;
        int cols = (getWidth() + width - 1) / width;
        var sprites = new Sprite[cols][rows];
        for (int x = 0; x < sprites.length; x++) for (int y = 0; y < sprites[x].length; y++) {
            sprites[x][y] = new Sprite(getSubimage(x*width, y*height, width, height));
        }
        return sprites;
    }

}
