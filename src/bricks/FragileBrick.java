package bricks;

public class FragileBrick extends BaseBrick {

    private int lives = 2;

    public FragileBrick(int x, int y) {
        super(x, y);
        super.setLives(lives);
        super.setBrickType(lives);
    }

    @Override
    public void hitBrick() {
        super.setLives(lives -= 1);
        if(lives == 0){
            super.setIsDestroyed(true);
        }
    }
}
