package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a puck that moves with a random velocity and gets removed when it goes out of bounds.
 */
public class Puck extends Ball{
    private final int ballSpeed;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a new Puck object.
     * @param topLeftCorner The position of the top-left corner.
     * @param dimensions The dimensions.
     * @param renderable The loaded image renderable.
     * @param collisionSound The sound to play on collision.
     * @param ballSpeed The speed of the puck.
     * @param windowDimensions The dimensions of the game window.
     * @param gameObjects The collection of game objects.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound, int ballSpeed,
                Vector2 windowDimensions, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.ballSpeed = ballSpeed;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;

        setVelocity(randomVelocity());
    }

    /**
     * Generates a random velocity vector for the puck.
     * @return A Vector2 representing the random velocity.
     */
    private Vector2 randomVelocity(){
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float)(Math.cos(angle) * ballSpeed);
        float velocityY = (float)(Math.sin(angle) * ballSpeed);
        return new Vector2(velocityX, velocityY);
    }

    /**
     * Updates the puck's position and removes it if it goes out of bounds.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float y = getCenter().y();

        if(y>windowDimensions.y()){
            gameObjects.removeGameObject(this);
        }
    }
}
