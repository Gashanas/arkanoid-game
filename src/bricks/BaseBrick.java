package bricks;

import java.awt.*;

public class BaseBrick {

    private Rectangle brick;
    private int lives = 1;
    private int defaultBrickHeight = 30;
    private int defaultBrickWidth = 70;
    private boolean isDestroyed = false;
    private int brickType=1;

    public int getLives() {
        return lives;
    }

    public void setBrickType(int type){
        this.brickType = type;
    }

    public int getBrickType(){
        return brickType;
    }

    public int getDefaultBrickHeight() {
        return defaultBrickHeight;
    }

    public int getDefaultBrickWidth() {
        return defaultBrickWidth;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public BaseBrick(int x, int y, int lives) {
        this.brick = new Rectangle(x,y,defaultBrickWidth,defaultBrickHeight);
        this.lives = lives;
    }

    public BaseBrick(int x, int y) {
        this.brick = new Rectangle(x,y,defaultBrickWidth,defaultBrickHeight);
        this.lives = 1;
    }

    public int getCoordinateX (){
        return brick.x;
    }

    public int getCoordinateY (){
        return brick.y;
    }

    public void printInfo(){
        System.out.println(brick.x+" "+brick.y+" "+ brick.width+" "+ brick.height);
    }

    public boolean isNotDestroyed(){
        return !isDestroyed;
    }

    public Rectangle getBrick(){
        return brick;
    }

    public void hitBrick(){
        lives -= 1;
        if(lives == 0){
            this.isDestroyed = true;
        }
    }

    public void setIsDestroyed(boolean isDestroyed){
        this.isDestroyed = isDestroyed;
    }

    public boolean collidesWith(Rectangle object) {
        return brick.intersects(object);
    }


}
