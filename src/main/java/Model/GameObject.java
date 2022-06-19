package Model;

import Model.Physics.Body;
import Model.Physics.Collision;

import static Model.GameConstants.*;

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

    public void move(float deltaX, float deltaY) {
        boolean h = false, v = false;
        if (levelContext.limits[LEFT] != null && body.left() + deltaX < levelContext.limits[LEFT]) h = true;
        if (levelContext.limits[RIGHT] != null && body.right() + deltaX > levelContext.limits[RIGHT]) h = true;
        if (levelContext.limits[TOP] != null && body.top() + deltaY < levelContext.limits[TOP]) v = true;
        if (levelContext.limits[BOTTOM] != null && body.bottom() + deltaY > levelContext.limits[BOTTOM]) v = true;
        var collisions = levelContext.getMovementCollisions(body, deltaX, deltaY);
        if (collisions.isEmpty() && !h && !v) {
            body.move(deltaX, deltaY);
            return;
        }
        Collision collision;
        while (!collisions.isEmpty()) {
            collision = collisions.poll();
            if (collision.horizontal()) h = true;
            if (collision.vertical()) v = true;
            onCollide(collision);
        }
        if ((levelContext.limits[LEFT] != null && body.left() + deltaX < levelContext.limits[LEFT])
                || (levelContext.limits[RIGHT] != null && body.right() + deltaX > levelContext.limits[RIGHT]))
            h = true;
        if ((levelContext.limits[TOP] != null && body.top() + deltaY < levelContext.limits[TOP])
                || (levelContext.limits[BOTTOM] != null && body.bottom() + deltaY > levelContext.limits[BOTTOM]))
            v = true;

        if (!h) body.moveX(deltaX);
        if (!v) body.moveY(deltaY);
    }
}
