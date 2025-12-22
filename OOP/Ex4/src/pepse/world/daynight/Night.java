package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class responsible for creating a night overlay that simulates the night time in the game.
 */
public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    /**
     * Creates a night overlay GameObject that covers the entire window.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle.
     * @return A GameObject representing the night overlay.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night =
                new GameObject(Vector2.ZERO, windowDimensions, new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");

        // Create a transition to change the opaqueness of the night overlay
        new Transition<Float>(
                night,
                night.renderer()::setOpaqueness,
                0f,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / 2f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

        return night;
    }
}
