import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Ray {
    private Vector2f position;
    private Vector2f direction;
    private float rayLength = 0;
    private float hitPosition = 0;
    private int wallSide = 0;
    private int objInt = 0;
    private int[] mapPosition = {0, 0};

    public Ray(Vector2f p, Vector2f d) {
        position = p;
        direction = d;
    }

    public Vector2f getPosition() {
        return position;
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

    public boolean calculateRay() {
        boolean hitObject = false;
        mapPosition[0] = (int) position.x;
        mapPosition[1] = (int) position.y;
        Vector2i stepDirection = new Vector2i(
                (int) (direction.x / Math.abs(direction.x)),
                (int) (direction.y / Math.abs(direction.y))
        );
        Vector2f deltaDistance = new Vector2f(
                (float) Math.sqrt(1 + Math.pow(direction.y, 2) / Math.pow(direction.x, 2)),
                (float) Math.sqrt(1 + Math.pow(direction.x, 2) / Math.pow(direction.y, 2))
        );
        float sideDistance[] = {
                direction.x < 0 ?
                        (position.x - mapPosition[0]) * deltaDistance.x :
                        (mapPosition[0] + 1 - position.x) * deltaDistance.x,
                direction.y < 0 ?
                        (position.y - mapPosition[1]) * deltaDistance.y :
                        (mapPosition[1] + 1 - position.y) * deltaDistance.y
        };
        while (!hitObject) {
            if (sideDistance[0] < sideDistance[1]) {
                wallSide = 0;
                sideDistance[0] += deltaDistance.x;
                mapPosition[0] += stepDirection.x;
            } else {
                wallSide = 1;
                sideDistance[1] += deltaDistance.y;
                mapPosition[1] += stepDirection.y;
            }
            objInt = Map.getInt(mapPosition[0], mapPosition[1]);
            if (objInt > 0)
                hitObject = true;
        }
        if (wallSide == 0)
            rayLength = Math.abs((mapPosition[0] - position.x + (1 - stepDirection.x) / 2) / direction.x);
        else
            rayLength = Math.abs((mapPosition[1] - position.y + (1 - stepDirection.y) / 2) / direction.y);
        if (wallSide == 1)
            hitPosition = position.x + ((mapPosition[1] - position.y + (1 - stepDirection.y) / 2) / direction.y) * direction.x;
        else
            hitPosition = position.y + ((mapPosition[0] - position.x + (1 - stepDirection.x) / 2) / direction.x) * direction.y;
        hitPosition -= Math.floor(hitPosition);
        return hitObject;
    }
}
