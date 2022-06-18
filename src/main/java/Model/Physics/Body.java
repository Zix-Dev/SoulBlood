package Model.Physics;

import java.awt.geom.Rectangle2D;

public class Body {

  private float x0, y0;
  public float width, height;

  public Body(float x, float y, float width, float height) {
    this.width = width;
    this.height = height;
    setX(x);
    setY(y);
  }

  public void update() {
  }

  public float getX() {
    return x0 +width/2;
  }

  public void setX(float x) {
    this.x0 = x - width/2;
  }

  public float getY() {
    return y0 + height/2;
  }

  public void setY(float y) {
    this.y0 = y-height/2;
  }

  public void move(float x, float y) {
    setX(x);
    setY(y);
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public Rectangle2D.Float getRect() {
    return new Rectangle2D.Float(left(), top(), width, height);
  }

  public boolean collides(Body body) {
    return getRect().intersects(body.getRect());
  }

  public float right() {
    return x0 + width;
  }

  public float left() {
    return x0;
  }

  public float top() {
    return y0;
  }

  public float bottom() {
    return y0 +height;
  }
}
