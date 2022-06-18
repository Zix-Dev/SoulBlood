package GraphicObjects.Sprite;

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
        int rows = (this.height + height - 1) / height;
        int cols = (this.width + width - 1) / width;
        var sprites = new Sprite[rows][cols];
        var a = new int[rows][cols];
        int i = 1;
        for (int x = 0; x < rows; x++)
            for (int y = 0; y < cols; y++) {
                sprites[x][y] = new Sprite(getSubimage(y*width,x*height,width,height));
                a[x][y] = i;
                i++;
            }
        return sprites;
    }

}
