package Model.Physics;

public class Body {

    public float x,y;
    public float width, height;

    public Body(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean collides(Body body) {
        float dx = this.x - body.x;
        if (dx < 0) dx *= -1;
        float dy = this.y - body.y;
        if (dx < 0) dy *= -1;
        float mw = (this.width + body.width)/2;
        float mh = (this.height + body.height)/2;
        return (dx <= mw) && (dy <= mh);
    }
}
