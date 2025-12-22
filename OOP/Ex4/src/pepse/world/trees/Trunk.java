package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

/**
 * Trunk class representing the trunk of a tree
 */
public class Trunk extends GameObject {
    private static final float WIDTH = 30f;

    private final float height;

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

        this.height = height;
    }

    public float getHeight() {
        return height;
    }
}
