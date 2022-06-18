package Controller;

import GraphicObjects.ParallaxBackground;
import GraphicObjects.Sprite.Sprite;
import GraphicObjects.Sprite.SpriteSheet;
import GraphicObjects.TileSet;
import Model.Camera;
import Model.GameObject.Input;
import Model.Level;
import Model.Physics.Body;
import Model.Player;
import Model.TileMap;
import Util.Json;
import View.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.awt.event.KeyEvent.*;

/*
 * Todo:
 *  - Check collision on gameobject
 *  - Call other object on collide
 *  -Tileset custom trimmer
 *  -Tileset collision box definition
 *  -Design assets
 */
public abstract class Main {

    public static JFrame window = new JFrame("SoulBlood");
    public static Level testLevel;
    public static Player player;
    public static Camera camera = new Camera(18,10);
    public static Renderer renderer;
    public static KeyInput keyInput = new KeyInput();
    public static Input playerInput = new Input();
    static {
        System.setProperty("sun.awt.noerasebackground", "true");
        TileMap tileMap = Json.read("src/main/resources/Maps/Test/Test.json", TileMap.class);
        testLevel = new Level(tileMap, camera);
        player = new Player(new Body(0.5f, 0.5f, 1, 1));
        player.input = playerInput;
        testLevel.add(player);
        TileSet tileSet = new TileSet("src/main/resources/Assets/TileSets/test.png", 32);
        renderer = new Renderer(testLevel, tileSet, camera);
        renderer.camera.track(player.body);
        renderer.addKeyListener(keyInput);
        Sprite[] parallaxLayers = null;
        try {
            parallaxLayers = new SpriteSheet(ImageIO.read(new File("src/main/resources/Assets/Backgrounds/BlueMountains.png")), 500, 500).getSpriteArray();
        } catch (IOException ignored) {}
        assert parallaxLayers != null;
        //renderer.camera.setLimits(0,tileMap.height, 0, tileMap.width);
        renderer.parallaxBackground = new ParallaxBackground(parallaxLayers[0], Arrays.copyOfRange(parallaxLayers, 1, 5), new float[]{0.7f,0.4f,0.2f,0.1f});
        window.add(renderer);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setVisible(true);
        window.setSize((int) (renderer.camera.width * 58), (int) (renderer.camera.height * 58));
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            playerInput.up = keyInput.get(VK_W);
            playerInput.down = keyInput.get(VK_S);
            playerInput.right = keyInput.get(VK_A);
            playerInput.left = keyInput.get(VK_D);
            testLevel.update();
            renderer.render();
        }
    }
}
