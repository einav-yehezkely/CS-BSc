package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a fruit that can be eaten by the avatar.
 */
public class Fruit extends GameObject {
    private static final float SIZE = 12f;
    private static final float REAPPEAR_TIME = 30f;
    public static final int ENERGY_GAIN = 10;

    private boolean eaten = false;

    /**
     * Constructor for a Fruit object
     * @param position the position of the fruit
     */
    public Fruit(Vector2 position) {
        super(
                position,
                new Vector2(SIZE, SIZE),
                new OvalRenderable(ColorSupplier.approximateColor(Color.RED))
        );
        this.setTag("fruit");
    }

    /**
     * Create fruits on some of the leaves
     * @param leaves the leaves to potentially place fruits on
     * @param random random number generator
     * @return list of fruits
     */
    public static List<GameObject> createFruits(
            List<GameObject> leaves,
            Random random
            ) {

        List<GameObject> fruits = new ArrayList<>();

        for (GameObject leaf : leaves) {
            if (random.nextFloat() < 0.07f) { // 7% chance to spawn a fruit on each leaf
                 Vector2 pos = leaf.getTopLeftCorner();
                     fruits.add(new Fruit(pos));
            }
        }

        return fruits;
    }

    /**
     * Handles the eating of the fruit.
     * The fruit disappears and reappears after a set time.
     */
    public void eat() {
        if (eaten) {
            return;
        }

        eaten = true;

        renderer().setRenderable(null);

        new ScheduledTask(
                this,
                REAPPEAR_TIME,
                false,
                this::reappear
        );
    }

    /**
     * Makes the fruit reappear after being eaten.
     */
    private void reappear() {
        renderer().setRenderable(
                new OvalRenderable(ColorSupplier.approximateColor(Color.RED))
        );
        eaten = false;
    }

}
