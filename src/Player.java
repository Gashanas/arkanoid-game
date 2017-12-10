import java.awt.*;

public class Player {
    private Rectangle playersBrick;
    private Dimension gameFieldSize;

    public Player(Dimension gameFieldSize) {
        this.gameFieldSize = gameFieldSize;
        playersBrick = new Rectangle(gameFieldSize.width - 100*2,gameFieldSize.height-20, 100,20);
    }

    public Player(Dimension gameFieldSize, int x, int y, int width, int heigth) {
        this.gameFieldSize = gameFieldSize;
        this.playersBrick = new Rectangle(x,y,width,heigth);
    }

    public Rectangle getPlayersBrick(){
        return playersBrick;
    }

    public boolean collidesWith(Rectangle object) {
        return playersBrick.intersects(object);
    }

    public void moveOnXAxis(int speed){
        playersBrick.x += speed;
        if(playersBrick.x < 0) playersBrick.x = 0;
        if(playersBrick.x > gameFieldSize.width - playersBrick.width) playersBrick.x = gameFieldSize.width-playersBrick.width;

    }
}
