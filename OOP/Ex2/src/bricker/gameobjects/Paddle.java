package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents the player's paddle in the game,
 * which can be moved left and right using keyboard input.
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Vector2 paddleDimensions;


    /**
     * Constructs a new Paddle object.
     * @param topLeftCorner The position of the top-left corner.
     * @param dimensions The dimensions of the paddle.
     * @param renderable The renderable (image) for the paddle.
     * @param inputListener The user input listener for keyboard input.
     * @param windowDimensions The dimensions of the game window.
     */
    public Paddle(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            UserInputListener inputListener,
            Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddleDimensions = dimensions;
    }

    /**
     * Updates the paddle's position based on user input.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        Vector2 topLeft = getTopLeftCorner();
        // Keep paddle inside window boundaries
        if (topLeft.x() < 0) {
            topLeft = new Vector2(0, getTopLeftCorner().y());
        } else if (topLeft.x() + paddleDimensions.x() > windowDimensions.x()) {
            topLeft = new Vector2(windowDimensions.x() - paddleDimensions.x(), topLeft.y());
        }
        setTopLeftCorner(topLeft);
    }
}
