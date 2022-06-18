package Model.Physics;

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

  public void moveTo(float x, float y) {
    setX(x);
    setY(y);
  }

  public void move(float deltaX, float deltaY) {
    x0+=deltaX;
    y0+= deltaY;
  }

  public void moveX(float deltaX) {
    x0+=deltaX;
  }

  public void  moveY(float deltaY) {
    y0+=deltaY;
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

  public boolean collides(Body body) {
    if (body == this) return false;
    return (left() < body.right())
            && (body.left() < right())
            && (top() < body.bottom())
            && (body.top() < bottom());
  }

  public Collision getMovementCollision(Body body, float deltaX, float deltaY) {
    boolean collidesVertically, collidesHorizontally;
    Collision collision = null;
    move(deltaX, 0);
    collidesHorizontally = collides(body);
    move(-deltaX, deltaY);
    collidesVertically = collides(body);
    move(0, -deltaY);
    if (collidesVertically || collidesHorizontally) {
      collision = new Collision(body, collidesVertically, collidesHorizontally);
    }
    return collision;
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

  public void setRight(float x) {
    this.x0 = x - width;
  }

  public void setLeft(float x) {
    this.x0 = x;
  }

  public void setTop(float y) {
    this.y0 = y;
  }

  public void setBottom(float y) {
    this.y0 = y - height;
  }

  public void approach(Collision collision) {
    if (collision.horizontal()) {
      if (collision.body().getX() < getX()) setLeft(collision.body().right());
      else setRight(collision.body().left());
    }
    if (collision.vertical()) {
      if (collision.body().getY() < getY()) setTop(collision.body().bottom());
      else setBottom(collision.body().top());
    }
  }

}
