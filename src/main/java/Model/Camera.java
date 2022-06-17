package Model;

public class Camera {

    private float x, y;
    public final float width, height;
    private GameObject target = null;
    private Float minX = null;
    private Float maxX = null;
    private Float minY = null;
    private Float maxY = null;

    public Camera(float width, float height) {
        this.width = width;
        this.height = height;
        this.x = width / 2f;
        this.y = height / 2f;
    }

    public void move(float posX, float posY) {
        if (minX != null && posX < minX) posX = minX;
        else if (maxX != null && posX > maxX) posX = maxX;
        if (minY != null && posY < minY) posY = minY;
        else if (maxY != null && posY > maxY) posY = maxY;
        this.x = posX;
        this.y = posY;
    }

    public void setLimits(float minX, float maxX, float minY, float maxY) {
        if (minX > maxX || minY > maxX) throw new IllegalArgumentException("A min can't be greater then a max");
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public float[] getLimits() {
        return new float[]{minX, maxX, minY, maxY};
    }

    public void track(GameObject o) {
        this.target = o;
    }

    public void stopTracking() {
        this.target = null;
    }

    public GameObject getTrackTarget() {
        return target;
    }

    public float x() {
        var posX = (target == null) ? x : target.body.x;
        if (minX != null && posX < minX) posX = minX;
        else if (maxX != null && posX > maxX) posX = maxX;
        return posX;
    }

    public float y() {
        var posY = (target == null) ? y : target.body.y;
        if (minY != null && posY < minY) posY = minY;
        else if (maxY != null && posY > maxY) posY = maxY;
        return posY;
    }

    public float right() {
        return x() + (width / 2f);
    }

    public float left() {
        return x() - (width / 2f);
    }

    public float top() {
        return y() - (height / 2f);
    }

    public float bottom() {
        return y() + (height / 2f);
    }

}
