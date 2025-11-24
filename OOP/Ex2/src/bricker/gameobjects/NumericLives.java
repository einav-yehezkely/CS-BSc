package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLives {
    private TextRenderable txt;

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
