package Controller;

import Graphics.TileSet;
import Model.GameObject;
import Model.GameObject.Input;
import Model.Level;
import Model.Physics.Body;
import Model.Player;
import Model.TileMap;
import Util.Json;
import View.Renderer;

import javax.swing.*;
import java.awt.*;

import static java.awt.event.KeyEvent.*;
import static java.lang.Thread.sleep;

/*
 * Todo:
 *  -Body Collisions and shapes --> #Test it
 *  -Tileset custom trimmer
 *  -Tileset collision box definition
 *  -Implement with parallax
 *  -Design assets
 *  -Try postprocessing with not static blocks
 *  -g.equals(getGraphics());
 */
public abstract class Main {

    public static JFrame window = new JFrame("SoulBlood");
    public static Level testLevel;
    public static Player player;
    public static Renderer renderer;
    public static KeyInput keyInput = new KeyInput();
    public static Input playerInput = new Input();
    static {
        System.setProperty("sun.awt.noerasebackground", "true");
        TileMap tileMap = Json.read("src/main/resources/Maps/Test/Test.json", TileMap.class);
        testLevel = new Level(tileMap);
        player = new Player(new Body(0.5f, 0.5f, 1, 1));
        player.input = playerInput;
        testLevel.add(player);
        TileSet tileSet = new TileSet("src/main/resources/Assets/TileSets/test.png", 32);
        renderer = new Renderer(testLevel, tileSet);
        renderer.camera.track(player);
        renderer.addKeyListener(keyInput);
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
