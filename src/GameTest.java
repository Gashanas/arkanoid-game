

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;

public class GameTest {
    @org.junit.Test
    public void getGameTitle() throws IOException {
        Game game = new Game("arkanoid");
        assertEquals(game.getGameTitle(),"arkanoid");
    }

    @org.junit.Test
    public void checkIfBallCollidesWithPlayersBrick() throws Exception {
        Game game = new Game("arkanoid");
        Ball testBall = new Ball(800/2, 500/2,10, new Dimension(800,500), game.getPlayer());
        Dimension vector = testBall.getMovementVector();
        game.checkIfBallCollidesWithPlayersBrick(testBall);
        assertEquals(testBall.getMovementVector().getHeight(),vector.getHeight(),0);
    }

    @org.junit.Test
    public void countNotDestroyedBricks() throws Exception {
        Game game = new Game("arkanoid");
        BricksField testBrickField = new BricksField(36, new Dimension(800,500));
        testBrickField.setLevel("");
        game.countNotDestroyedBricks(testBrickField);
        assertEquals(36,game.getNotDestroyedBricks());
    }

}
