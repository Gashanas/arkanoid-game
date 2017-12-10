package bricks;

/**
 * Created by p998bef on 2017.12.10.
 */
public class SuperBrick extends BaseBrick {

    private int lives = 5;

    public SuperBrick(int x, int y) {
        super(x, y);
        super.setLives(lives);
        super.setBrickType(lives);
    }
}
