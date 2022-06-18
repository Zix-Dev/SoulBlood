package Model;

public class Player extends GameObject {

    public Player(Model.Physics.Body body) {
        super(body);
    }

    @Override
    public void update() {
        super.update();
        if (input != null) {
            float x, y, deltaX, deltaY;
            x = body.x;
            y = body.y;
            deltaY = input.up ? -0.1f : 0;
            deltaY += input.down ? 0.1f : 0;
            deltaX = input.right ? -0.1f : 0;
            deltaX += input.left ? 0.1f : 0;
            body.x += deltaX;
            body.y += deltaY;
            if (levelContext.collides(this)) {
                body.x = x;
                body.y = y;
            }
        }
    }

}
