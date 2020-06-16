import java.util.Random;

public class Apple {
    private final int SINGLE_OBJECT_SIZE = 16; //the size of simple game object
    private int appleX;
    private int appleY;
    public Apple(){
        //in the apple constructor we generate random coordinates, where the apple will appear
            appleX = new Random().nextInt(20)* SINGLE_OBJECT_SIZE;
            appleY = new Random().nextInt(20)* SINGLE_OBJECT_SIZE;
    }

    public boolean snakeEatAnApple(int x, int y){
        //if the coordinates of the snake head matches with apple's coordinates - the snake ate this apple
        if(x == appleX && y == appleY){
            return true;
        }
        return false;
    }
    public int getAppleX(){
        return appleX;
    }
    public int getAppleY(){
        return appleY;
    }

}
