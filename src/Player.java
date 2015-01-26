import org.jsfml.system.Vector2f;

public class Player {
    /* Current player position */
    private Vector2f position = new Vector2f(0,0);
    /* Current camera direction */
    private Vector2f direction = new Vector2f(0,0);
    /* Current camera plane */
    private Vector2f plane = new Vector2f(0,0);
    /* How much the camera is tilted on the y-axis */
    private float yaw = 0;
    /* How fast the player moves */
    private float moveSpeed = 0;
    /* How fast the camera moves */
    private float rotSpeed = 0;
    private float forwardDir = 0;
    private float sidewaysDir = 0;
    public Player() {}
    public void moveForward(float dir) {
        /* If the position isn't occupied, set the player's position by direction */
        float posX = position.x;
        float posY = position.y;
        if (Map.getInt((int)(position.x + direction.x * moveSpeed * dir), (int)position.y) == 0)
            posX = position.x + direction.x * moveSpeed * dir;
        if (Map.getInt((int)position.x, (int)(position.y + direction.y * moveSpeed * dir)) == 0)
            posY = position.y + direction.y * moveSpeed * dir;
        position = new Vector2f(posX, posY);
    }
    public void moveSideways(float dir) {
        /* The same as moveForward, but from side to side */
        Vector2f newDirection = new Vector2f(
                (float)(direction.x * Math.cos(Math.PI/2) - direction.y * Math.sin(Math.PI/2)),
                (float)(direction.x * Math.sin(Math.PI/2) + direction.y * Math.cos(Math.PI/2))
        );
        float posX = position.x;
        float posY = position.y;
        if (Map.getInt((int)(position.x + newDirection.x * moveSpeed * dir), (int)position.y) == 0)
            posX = position.x + newDirection.x * moveSpeed * dir;
        if (Map.getInt((int)position.x, (int)(position.y + newDirection.y * moveSpeed * dir)) == 0)
            posY = position.y + newDirection.y * moveSpeed * dir;
        position = new Vector2f(posX, posY);
    }
    public void rotateCamera(float dir) {
        /* Rotate the camera by the pitch */
        direction = new Vector2f(
                (float)(direction.x * Math.cos(rotSpeed * dir) - direction.y * Math.sin(rotSpeed * dir)),
                (float)(direction.x * Math.sin(rotSpeed * dir) + direction.y * Math.cos(rotSpeed * dir))
        );
        plane = new Vector2f(
                (float)(plane.x * Math.cos(rotSpeed * dir) - plane.y * Math.sin(rotSpeed * dir)),
                (float)(plane.x * Math.sin(rotSpeed * dir) + plane.y * Math.cos(rotSpeed * dir))
        );
    }
    public void tiltCamera(float dir) {
        /* Rotate the camera by the yaw */
        if (Math.abs(yaw + (rotSpeed * dir)) < 1.5)
            yaw += rotSpeed * dir;
    }
    public void setPosition(Vector2f p) {
        position = p;
    }
    public void setDirection(Vector2f d) {
        direction = d;
    }
    public void setPlane(Vector2f p) {
        plane = p;
    }
    public void setMoveSpeed(float s) {
        moveSpeed = s;
    }
    public void setRotSpeed(float s) { rotSpeed = s; }
    public Vector2f getPosition() {
        return position;
    }
    public Vector2f getDirection() {
        return direction;
    }
    public Vector2f getPlane() {
        return plane;
    }
    public float getYaw() {
        return yaw;
    }

    public void setSidewaysDir(float dir) {
        sidewaysDir = dir;
    }

    public void setForwardDir(float dir) {
        forwardDir = dir;
    }

    public float getSidewaysDir() { return sidewaysDir; }

    public float getForwardDir() { return forwardDir; }

    public void update() {
        moveForward(forwardDir);
        moveSideways(sidewaysDir);
    }
}
