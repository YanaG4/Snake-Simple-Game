public class Snake {
    private final int SIZE = 320;
    private final int SINGLE_OBJECT_SIZE = 16;
    private final int ALL_DOTS = (int)Math.pow(SIZE/SINGLE_OBJECT_SIZE, 2);

    private int[] snakeX = new int[ALL_DOTS];
    private int[] snakeY = new int[ALL_DOTS];

    private int snakeBodyParts; //the amount of current body parts, including snake's head
    private SnakeState currentSnakeState; //current snake direction

    enum SnakeState { //snake movement direction
        LEFT,
        RIGHT,
        UP,
        DOWN
    }


    public Snake() {
        currentSnakeState = SnakeState.RIGHT;
        snakeBodyParts = 3; //at the beginning the snake has just 3 body parts, including it's head
        for (int i = 0; i < snakeBodyParts; i++) {
            //this is a hardcoding of the snake starting position. Particularly, for it's every single body part.
            //alternatively, we may generate it randomly, but it isn't realized yet
            snakeX[i] = SINGLE_OBJECT_SIZE * snakeBodyParts - i * SINGLE_OBJECT_SIZE;
            snakeY[i] = SIZE/2;
        }

    }

    public int getSnakeBodyParts() {
        return snakeBodyParts;
    }

    //this method is used when the snake eats an apple and gains +1 body part
    public void increaseSnakeBodyParts(){
        snakeBodyParts++;
    }

    public SnakeState getSnakeState(){
        return currentSnakeState;
    }

    public int getX(int i){
        return snakeX[i];
    }

    public int getY(int i){
        return snakeY[i];
    }

    public void move() {
        //for changing position of our snake we need to "delete" current snake tail
        //here we replace every single body's coordinates to a next ones
        for (int i = snakeBodyParts; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        //for creating new snake's head, we need to check the direction of the snake
        switch (currentSnakeState){
            case LEFT:
                //if the snake crawls to the left, it means, that it's X coordinate decreases
                snakeX[0] -= SINGLE_OBJECT_SIZE;
                break;
            case RIGHT:
                snakeX[0] += SINGLE_OBJECT_SIZE;
                break;
            case UP:
                snakeY[0] -= SINGLE_OBJECT_SIZE;
                break;
            case DOWN:
                snakeY[0] += SINGLE_OBJECT_SIZE;
                break;
         }
    }

    public boolean collisionsOccurred() {
        //here we check whether the snake bump into itself
        //we have to check both x and y coordinates
        for (int i = snakeBodyParts; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i])
                return true;
        }
        //here we check whether the snake crawls out of the game field
        if (snakeX[0] > SIZE || snakeX[0] < 0 || snakeY[0] > SIZE || snakeY[0] < 0) {
            return true;
        }
        return false;
    }

    public void tryToTurnLeft(){
        //we can't turn right if previously the snake crawls to the left
        //if that isn't that case, the snake state turns to left
        if (currentSnakeState != SnakeState.RIGHT)
            currentSnakeState = SnakeState.LEFT;
    }

    public void tryToTurnRight(){
        if (currentSnakeState != SnakeState.LEFT)
            currentSnakeState = SnakeState.RIGHT;
    }

    public void tryToTurnUp(){
        if (currentSnakeState != SnakeState.DOWN)
        currentSnakeState = SnakeState.UP;
    }

    public void tryToTurnDown(){
        if (currentSnakeState != SnakeState.UP)
            currentSnakeState = SnakeState.DOWN;
    }

}
