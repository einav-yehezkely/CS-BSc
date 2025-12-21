package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final float HALO_RADIUS = 90f;
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);

    public static GameObject create(GameObject sun){
        Vector2 haloTopLeft =
                sun.getCenter().subtract(new Vector2(HALO_RADIUS, HALO_RADIUS));

        GameObject halo = new GameObject(
                haloTopLeft,
                new Vector2(HALO_RADIUS * 2, HALO_RADIUS * 2),
                new OvalRenderable(HALO_COLOR)
        );

        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        halo.setTag("sunHalo");

        halo.addComponent(deltaTime ->
                halo.setCenter(sun.getCenter())
        );

        return halo;
    }
}
