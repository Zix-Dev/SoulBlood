package Model;

import static Model.GameConstants.APPROACH;
import static Model.Physics.PhysicsConstants.SPEED;

public class Player extends GameObject {

    public Player(Model.Physics.Body body) {
        super(body);
        collisionBehaviour = APPROACH;
    }

    @Override
    public void update() {
        super.update();
        if (input != null) {
            float deltaX, deltaY;
            deltaY = input.up ? -SPEED : 0;
            deltaY += input.down ? SPEED : 0;
            deltaX = input.right ? -SPEED : 0;
            deltaX += input.left ? SPEED : 0;
            sprite = deltaX < 0 ? 6 : (deltaX > 0) ? 7 : sprite;
            move(deltaX, deltaY);
        }
    }

}
