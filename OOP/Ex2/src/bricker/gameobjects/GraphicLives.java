package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


public class GraphicLives {
    private GameObjectCollection gameObjects;
    private GameObject[] hearts;

    public GraphicLives(GameObjectCollection gameObjects,
                        ImageReader imageReader,
                        Vector2 windowDimensions,
                        int initialLives,
                        int wallThickness,
                        Vector2 heartDimensions) {
        this.gameObjects = gameObjects;
        hearts = new GameObject[initialLives];
        Renderable heartImage =
                imageReader.readImage("assets/heart.png", true);
        for (int i = 0; i < initialLives; i++) {
            hearts[i] = new GameObject(
                    new Vector2(
                            (wallThickness * 2) + (i * (heartDimensions.x() + 5)),
                            windowDimensions.y() - heartDimensions.y() - 10),
                    heartDimensions,
                    heartImage
            );
            gameObjects.addGameObject(hearts[i], Layer.UI);
        }
    }

    public void loseLife(int remainingLives) {
        gameObjects.removeGameObject(hearts[remainingLives], Layer.UI);
    }

}
