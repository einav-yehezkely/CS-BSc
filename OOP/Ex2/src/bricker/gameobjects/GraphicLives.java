package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


public class GraphicLives {
    private GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final int wallThickness;
    private final Vector2 heartDimensions;
    private GameObject[] hearts;
    private final Renderable heartImage;

    public GraphicLives(GameObjectCollection gameObjects,
                        ImageReader imageReader,
                        Vector2 windowDimensions,
                        int initialLives,
                        int wallThickness,
                        Vector2 heartDimensions,
                        int maxLives) {
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.wallThickness = wallThickness;
        this.heartDimensions = heartDimensions;
        hearts = new GameObject[maxLives];
        this.heartImage =
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

    public void gainLife(int lives){

        if (hearts[lives-1] != null) {
            // the heart object already exists, just put it back on screen
            gameObjects.addGameObject(hearts[lives-1], Layer.UI);
            return;
        }
        hearts[lives-1] = new GameObject(
                new Vector2(
                        (wallThickness * 2) + ((lives-1) * (heartDimensions.x() + 5)),
                        windowDimensions.y() - heartDimensions.y() - 10),
                heartDimensions,
                heartImage
        );
        gameObjects.addGameObject(hearts[lives-1], Layer.UI);
    }

}
