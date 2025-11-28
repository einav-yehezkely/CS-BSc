package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a ball in the game that can collide with other objects.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter;

    /**
     * Constructs a new Ball object.
     * @param topLeftCorner The position of the top-left corner.
     * @param dimensions The dimensions of the ball.
     * @param renderable The renderable image of the ball.
     * @param collisionSound The sound to play upon collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
    }

    /**
     * Handles collision with another game object.
     * @param other The other game object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
        collisionCounter++;

    }

    /**
     * Returns the number of collisions this ball has had.
     * Was asked to add this method on part 1.3.
     * @return The collision count.
     */
    public int getCollisionCounter(){
        return collisionCounter;
    }
}
