package troisstudentsbjjm.theshapeofus;

/**
 * Created by Braedon Jolie on 2017-05-19.
 */

public class RectHitbox {
    float top;
    float left;
    float bottom;
    float right;
    float height;

    boolean intersects(RectHitbox rectHitBox) {
        boolean hit = false;

        if(this.right > rectHitBox.left && this.left < rectHitBox.right) {
            // Intersecting on the x-axis

            if(this.top < rectHitBox.bottom && this.bottom > rectHitBox.top) {
                // Intersecting on y as well

                // Collision
                hit = true;
            }
        }

        return hit;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
