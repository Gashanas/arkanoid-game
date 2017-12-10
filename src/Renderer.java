import bricks.BaseBrick;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer extends JPanel {

    private Dimension gameField;
    private Player player;
    private List<Ball> ballList = new ArrayList<Ball>();
    private BricksField bricks;
    private int score;
    private boolean isSuperMode = false;
    private int superModeTimer;

    public Renderer(Frame container, Dimension gameField, Player player, List ballList, BricksField bricks, int score){
        this.gameField = gameField;
        this.player = player;
        this.ballList = ballList;
        this.bricks = bricks;
        this.score = score;

    }

    public void setSuperModeTimer(int timer) {
        this.superModeTimer = timer;
    }

    public void setBallList(List<Ball> ballList){
        this.ballList = ballList;
    }

    public void setSize(Dimension size){
            super.setSize(size);
            gameField = new Dimension(size.width-200,size.height-290);

    }

    public void setIsSuperMode(boolean isSuperMode){
        this.isSuperMode = isSuperMode;
    }

    public void setObjectChanges(Player player, List<Ball> ballList, BricksField bricks){
        if (player != null) {
            this.player = player;
        }
        if(ballList != null){
            this.ballList = ballList;
        }
        if(bricks != null){
            this.bricks = bricks;
        }
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void paint(Graphics g){
        super.paint(g);
        g.translate((getWidth()-gameField.width)/2, (getHeight()-gameField.height)/2);


        g.setColor(Color.cyan);
        g.fillRect(0,0,gameField.width,gameField.height);

        for (BaseBrick brick : this.bricks.getBaseBrickList()) {
            renderBrick(g, brick);
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        Graphics2D g2d = (Graphics2D) g.create();
        g.setColor(Color.BLACK);
        Graphics2D g2d2 = (Graphics2D) g.create();
        g2d2.drawString("Score: "+score, 700, 20);

        for(int i =0;i<ballList.size();i++){
            renderBall(ballList.get(i),g);
        }

        renderPlayersBrick(g);

        g.setColor(new Color(0,0,0));
        g.drawRect(0,0,gameField.width,gameField.height);

        if(isSuperMode){
            g.fillRect(0, getHeight()-260,getWidth()-184,5);
            g2d2.setColor(Color.red);
            g2d2.drawString("Super mode timer: "+this.superModeTimer,50,20);
        }

    }

    public void renderBrick(Graphics g, BaseBrick brick){
        if(brick.isNotDestroyed()){

            switch(brick.getLives()){
                case 1: g.setColor(Color.LIGHT_GRAY);
                    break;
                case 2: g.setColor(Color.PINK);
                    break;
                case 3: g.setColor(Color.YELLOW);
                    break;
                case 4: g.setColor(Color.ORANGE);
                    break;
                case 5: g.setColor(Color.RED);
                    break;
            }
            g.fillRect(brick.getCoordinateX(),brick.getCoordinateY(), brick.getDefaultBrickWidth(),brick.getDefaultBrickHeight());
            if(brick.getBrickType()==2){
                Ball ball = new Ball(brick.getCoordinateX()+brick.getDefaultBrickWidth()/2-10,brick.getCoordinateY()+5,10,gameField,player);
                renderBall(ball, g);
            }
            if(brick.getBrickType()==5){
                Graphics2D g2d2 = (Graphics2D) g.create();
                g2d2.setColor(Color.BLACK);
                g2d2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
                g2d2.drawString("S ", brick.getCoordinateX()+30,brick.getCoordinateY()+23);
            }
        };
    }

    private void renderBall (Ball ball, Graphics g ){

        g.setColor(Color.ORANGE);
        g.fillOval(ball.getBallPossition().x,ball.getBallPossition().y, ball.getRadius()*2,ball.getRadius()*2);

    }

    private void renderPlayersBrick (Graphics g){
        g.setColor(new Color(130,130,130));
        g.fillRect(player.getPlayersBrick().x, player.getPlayersBrick().y, player.getPlayersBrick().width, player.getPlayersBrick().height);
    }

}
