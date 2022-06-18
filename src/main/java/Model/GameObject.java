package Model;

import Model.Physics.Body;
import Model.Physics.Collision;

import static Model.GameConstants.APPROACH;
import static Model.GameConstants.NONE;

public class GameObject {

    public Input input = null;
    public int collisionBehaviour = NONE;
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

    public void onCollide(Collision c) {
        switch (collisionBehaviour) {
            case NONE -> {}
            case APPROACH -> body.approach(c);
        }
    }

    public void moveTo(float nextX, float nextY) {

    }

    public void move(float deltaX, float deltaY) {
        var collisions = levelContext.getMovementCollisions(body, deltaX, deltaY);
        if (collisions.isEmpty()) {
            body.move(deltaX, deltaY);
            return;
        }
        boolean h = true, w = true;
        Collision collision;
        while (!collisions.isEmpty()) {
            collision = collisions.poll();
            if (!collision.horizontal()) h = false;
            if (!collision.vertical()) w = false;
            onCollide(collision);
        }
        if (w) body.moveX(deltaX);
        if (h) body.moveY(deltaY);
    }
}
