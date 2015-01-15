import org.jsfml.system.Vector2f;

public class Player {
    private Vector2f position = new Vector2f(0,0);
    private Vector2f direction = new Vector2f(0,0);
    private Vector2f plane = new Vector2f(0,0);
    private float moveSpeed = 0;
    private float rotSpeed = 0;
    public Player() {}
    public void movePlayer(float dir) {
        int[][] currentMap = Map.getCurrentMap();
        float posX = position.x;
        float posY = position.y;
        if (currentMap[(int)(position.x + direction.x * moveSpeed * dir)][(int)position.y] == 0)
            posX = position.x + direction.x * moveSpeed * dir;
        if (currentMap[(int)position.x][(int)(position.y + direction.y * moveSpeed * dir)] == 0)
            posY = position.y + direction.y * moveSpeed * dir;
        position = new Vector2f(posX, posY);
    }
    public void rotateCamera(float dir) {
        direction = new Vector2f(
                (float)(direction.x * Math.cos(rotSpeed * dir) - direction.y * Math.sin(rotSpeed * dir)),
                (float)(direction.x * Math.sin(rotSpeed * dir) + direction.y * Math.cos(rotSpeed * dir))
        );
        plane = new Vector2f(
                (float)(plane.x * Math.cos(rotSpeed * dir) - plane.y * Math.sin(rotSpeed * dir)),
                (float)(plane.x * Math.sin(rotSpeed * dir) + plane.y * Math.cos(rotSpeed * dir))
        );
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
}