package Model;

import Model.Physics.Body;

import java.util.LinkedList;
import java.util.Queue;

public class Level {

    public TileMap map;
    public final Camera camera;
    private final LinkedList<GameObject> objects = new LinkedList<>();
    private final Queue<GameObject> addQueue = new LinkedList<>();
    private final Queue<GameObject> removeQueue = new LinkedList<>();

    public Level(TileMap map, Camera camera) {
        this.map = map;
        this.camera = camera;
    }

    public void update() {
        for (GameObject o : objects) o.update();
        while (!removeQueue.isEmpty()) objects.remove(removeQueue.poll());
        while (!addQueue.isEmpty()) objects.add(addQueue.poll());
        camera.update();
    }

    public void add(GameObject o) {
        if (o.levelContext != null) return;
        addQueue.add(o);
        o.levelContext = this;
    }

    public void remove(GameObject o) {
        addQueue.remove(o);
        o.levelContext = null;
    }

    public GameObject get(int i) {
        return objects.get(i);
    }

    public GameObject[] getAllObjects() {
        return objects.toArray(new GameObject[0]);
    }

    public Body collides(Body b) {
        for (var o2 : map.colliders) {
            if (b.collides(o2)) return o2;
        }
        for (var o2 : objects) {
            if (b.collides(o2.body)) return o2.body;
        }
        return null;
    }
}
