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
            x = body.getX();
            y = body.getY();
            deltaY = input.up ? -0.1f : 0;
            deltaY += input.down ? 0.1f : 0;
            deltaX = input.right ? -0.1f : 0;
            deltaX += input.left ? 0.1f : 0;
            body.move(x+deltaX, y+deltaY);
            if (levelContext.collides(this)) {
                body.setX(x);
                body.setY(y);
            }
        }
    }

}
