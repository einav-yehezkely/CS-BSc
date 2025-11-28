package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.StrategyFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final String WINDOW_TITLE = "Bricker";
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    private static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final Vector2 HEART_DIMENSIONS = new Vector2(20, 20);
    private static final Vector2 TEXT_DIMENSIONS = new Vector2(20, 20);
    private static final float BALL_INITIAL_VELOCITY = 200;
    private static final Vector2 PADDLE_STARTING_POSITION =
            new Vector2(WINDOW_DIMENSIONS.x() / 2, WINDOW_DIMENSIONS.y() - 30);
    private static final Vector2 BALL_STARTING_POSITION =
            WINDOW_DIMENSIONS.mult(0.5f);
    private static final int WALL_THICKNESS = 5;
    private static final int DEFAULT_NUM_BRICKS = 8;
    private static final int DEFAULT_NUM_ROWS = 7;
    private static final int BRICK_HEIGHT = 15;
    private static final int GAP_BETWEEN_BRICKS = 1;
    private static final int INITIAL_LIVES = 3;
    private static final int MAX_LIVES = 4;

    private final int numBricks;
    private final int numRows;
    private Ball ball;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private int lives;
    private GraphicLives graphicLives;
    private NumericLives numericLives;
    private Counter brickCounter;
    private final Brick[][] bricksGrid;
    private Paddle mainPaddle;

    /**
     * Constructor of BrickerGameManager
     * @param windowTitle the title of the window
     * @param windowDimensions the dimensions of the window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.numBricks = DEFAULT_NUM_BRICKS;
        this.numRows = DEFAULT_NUM_ROWS;
        this.bricksGrid = new Brick[numRows][numBricks];
    }

    /**
     * Constructor of BrickerGameManager with custom number of bricks and rows
     * @param windowTitle the title of the window
     * @param windowDimensions the dimensions of the window
     * @param numBricks custom number of bricks per row
     * @param numRows custom number of rows
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numBricks, int numRows) {
        super(windowTitle, windowDimensions);
        this.numBricks = numBricks;
        this.numRows = numRows;
        this.bricksGrid = new Brick[numRows][numBricks];
    }

    /**
     * Initializes the game by creating game objects and setting up the game environment.
     * @param imageReader imageReader
     * @param soundReader soundReader
     * @param inputListener inputListener
     * @param windowController windowController
     */
    @Override
    public void initializeGame(
            ImageReader imageReader, 
            SoundReader soundReader, 
            UserInputListener inputListener, 
            WindowController windowController) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
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
        ball =
                new Ball(Vector2.ZERO, BALL_DIMENSIONS, ballImage, getCollisionSound());

        resetBall();
        gameObjects().addGameObject(ball);

        // create a paddle game object
        Renderable paddleImage = getPaddleImage();
        Paddle paddle =
                new Paddle(
                        Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage, inputListener, WINDOW_DIMENSIONS
                );
        this.mainPaddle = paddle;


        paddle.setCenter(PADDLE_STARTING_POSITION);
        gameObjects().addGameObject(paddle);

        setWalls();

        // create bricks
        this.brickCounter = new Counter(numBricks * numRows);
        StrategyFactory factory = new StrategyFactory(bricksGrid, this);
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);
        createBricks(brickImage, factory);

        // create hearts display
        graphicLives = new GraphicLives(gameObjects(), imageReader, WINDOW_DIMENSIONS,
                INITIAL_LIVES, WALL_THICKNESS, HEART_DIMENSIONS, MAX_LIVES);
        this.lives = INITIAL_LIVES;

        // create numerical lives display
        numericLives = new NumericLives(gameObjects(), WINDOW_DIMENSIONS, INITIAL_LIVES,
                WALL_THICKNESS, TEXT_DIMENSIONS, HEART_DIMENSIONS);
    }

    private void resetBall(){
        ball.setCenter(BALL_STARTING_POSITION);

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

    private void createBricks(Renderable brickImage, StrategyFactory factory){
        float totalGapsWidth = (numBricks - 1) * GAP_BETWEEN_BRICKS;
        float brickWidth =
                (WINDOW_DIMENSIONS.x() - 2 * WALL_THICKNESS - totalGapsWidth) / numBricks;
        float brickHeight = BRICK_HEIGHT;
        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numBricks; col++){
                CollisionStrategy strategy = factory.generateStrategy(0);
                Brick brick = new Brick(
                        new Vector2(
                                WALL_THICKNESS + col * (brickWidth + GAP_BETWEEN_BRICKS),
                                WALL_THICKNESS + row * brickHeight
                        ),
                        new Vector2(brickWidth, brickHeight),
                        brickImage,
                        row,
                        col,
                        strategy
                );
                bricksGrid[row][col] = brick;
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Updates the game state, checking for game end conditions.
     * @param deltaTime the time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();

    }

    private void checkForGameEnd(){
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight > WINDOW_DIMENSIONS.y()){
            lives--;
            graphicLives.loseLife(lives);
            numericLives.updateLives(lives);
            if(lives > 0){
                resetBall();
            }
            else{
                prompt = "You lose! Play again?";
            }
        }
        if(brickCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = "You win! Play again?";
        }
        if (!prompt.isEmpty()){
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else{
                windowController.closeWindow();
            }
        }
    }

    /**
     * Gets the dimensions of the heart icon.
     * @return the dimensions of the heart icon.
     */
    public Vector2 getHeartDimensions() {
        return HEART_DIMENSIONS;
    }

    /**
     * Gets the window dimensions.
     * @return the window dimensions.
     */
    public Vector2 getWindowDimensions(){
        return WINDOW_DIMENSIONS;
    }

    /**
     * Gets the collection of game objects.
     * @return the collection of game objects.
     */
    public GameObjectCollection getGameObjects(){
        return gameObjects();
    }

    /**
     * Increases the player's lives by one, up to the maximum allowed.
     */
    public void gainLife(){
        if(lives < MAX_LIVES){
            lives++;
            graphicLives.gainLife(lives);
            numericLives.updateLives(lives);
        }
    }

    /**
     * Gets the image reader.
     * @return the image reader.
     */
    public ImageReader getImageReader(){
        return imageReader;
    }

    /**
     * Gets the main paddle.
     * @return the main paddle reference.
     */
    public Paddle getMainPaddle(){
        return mainPaddle;
    }

    /**
     * Gets the sound reader.
     * @return the sound reader.
     */
    public SoundReader getSoundReader(){
        return soundReader;
    }

    /**
     * Gets the user input listener.
     * @return the user input listener.
     */
    public UserInputListener getInputListener(){
        return inputListener;
    }

    /**
     * Gets the brick counter.
     * @return the brick counter.
     */
    public Counter getBrickCounter(){
        return brickCounter;
    }

    /**
     * Gets the collision sound of the ball and bricks.
     * @return the collision sound.
     */
    public Sound getCollisionSound(){
        return soundReader.readSound("assets/blop.wav");
    }

    /**
     * Gets the ball dimensions.
     * @return the ball dimensions.
     */
    public Vector2 getBallDimensions(){
        return BALL_DIMENSIONS;
    }

    /**
     * Gets the paddle dimensions.
     * @return the paddle dimensions.
     */
    public Vector2 getPaddleDimensions(){
        return PADDLE_DIMENSIONS;
    }

    /**
     * Gets the paddle image.
     * @return the paddle image renderable.
     */
    public Renderable getPaddleImage(){
        return imageReader.readImage("assets/paddle.png", true);
    }


    /**
     * The main method to start the game.
     * @param args command line arguments (optional: number of bricks and rows).
     */
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
