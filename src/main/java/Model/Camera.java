package Model;

import Model.Physics.Body;

import static Model.GameConstants.*;

public class Camera {

    private float x0, y0;
    public final float width, height;
    private Body target = null;
    private final Float[] limits = new Float[4]; //Top, bottom, left, right

    public Camera(float width, float height) {
        this.width = width;
        this.height = height;
        this.x0 = 0;
        this.y0 = 0;
    }

    public void track(Body b) {
        this.target = b;
    }

    public void stopTracking() {
        this.target = null;
    }

    public Body getTrackTarget() {
        return target;
    }

    public float x() {
        return x0 + width/2f;
    }

    public float y() {
        return y0 + height / 2f;
    }

    public float top() {return y0;}
    public float bottom() {return y0 +height;}
    public float left() {return x0;}
    public float right() {return x0 + width;}

    public void setLimits(float top, float bottom, float left, float right) {
        if (top+height > bottom || left + width > right)
            throw new IllegalArgumentException("Camera size have no space");
        limits[TOP] = top;
        limits[BOTTOM]= bottom;
        limits[LEFT] = left;
        limits[RIGHT] = right;
    }

    public Float[] getLimits() {
        return limits;
    }

    public void moveTo(float x, float y)  {
        x -= width/2;
        y -= height/2;
        if (limits[LEFT] != null && x < limits[LEFT]) x = limits[LEFT];
        if (limits[RIGHT] != null && x + width > limits[RIGHT]) x = limits[RIGHT]-width;
        if (limits[TOP] != null && y < limits[TOP]) y = limits[TOP];
        if (limits[BOTTOM] != null && y + height > limits[BOTTOM]) y = limits[BOTTOM] - height;
        this.x0 = x;
        this.y0 = y;
    }

    public void update() {
         if (target != null) moveTo(target.getX(), target.getY());
    }
}
