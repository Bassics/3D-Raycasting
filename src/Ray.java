import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.Arrays;

public class Ray {
    /* Starting position of the Ray */
    private Vector2f position;
    /* Direction of the Ray */
    private Vector2f direction;
    /* Length of the ray */
    private float rayLength = 0;
    /* The ratio of where on the object the ray hit */
    private float hitPosition = 0;
    /* Did the ray hit the north/south or the east/west side? */
    private int wallSide = 0;
    /* The wall type of the hit object */
    private int objInt = 0;
    /* The position of the object */
    private int[] mapPosition = {0, 0};
    private Vector2f exactHit;

    public Ray(Vector2f p, Vector2f d) {
        position = p;
        direction = d;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getDirection() {
        return direction;
    }

    public float getLength() {
        return rayLength;
    }

    public float getHitPosition() {
        return hitPosition;
    }

    public int getSide() {
        return wallSide;
    }

    public int getInt() {
        return objInt;
    }

    public int[] getMapPos() {
        return mapPosition;
    }

    public Vector2f getExactHit() {
        return exactHit;
    }

    public boolean calculateRay() {
        /* Whether or not the ray hit something */
        boolean hitObject = false;
        /* The grid-bound position of the ray */
        mapPosition[0] = (int) position.x;
        mapPosition[1] = (int) position.y;
        /* What direction to take on the current ray step [-1 | 1] */
        Vector2i stepDirection = new Vector2i(
                (int) (direction.x / Math.abs(direction.x)),
                (int) (direction.y / Math.abs(direction.y))
        );
        /* Distance from a certain x/y location to a next x/y location (DDA algorithm) */
        Vector2f deltaDistance = new Vector2f(
                (float) Math.sqrt(1 + Math.pow(direction.y, 2) / Math.pow(direction.x, 2)),
                (float) Math.sqrt(1 + Math.pow(direction.x, 2) / Math.pow(direction.y, 2))
        );
        /* Distance from the current x/y location to the next available x/y location (DDA algorithm) */
        float sideDistance[] = {
                direction.x < 0 ?
                        (position.x - mapPosition[0]) * deltaDistance.x :
                        (mapPosition[0] + 1 - position.x) * deltaDistance.x,
                direction.y < 0 ?
                        (position.y - mapPosition[1]) * deltaDistance.y :
                        (mapPosition[1] + 1 - position.y) * deltaDistance.y
        };
        /* Loop until ray hits something */
        while (!hitObject) {
            /* Depending on which is closer (x or y) move the ray in that direction */
            if (sideDistance[0] < sideDistance[1]) {
                /* The wall is north, south */
                wallSide = 0;
                sideDistance[0] += deltaDistance.x;
                mapPosition[0] += stepDirection.x;
            } else {
                /* The wall is east, west */
                wallSide = 1;
                sideDistance[1] += deltaDistance.y;
                mapPosition[1] += stepDirection.y;
            }
            /* Get the current space type */
            objInt = Map.getInt(mapPosition[0], mapPosition[1]);
            /* If the space isn't empty then it hit something */
            if (objInt > 0)
                hitObject = true;
        }
        /* Calculate the ray's length */
        if (wallSide == 0)
            rayLength = Math.abs((mapPosition[0] - position.x + (1 - stepDirection.x) / 2) / direction.x);
        else
            rayLength = Math.abs((mapPosition[1] - position.y + (1 - stepDirection.y) / 2) / direction.y);
        /* Calculate where EXACTLY on the wall, the ray hit */
        exactHit = new Vector2f(
                position.x + ((mapPosition[1] - position.y + (1 - stepDirection.y) / 2) / direction.y) * direction.x,
                position.y + ((mapPosition[0] - position.x + (1 - stepDirection.x) / 2) / direction.x) * direction.y
        );
        if (wallSide == 1)
            hitPosition = exactHit.x;
        else
            hitPosition = exactHit.y;
        hitPosition -= Math.floor(hitPosition);
        return hitObject;
    }
}
