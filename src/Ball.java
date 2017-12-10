import java.awt.*;

public class Ball {


    private Point ballPossition = new Point(0,0);
    private int radius;
    private Dimension movementVector = new Dimension(0,0);
    private Dimension gameField = new Dimension();
    private int xDirection;
    private int yDirection;
    private Player playersBrick;

    public Ball(int x, int y, int radius, Dimension field, Player playersBrick) {
        ballPossition = new Point(x, y);
        this.radius = radius;
        this.movementVector = new Dimension(2,2);
        this.gameField = field;
        this.playersBrick = playersBrick;
    }

    public Dimension getMovementVector() {
        return movementVector;
    }

    public void setMovementVectorHeight (int height){
        this.movementVector.height = height;
    }

    public void setMovementVectorWidth (int width){
        this.movementVector.width = width;
    }

    public void setVector(int xMovement, int yMovement) {
        movementVector = new Dimension(xMovement,yMovement);
    }

    public int getRadius() {
        return radius;
    }

    public Point getBallPossition(){
        return ballPossition;
    }

    public void setBallPossition(int x, int y){
        this.ballPossition = new Point(x,y);
    }

    public void move() {
        ballPossition.move(ballPossition.x+ movementVector.width,ballPossition.y+ movementVector.height);
    }


}
