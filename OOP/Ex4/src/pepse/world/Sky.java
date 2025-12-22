package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class representing the sky in the game world.
 */
public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * Creates a sky GameObject that fills the entire window.
     * @param windowDimensions the dimensions of the window
     * @return a GameObject representing the sky
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag("sky");
        return sky;
    }
}
