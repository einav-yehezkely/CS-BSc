package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


public class GraphicLives {
    private final GameObjectCollection gameObjects;
    private GameObject[] hearts;

    public GraphicLives(GameObjectCollection gameObjects,
                        ImageReader imageReader,
                        Vector2 window_dimensions,
                        int initial_lives,
                        int wall_thickness,
                        Vector2 heart_dimensions) {
        this.gameObjects = gameObjects;
        hearts = new GameObject[initial_lives];
        Renderable heartImage =
                imageReader.readImage("assets/heart.png", true);
        for (int i = 0; i < initial_lives; i++) {
            hearts[i] = new GameObject(
                    new Vector2(
                            (wall_thickness * 2) + (i * (heart_dimensions.x() + 5)),
                            window_dimensions.y() - heart_dimensions.y() - 10),
                    heart_dimensions,
                    heartImage
            );
            gameObjects.addGameObject(hearts[i], Layer.UI);
        }
    }

    public void loseLife(int remainingLives) {
        gameObjects.removeGameObject(hearts[remainingLives], Layer.UI);
    }

}
