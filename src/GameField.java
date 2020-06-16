import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;

    //images for our game objects
    private Image snakeBody;
    private Image appleIcon;
    private Image snakeHead;

    //we need a timer for repainting the game field and moving the snake
    private Timer timer;

    //check state - we are still in the game or we lost it
    //at the beginning it's true, because the game starts right after the launching
    private boolean inGame = true;

    //we create but not initialized apple and snake class objects
    Apple apple;
    Snake snake;

    public GameField(){
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.CYAN));
        drawImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        //we set the focus on the game field for listening the key pressed here and for responding on it
        setFocusable(true);
    }

    private void initGame(){
        repaint();
        //We set the timer with 250 millisecond frequency; this class is a listener for the timer
        timer = new Timer(250, this);
        timer.start();
        apple = new Apple();
        snake = new Snake();
    }


    private void drawImage(){
        //we draw image through the loadImage method
        appleIcon = loadImage("apple.png");
        snakeBody = loadImage("snake_body.png");
        snakeHead = loadImage("snakeHead_right.png");
    }

    private Image loadImage(String fileName){
        ImageIcon appleImageIcon = new ImageIcon(fileName);
        return appleImageIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //if we are still in the game
        if (inGame){
            //we put an apple icon on the game field
            g.drawImage(appleIcon, apple.getAppleX(), apple.getAppleY(), this);

            //for the snake head we need to check the current direction of the snake
            //for every snake direction we have 4 different head icons.
            //method checkSnakeHeadPosition loads the proper icon for the current snake position
            checkSnakeHeadPosition();
            //then we put the snake's head on the game field
            g.drawImage(snakeHead, snake.getX(0), snake.getY(0), this);
            //for the body parts array we need for() loop to put every single body part at the field
            for (int i = 1; i < snake.getSnakeBodyParts(); i++){
                g.drawImage(snakeBody, snake.getX(i), snake.getY(i), this);
            }
        }
        //but if the game is lost we need to declare it
        //for putting the formatted string we use the method draw string down below
        else {
            drawString(g, "Game over", (int)SIZE/3, Color.RED, 16);
            drawString(g, "Press Enter to Continue...", (int)SIZE/3+50, Color.CYAN, 14);
        }
    }

    //this method is used to load the proper snake's head image
    private void checkSnakeHeadPosition(){
        switch (snake.getSnakeState()) {
            case RIGHT:
                snakeHead = loadImage("snakeHead_right.png");
                break;
            case LEFT:
                snakeHead = loadImage("snakeHead_left.png");
                break;
            case UP:
                snakeHead = loadImage("snakeHead_up.png");
                break;
            case DOWN:
                snakeHead = loadImage("snakeHead_down.png");
                break;
            default:
                snakeHead = loadImage("snakeHead_right.png");
        }
    }

    //Here is the adjustments for drawing strings
    private void drawString(Graphics g, String str, int positionY, Color color, int fontSize){
        Font f = new Font("Arial", Font.BOLD, fontSize);
        g.setColor(color);
        g.setFont(f);
        FontMetrics metrics = g.getFontMetrics(f);
        //the string is always aligned to the center horizontally
        int x = (int)(370 - metrics.stringWidth(str))/2;
        g.drawString(str, x, positionY);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //if we are still in the game we have to
        //check if the snake ate an apple
        //or if the snake crawled out from the field or bump into itself.
        //And move on
        if (inGame){
            //if the snake eat an apple it gains +1 body part
            if (apple.snakeEatAnApple(snake.getX(0), snake.getY(0))) {
                snake.increaseSnakeBodyParts();
                //also we need to create a new apple
                apple = new Apple();
                //if creates with the same coordinates as the snake has
                //we shall try to recreate it.
                while(appleIsInASnake())
                    apple = new Apple();
            }
            //but if the collision occurred the game if over
            if(snake.collisionsOccurred()) {
                inGame = false;
                //we also need to stop the timer
                timer.stop();
            }
            else
                //if there is no collision we keep moving
                snake.move();
        }
        repaint();
    }

    //when we generate a new apple we want to be sure whether the apple was created with the same coordinates
    //as some of the snake body parts or not. If it was, we will redraw it in the actionPerformed method and check again
    private boolean appleIsInASnake(){
        for (int i = 0; i < snake.getSnakeBodyParts(); i++)
            if (apple.getAppleX() == snake.getX(i) && apple.getAppleY() == snake.getY(i))
                return true;
        return false;
    }

    //there is a key press event handling method
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            //if the ENTER key is pressed in we are not in the game -> start a new game
            if (key == KeyEvent.VK_ENTER && !inGame){
                inGame = true;
                initGame();
            }
            if (key == KeyEvent.VK_LEFT){
                snake.tryToTurnLeft();
            }
            if (key == KeyEvent.VK_RIGHT){
                snake.tryToTurnRight();
            }
            if (key == KeyEvent.VK_UP){
                snake.tryToTurnUp();
            }
            if (key == KeyEvent.VK_DOWN){
                snake.tryToTurnDown();
            }
        }
    }

}
