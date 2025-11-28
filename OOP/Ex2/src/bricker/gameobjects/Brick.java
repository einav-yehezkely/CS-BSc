package bricker.gameobjects;
import bricker.brick_strategies.CollisionStrategy;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick in the Bricker game with a specific collision strategy.
 */
public class Brick extends GameObject {
    private final int row;
    private final int col;
    CollisionStrategy strategy;

    /**
     * Constructs a new Brick object.
     * @param topLeftCorner The position of the top-left corner.
     * @param dimensions The dimensions of the brick.
     * @param renderable The renderable image for the brick.
     * @param row The row index of the brick in the grid.
     * @param col The column index of the brick in the grid.
     * @param strategy The collision strategy for this brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 int row, int col, CollisionStrategy strategy) {
        super(topLeftCorner, dimensions, renderable);
        this.row = row;
        this.col = col;
        this.strategy = strategy;
    }

    /**
     * Handles collision events by delegating to the assigned collision strategy.
     * @param other The other game object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        strategy.onCollision(this, other);
    }

    /**
     * Gets the row index of the brick.
     * @return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of the brick.
     * @return The column index.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the collision strategy of the brick.
     * @return The collision strategy.
     */
    public CollisionStrategy getStrategy() {
        return strategy;
    }
}
