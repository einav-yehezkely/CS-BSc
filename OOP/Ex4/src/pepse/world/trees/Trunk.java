package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Trunk class representing the trunk of a tree
 */
public class Trunk extends GameObject {
    private static final float WIDTH = 30f;

    /** Constructor for a Trunk object
     * @param groundPosition the position of the ground where the trunk starts
     * @param height the height of the trunk
     */
    public Trunk(Vector2 groundPosition, float height) {
        super(
                new Vector2(
                        groundPosition.x() - WIDTH / 2,
                        groundPosition.y() - height
                ),
                new Vector2(WIDTH, height),
                new RectangleRenderable(new Color(100, 50, 20))
        );

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

    }
}
