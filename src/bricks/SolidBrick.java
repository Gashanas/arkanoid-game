package bricks;

public class SolidBrick extends BaseBrick {

    private int lives = 4;

    public SolidBrick(int x, int y) {
        super(x, y);
        super.setLives(lives);
        super.setBrickType(lives);
    }

}
