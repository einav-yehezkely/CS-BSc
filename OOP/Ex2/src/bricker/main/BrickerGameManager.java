package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
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
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final String WINDOW_TITLE = "Bricker";
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    private static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final float BALL_INITIAL_VELOCITY = 200;
    private static final Vector2 PADDLE_STARTING_POSITION =
            new Vector2(WINDOW_DIMENSIONS.x() / 2, WINDOW_DIMENSIONS.y() - 30);
    private static final Vector2 BALL_STARTING_POSITION =
            WINDOW_DIMENSIONS.mult(0.5f);
    private static final int WALL_THICKNESS = 5;
    private static final int DEFAULT_NUM_BRICKS = 8;
    private static final int DEFAULT_NUM_ROWS = 7;
    private static final int BRICK_HEIGHT = 15;
    private static final int GAP_BETWEEN_BRICKS = 2;

    private int numBricks;
    private int numRows;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.numBricks = DEFAULT_NUM_BRICKS;
        this.numRows = DEFAULT_NUM_ROWS;
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numBricks, int numRows) {
        super(windowTitle, windowDimensions);
        this.numBricks = numBricks;
        this.numRows = numRows;
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

        ball.setCenter(BALL_STARTING_POSITION);
        gameObjects().addGameObject(ball);

        float ballVelocityX = BALL_INITIAL_VELOCITY;
        float ballVelocityY = BALL_INITIAL_VELOCITY;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelocityX *= -1;
        }
        if (rand.nextBoolean()){
            ballVelocityY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelocityX, ballVelocityY));

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

        // create bricks
        CollisionStrategy strategy = new BasicCollisionStrategy(gameObjects());
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);
        createBricks(brickImage, strategy);
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

        topWall.setTag("wall");
        leftWall.setTag("wall");
        rightWall.setTag("wall");
        gameObjects().addGameObject(topWall);
        gameObjects().addGameObject(leftWall);
        gameObjects().addGameObject(rightWall);
    }

    private void createBricks(Renderable brickImage, CollisionStrategy strategy){
        float brickWidth = (WINDOW_DIMENSIONS.x() - 2 * WALL_THICKNESS) / numBricks;
        float brickHeight = BRICK_HEIGHT;
        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numBricks; col++){
                GameObject brick = new Brick(
                        new Vector2(
                                WALL_THICKNESS + col * brickWidth, WALL_THICKNESS + row * brickHeight
                        ),
                        new Vector2(brickWidth, brickHeight),
                        brickImage,
                        row,
                        col,
                        strategy
                );
                gameObjects().addGameObject(brick);
            }
        }
    }
    public static void main(String[] args) {
        if(args.length == 2){
            new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS,
                    Integer.parseInt(args[0]), Integer.parseInt(args[1])).run();
        }
        else{
            new BrickerGameManager(WINDOW_TITLE, WINDOW_DIMENSIONS).run();
        }
    }
}
