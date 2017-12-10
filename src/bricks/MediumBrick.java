package bricks;
public class MediumBrick extends BaseBrick {

    private int lives = 3;

    public MediumBrick(int x, int y) {
        super(x, y);
        super.setLives(lives);
        super.setBrickType(lives);
    }
}
