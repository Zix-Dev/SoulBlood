package Model;

import Model.Physics.Body;

public class GameObject {

    public Input input = null;
    public Body body;
    public int sprite = 7;
    public Level levelContext = null;

    public GameObject(Body body) {
        this.body = body;
    }

    public void update() {
        body.update();
    }

    public static class Input {
        public boolean up = false;
        public boolean down = false;
        public boolean right = false;
        public boolean left = false;
    }
}
