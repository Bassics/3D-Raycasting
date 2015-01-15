import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Renderer implements Drawable {
    private RenderWindow window;
    private Player player;
    public Renderer(RenderWindow w, Player p) {
        window = w;
        player = p;
    }
    public void draw(RenderTarget target, RenderStates states) {
        int[][] currentMap = Map.getCurrentMap();
        int w = window.getSize().x;
        int h = window.getSize().y;
        for (int x = 0; x < w; x++) {
            float camDirection = 2 * x / (float)w - 1;
            Vector2f rayPosition = player.getPosition();
            Vector2f rayDirection = new Vector2f(
                player.getDirection().x + player.getPlane().x * camDirection,
                player.getDirection().y + player.getPlane().y * camDirection
            );
            int mapPosition[] = {(int)rayPosition.x, (int)rayPosition.y};
            float sideDistance[] = {0,0};
            Vector2f deltaDistance = new Vector2f(
                    (float)Math.sqrt(1 + Math.pow(rayDirection.y, 2) / Math.pow(rayDirection.x, 2)),
                    (float)Math.sqrt(1 + Math.pow(rayDirection.x, 2) / Math.pow(rayDirection.y, 2))
            );
            float rayLength = 0;
            int[] stepDirection = {0,0};
            boolean hitObject = false;
            int wallSide = 0;
            if (rayDirection.x < 0) {
                stepDirection[0] = -1;
                sideDistance[0] = (rayPosition.x - mapPosition[0]) * deltaDistance.x;
            } else {
                stepDirection[0] = 1;
                sideDistance[0] = (mapPosition[0] + 1 - rayPosition.x) * deltaDistance.x;
            }
            if (rayDirection.y < 0) {
                stepDirection[1] = -1;
                sideDistance[1] = (rayPosition.y - mapPosition[1]) * deltaDistance.y;
            } else {
                stepDirection[1] = 1;
                sideDistance[1] = (mapPosition[1] + 1 - rayPosition.y) * deltaDistance.y;
            }
            while (!hitObject) {
                if (sideDistance[0] < sideDistance[1]) {
                    sideDistance[0] += deltaDistance.x;
                    mapPosition[0] += stepDirection[0];
                    wallSide = 0;
                } else {
                    sideDistance[1] += deltaDistance.y;
                    mapPosition[1] += stepDirection[1];
                    wallSide = 1;
                }
                if (currentMap[mapPosition[0]][mapPosition[1]] > 0)
                    hitObject = true;
            }
            if (wallSide == 0)
                rayLength = Math.abs((mapPosition[0] - rayPosition.x + (1 - stepDirection[0]) / 2) / rayDirection.x);
            else
                rayLength = Math.abs((mapPosition[1] - rayPosition.y + (1 - stepDirection[1]) / 2) / rayDirection.y);
            int lineHeight = Math.abs((int)(h / rayLength));
            float wallX;
            if (wallSide == 1)
                wallX = rayPosition.x + ((mapPosition[1] - rayPosition.y + (1 - stepDirection[1]) / 2) / rayDirection.y) * rayDirection.x;
            else
                wallX = rayPosition.y + ((mapPosition[0] - rayPosition.x + (1 - stepDirection[0]) / 2) / rayDirection.x) * rayDirection.y;
            wallX -= Math.floor(wallX);
            float texX = (float)Math.floor(wallX * lineHeight);
            if (wallSide == 0 && rayDirection.x < 0 || wallSide == 1 && rayDirection.y < 0)
                texX = lineHeight - texX - 1;
            Color wallColor = new Color((int)(255/(1.5 * wallSide)), (int)(255/(1.5 * wallSide)), (int)(255/(1.5 * wallSide)));
            RectangleShape rect = new RectangleShape(new Vector2f(1, lineHeight));
            rect.setFillColor(wallColor);
            rect.setPosition(x, -lineHeight / 2 + h / 2);
            rect.draw(target, states);
        }
    }
}
