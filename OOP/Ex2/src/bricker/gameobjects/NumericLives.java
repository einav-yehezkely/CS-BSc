package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Class representing the numeric display of lives in the game.
 */
public class NumericLives {
    private final TextRenderable txt;

    /**
     * Constructor for NumericLives display.
     *
     * @param gameObjects      The collection of game objects.
     * @param windowDimensions The dimensions of the game window.
     * @param initialLives     The initial number of lives to display.
     * @param wallThickness    The thickness of the walls (for positioning).
     * @param textDimensions   The dimensions of the text display.
     * @param heartDimensions  The dimensions of the heart icon (for positioning).
     */
    public NumericLives(GameObjectCollection gameObjects,
                        Vector2 windowDimensions,
                        int initialLives,
                        int wallThickness,
                        Vector2 textDimensions,
                        Vector2 heartDimensions) {
        txt = new TextRenderable(("" + initialLives));
        txt.setColor(Color.GREEN);
        GameObject livesText = new GameObject(
                new Vector2(
                        wallThickness * 2,
                        windowDimensions.y() - textDimensions.y() - heartDimensions.y() - 20),
                textDimensions,
                txt
        );
        gameObjects.addGameObject(livesText, Layer.UI);
    }

    /**
     * Updates the displayed number of lives and changes color based on remaining lives.
     *
     * @param remainingLives The current number of remaining lives.
     */
    public void updateLives(int remainingLives) {
        txt.setString("" + remainingLives);
        txt.setString("" + remainingLives);
        if(remainingLives >= 3){
            txt.setColor(Color.GREEN);
        }
        if(remainingLives == 2){
            txt.setColor(Color.YELLOW);
        }
        if(remainingLives == 1){
            txt.setColor(Color.RED);
        }
    }


}
