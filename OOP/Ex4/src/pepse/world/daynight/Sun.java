package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Terrain;

import java.awt.*;

public class Sun {
    private static final float SUN_RADIUS = 50f;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Vector2 center =
                new Vector2(windowDimensions.x() / 2f,
                        windowDimensions.y() / 3f);
        Vector2 sunCenter = center.subtract(new Vector2(SUN_RADIUS, SUN_RADIUS));

        GameObject sun = new GameObject(
                sunCenter,
                new Vector2(SUN_RADIUS*2, SUN_RADIUS*2),
                new OvalRenderable(Color.YELLOW)
        );

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        Vector2 cycleCenter =
                new Vector2(windowDimensions.x() / 2f,
                        windowDimensions.y() * (2f / 3f));  // TODO: adjust for terrain height

        new Transition<Float>(
                sun,
                (Float angle) -> sun.setCenter(
                        sunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );


        return sun;
    }
}
