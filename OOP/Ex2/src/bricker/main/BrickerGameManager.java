package bricker.main;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class BrickerGameManager extends GameManager {
    private static final String WINDOW_TITLE = "Bricker";
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    private static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final Vector2 BALL_INITIAL_VELOCITY = Vector2.DOWN.mult(150);
    private static final Vector2 PADDLE_STARTING_POSITION =
            new Vector2(WINDOW_DIMENSIONS.x() / 2, WINDOW_DIMENSIONS.y() - 30);
    private static final Vector2 BALL_STARTING_POSITION =
            WINDOW_DIMENSIONS.mult(0.5f);
    private static final int WALL_THICKNESS = 5;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // create background game object
        Renderable backgroundImage =
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background =
                new GameObject(Vector2.ZERO, WINDOW_DIMENSIONS, backgroundImage);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Create a ball game object
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball =
                new Ball(Vector2.ZERO, BALL_DIMENSIONS, ballImage, collisionSound);

        ball.setVelocity(BALL_INITIAL_VELOCITY);
        ball.setCenter(BALL_STARTING_POSITION);
        gameObjects().addGameObject(ball);

        // create a paddle game object
        Renderable paddleImage =
                imageReader.readImage("assets/paddle.png", true);
        GameObject paddle =
                new Paddle(
                        Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage, inputListener, WINDOW_DIMENSIONS
                );

        paddle.setCenter(PADDLE_STARTING_POSITION);
        gameObjects().addGameObject(paddle);

        setWalls();
    }

    private void setWalls(){
        RectangleRenderable wallRenderable = new RectangleRenderable(Color.BLACK);

        GameObject topWall = new GameObject(
                Vector2.ZERO,
                new Vector2(WINDOW_DIMENSIONS.x(), WALL_THICKNESS),
                wallRenderable);
        GameObject leftWall = new GameObject(
                Vector2.ZERO,
                new Vector2(WALL_THICKNESS, WINDOW_DIMENSIONS.y()),
                wallRenderable);
        GameObject rightWall = new GameObject(
                new Vector2(WINDOW_DIMENSIONS.x() - WALL_THICKNESS, 0),
                new Vector2(WALL_THICKNESS, WINDOW_DIMENSIONS.y()),
                wallRenderable);

        gameObjects().addGameObject(topWall);
        gameObjects().addGameObject(leftWall);
        gameObjects().addGameObject(rightWall);
    }
    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS).run();
    }
}
