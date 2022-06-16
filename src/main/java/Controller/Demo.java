package Controller;

import Graphics.ParallaxBackground;
import Graphics.Sprite.SpriteSheet;
import Model.TileMap;
import Util.Json;
import View.Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.Arrays;

import static java.awt.event.KeyEvent.*;
import static java.lang.Thread.sleep;

public class Demo {

    public static JFrame frame = new JFrame();
    public static Viewer viewer = new Viewer();
    public static KeyInput keyInput = new KeyInput();
    static {
        frame.add(viewer);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        viewer.addKeyListener(keyInput);
    }

    public static void main(String[] args) throws Exception {
        frame.setVisible(true);
        var tm = Json.read("src/main/resources/Maps/Test/Test.json", TileMap.class);
        tm.load();
        while (true) {
            viewer.getGraphics().fillRect(0,0, viewer.getWidth(), viewer.getHeight());
            //tm.draw(viewer.getGraphics());
            var tiles = tm.tileSet.tiles;
            for (int i = 0; i < tiles.length; i++) {
                viewer.draw(tiles[i], (i%4)*35+50, (i/4)*35+50);
            }
            viewer.update();
        }
    }

    public static void parallax() throws Exception {
        frame.setVisible(true);
        var s = new SpriteSheet(ImageIO.read(new File("C:/Users/marca/Downloads/Sprite-0001-Sheet.png")),500,500).getSpriteArray();
        var p = new ParallaxBackground(s[0], Arrays.copyOfRange(s, 1, s.length), new float[]{2.5f,2f,1.5f,1f});
        while (true) {
            viewer.getGraphics().fillRect(0,0,viewer.getWidth(),viewer.getHeight());
            viewer.getGraphics().drawImage(p,0,0,null);
            viewer.update();
            var a = keyInput.get(VK_A)? 1f : 0f;
            a += keyInput.get(VK_D)? -1f : 0f;
            var b = keyInput.get(VK_W)? 1f:0f;
            b += keyInput.get(VK_S)?-1f:0f;
            p.setPosition(p.getX()+a, p.getY()+b);
            p.update();
        }
    }

}
