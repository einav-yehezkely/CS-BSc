package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a tree composed of a trunk, leaves, and fruits.
 */
public class Tree {
    private static final float MIN_HEIGHT = 120f;
    private static final float MAX_HEIGHT = 200f;

    private final List<GameObject> parts = new ArrayList<>();

    /**
     * Constructs a Tree object at the specified ground position.
     * @param groundPosition the position on the ground where the tree will be placed
     * @param random the random number generator for creating tree parts
     */
    public Tree(Vector2 groundPosition, Random random) {
        float height =
                MIN_HEIGHT + random.nextFloat() * (MAX_HEIGHT - MIN_HEIGHT);
        Trunk trunk = new Trunk(groundPosition, height);
        parts.add(trunk);
        List<GameObject> leaves = Leaf.createLeaves(trunk, random);
        parts.addAll(leaves);
        parts.addAll(Fruit.createFruits(leaves, random));
    }

    /**
     * Returns the parts of the tree (trunk, leaves, fruits).
     * @return a list of GameObjects representing the tree parts
     */
    public List<GameObject> getParts() {
        return parts;
    }
}
