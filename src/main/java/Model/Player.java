package Model;

import static Model.Physics.PhysicsConstants.SPEED;

public class Player extends GameObject {

    public Player(Model.Physics.Body body) {
        super(body);
    }

    @Override
    public void update() {
        super.update();
        if (input != null) {
            float x, y, deltaX, deltaY;
            x = body.getX();
            y = body.getY();
            deltaY = input.up ? -SPEED : 0;
            deltaY += input.down ? SPEED : 0;
            deltaX = input.right ? -SPEED : 0;
            deltaX += input.left ? SPEED : 0;
            sprite = x < body.getX() ? 6 : (x > body.getX()) ? 7 : sprite;
            body.move(x+deltaX, y+deltaY);
            var otherBody = levelContext.collides(this.body);
            if (otherBody != null) body.move(x,y);
        }
    }

}
