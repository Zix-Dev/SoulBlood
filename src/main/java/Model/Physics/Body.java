package Model.Physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Body {

  public float x, y;
  public float width, height;

  public Body(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public void update() {}

  public Rectangle2D.Float getRect() {
    return new Rectangle2D.Float(left(), top(), width, height);
  }

  public boolean collides(Body body) {
    return getRect().intersects(body.getRect());
  }

  public float right() {
    return x + (width / 2f);
  }

  public float left() {
    return x - (width / 2f);
  }

  public float top() {
    return y - (height / 2f);
  }

  public float bottom() {
    return y + (height / 2f);
  }
}
