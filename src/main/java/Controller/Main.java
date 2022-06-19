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
 *  - Check collision on game object
 *  - Call other object on collide
 *  -Tileset custom trimmer
 *  -Tileset collision box definition
 *  -Calculate viewport scale
 *  -Debug keys and fullscreen
 */
public abstract class Main {

    public static JFrame window = new JFrame("SoulBlood");
    public static Level testLevel;
    public static Player player;
    public static Camera camera = new Camera(18, 10);
    public static Renderer renderer;
    public static KeyInput keyInput = new KeyInput();
    public static Input playerInput = new Input();

    static {
        System.setProperty("sun.awt.noerasebackground", "true");
        TileMap tileMap = Json.read("src/main/resources/Maps/Test/Test.json", TileMap.class);
        testLevel = new Level(tileMap, camera);
        testLevel.setLimits(-5, tileMap.width + 5, -5, tileMap.height + 5);
        player = new Player(new Body(21.5f, 21.5f, 0.8f, 0.8f));
        player.input = playerInput;
        testLevel.add(player);
        TileSet tileSet = new TileSet("src/main/resources/Assets/TileSets/test.png", 32);
        renderer = new Renderer(testLevel, tileSet, camera);
        renderer.camera.track(player.body);
        renderer.addKeyListener(keyInput);
        Sprite[] parallaxLayers = null;
        try {
            parallaxLayers = new SpriteSheet(ImageIO.read(new File("src/main/resources/Assets/Backgrounds/SunsetMountains.png")), 500, 500).getSpriteArray();
        } catch (IOException ignored) {
        }
        assert parallaxLayers != null;
        renderer.camera.setLimits(0, tileMap.height, 0, tileMap.width);
        renderer.parallaxBackground = new ParallaxBackground(
                parallaxLayers[0].resize(900, 500),
                Arrays.copyOfRange(parallaxLayers, 1, 6),
                new float[]{1.1f, 0.7f, 0.4f, 0.2f, 0.1f});
        window.add(renderer);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setSize(1300, 700);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Press 1 to show FPS and UPS");
        System.out.println("Press 2 to show hit boxes");
        System.out.println("Press 3 to show coordinates");
        System.out.println("Press 4 to show camera");
        long lastCheck = 0;
        int ups = 0, fps = 0;
        renderer.requestFocus();
        while (true) {
            playerInput.up = keyInput.get(VK_W);
            playerInput.down = keyInput.get(VK_S);
            playerInput.right = keyInput.get(VK_A);
            playerInput.left = keyInput.get(VK_D);
            testLevel.update();
            ups++;
            renderer.render();
            fps++;
            if (System.currentTimeMillis() - lastCheck > 1000) {
                var g = (Graphics2D) renderer.ratesCard.getGraphics();
                g.setBackground(new Color(255, 255, 255, 0));
                g.clearRect(0, 0, renderer.ratesCard.getWidth(), renderer.ratesCard.getHeight());
                g.setColor(new Color(0,0,0,100));
                g.fillRect(0,0 ,80,45);
                g.setColor(Color.WHITE);
                g.drawString("UPS: "+ups, 10,15);
                g.drawString("FPS: " + fps, 10, 35);
                g.dispose();
                ups = 0; fps = 0;
                lastCheck = System.currentTimeMillis();
                if (keyInput.get(VK_1)) renderer.renderRates = !renderer.renderRates;
                if (keyInput.get(VK_2)) renderer.renderColliders = !renderer.renderColliders;
                if (keyInput.get(VK_3)) renderer.renderMapCoordinates = !renderer.renderMapCoordinates;
                if (keyInput.get(VK_4)) renderer.renderCamera = !renderer.renderCamera;
            }

        }
    }
}
