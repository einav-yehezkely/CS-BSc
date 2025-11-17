package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {
    private static final String WINDOW_TITLE = "Bricker";
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    private static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final Vector2 BALL_VELOCITY = Vector2.DOWN.mult(150);
    private static final Vector2 PADDLE_STARTING_POSITION =
            new Vector2(WINDOW_DIMENSIONS.x() / 2, WINDOW_DIMENSIONS.y() - 30);
    private static final Vector2 BALL_STARTING_POSITION =
            WINDOW_DIMENSIONS.mult(0.5f);

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // Create a ball game object
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        GameObject ball =
                new GameObject(Vector2.ZERO, BALL_DIMENSIONS, ballImage);

        ball.setVelocity(BALL_VELOCITY);
        ball.setCenter(BALL_STARTING_POSITION);
        gameObjects().addGameObject(ball);

        // create a paddle game object
        Renderable paddleImage =
                imageReader.readImage("assets/paddle.png", true);
        GameObject paddle =
                new GameObject(Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage);

        paddle.setCenter(PADDLE_STARTING_POSITION);
        gameObjects().addGameObject(paddle);
    }

    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS).run();
    }
}
