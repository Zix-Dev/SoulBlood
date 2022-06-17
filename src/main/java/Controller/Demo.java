package Controller;

import Graphics.ParallaxBackground;
import Graphics.Sprite.SpriteSheet;
import Graphics.TileSet;
import Model.TileMap;
import Util.Json;
import View.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

import static java.awt.event.KeyEvent.*;
import static java.lang.Thread.sleep;

public class Demo {

    public static JFrame frame = new JFrame();
    public static Renderer renderer;
    public static KeyInput keyInput = new KeyInput();
    static {
        System.setProperty("sun.awt.noerasebackground", "true");
        var tm = Json.read("src/main/resources/Maps/Test/Test.json", TileMap.class);
        var ts = new TileSet("src/main/resources/Assets/TileSets/test.png", 32);
        renderer = new Renderer(tm, ts);
        frame.setBackground(Color.BLACK);
        frame.getContentPane().setBackground(Color.BLACK);
        renderer.setBackground(Color.BLACK);
        frame.add(renderer);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        renderer.addKeyListener(keyInput);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            float x = keyInput.get(VK_A)?-0.02f:0;
            x += keyInput.get(VK_D)?0.02f:0;
            float y = keyInput.get(VK_W)?-0.02f:0;
            y += keyInput.get(VK_S)?0.02f:0;
            renderer.setCamX(renderer.getCamX()+x);
            renderer.setCamY(renderer.getCamY()+y);
            renderer.draw();
            sleep(1);
        }
    }

    public static void tilemap() throws Exception{
        while (true) {
            float x = keyInput.get(VK_A)?-0.02f:0;
            x += keyInput.get(VK_D)?0.02f:0;
            float y = keyInput.get(VK_W)?-0.02f:0;
            y += keyInput.get(VK_S)?0.02f:0;
            renderer.setCamX(renderer.getCamX()+x);
            renderer.setCamY(renderer.getCamY()+y);
            renderer.draw();
        }
    }

    public static void parallax() throws Exception {
        var s = new SpriteSheet(ImageIO.read(new File("C:/Users/marca/Downloads/Sprite-0001-Sheet.png")),500,500).getSpriteArray();
        var p = new ParallaxBackground(s[0], Arrays.copyOfRange(s, 1, s.length), new float[]{2.5f,2f,1.5f,1f});
        while (true) {
            renderer.getGraphics().fillRect(0,0, renderer.getWidth(), renderer.getHeight());
            renderer.getGraphics().drawImage(p,0,0,null);
            var a = keyInput.get(VK_A)? 1f : 0f;
            a += keyInput.get(VK_D)? -1f : 0f;
            var b = keyInput.get(VK_W)? 1f:0f;
            b += keyInput.get(VK_S)?-1f:0f;
            p.setPosition(p.getX()+a, p.getY()+b);
            p.update();
        }
    }

}
