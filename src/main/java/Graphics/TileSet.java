package Graphics;

import Graphics.Sprite.Sprite;
import Graphics.Sprite.SpriteSheet;
import Util.PostLoadable;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TileSet implements PostLoadable {

    public transient Sprite[] tiles;
    public String path;
    public int tileSize;

    public TileSet(String path, int tileSize) {
        this.path = path;
        this.tileSize = tileSize;
    }

    public void load() {
        try {
            var img = ImageIO.read(new File(path));
            this.tiles = new SpriteSheet(img, tileSize, tileSize).getSpriteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
