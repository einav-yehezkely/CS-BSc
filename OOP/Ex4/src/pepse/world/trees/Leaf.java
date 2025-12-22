package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pepse.util.ColorSupplier;

/**
 * Leaf class representing leaves of a tree
 */
public class Leaf extends GameObject {
    private static final float SIZE = 20f;
    private static final int MAX_LEAVES = 100;
    private static final int MIN_LEAVES = 50;
    private static final float CANOPY_RADIUS = 80f;


    /**
     * Constructor for a Leaf object
     * @param topLeft the top-left corner of the leaf
     */
    public Leaf(Vector2 topLeft) {
        super(topLeft,
                new Vector2(SIZE, SIZE),
                new RectangleRenderable(
                        ColorSupplier.approximateColor(new Color(50, 200, 30)))
        );
    }

    /**
     * Create leaves around the top of the trunk
     * @param trunk the trunk to create leaves around
     * @param random random number generator
     * @return list of leaves
     */
    public static List<GameObject> createLeaves(Trunk trunk, Random random) {
        List<GameObject> leaves = new ArrayList<>();

        Vector2 trunkTopCenter = new Vector2(
                trunk.getTopLeftCorner().x() + trunk.getDimensions().x() / 2,
                trunk.getTopLeftCorner().y()
        );

        for (int i = 0; i < MIN_LEAVES; i++) {
            leaves.add(randomLeaf(trunkTopCenter, random));
        }

        int numOfLeaves = MIN_LEAVES;

        while (random.nextFloat() < 0.6f && numOfLeaves < MAX_LEAVES) { // מטבע מוטה
            leaves.add(randomLeaf(trunkTopCenter, random));
            numOfLeaves++;
        }

        return leaves;
    }

    /**
     * Generate a random leaf around the given center within the canopy radius
     * @param center the center point
     * @param random random number generator
     * @return a randomly positioned leaf
     */
    private static Leaf randomLeaf(Vector2 center, Random random) {
        float x = center.x()
                - CANOPY_RADIUS
                + random.nextFloat() * (2 * CANOPY_RADIUS);

        float y = center.y()
                - CANOPY_RADIUS
                + random.nextFloat() * (2 * CANOPY_RADIUS);

        Leaf leaf = new Leaf(new Vector2(x, y));
        animateLeaf(leaf, random);
        return leaf;
    }

    /**
     * Animate the leaf with rotation and size transitions
     * @param leaf the leaf to animate
     * @param random random number generator
     */
    private static void animateLeaf(Leaf leaf, Random random) {
        float delay = random.nextFloat();

        new ScheduledTask(
                leaf,
                delay,
                false,
                () -> {
                    new Transition<>(
                            leaf,
                            angle -> leaf.renderer().setRenderableAngle(angle),
                            -30f,
                            30f,
                            Transition.LINEAR_INTERPOLATOR_FLOAT,
                            3f,
                            Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                            null
                    );

                    Vector2 baseSize = leaf.getDimensions();
                    new Transition<>(
                            leaf,
                            leaf::setDimensions,
                            baseSize,
                            baseSize.mult(1.2f),
                            Transition.LINEAR_INTERPOLATOR_VECTOR,
                            3f,
                            Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                            null
                    );
                }
        );
    }

}
