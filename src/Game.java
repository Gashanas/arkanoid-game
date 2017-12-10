import bricks.BaseBrick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private JFrame frame;
    private Renderer renderer;
    private Dimension gameField = new Dimension(800,500);
    private boolean isRunning = false;
    private boolean isPaused = false;
    private int notDestroyedBricks = 0;

    private int stardartPlayersBrickWidth = 100;
    private int stardartPlayersBrickHeight = 20;
    private int score = 0;
    private int lastScore = 0;
    private String gameTitle;

    private Player player;
    private List<Ball> ballList = new ArrayList<Ball>();
    private BricksField bricks;
    private Ball defaultBall = new Ball(gameField.width/2, gameField.height/2,10, gameField, player);

    private boolean isSuperMode = false;
    private int superModeTimer = 100;

    private Menu menu;

    public Game(String gameTitle) throws IOException {

        this.gameTitle = gameTitle;

        player = new Player(gameField, gameField.width - stardartPlayersBrickWidth*2,gameField.height-stardartPlayersBrickHeight, stardartPlayersBrickWidth,stardartPlayersBrickHeight);
        bricks = new BricksField(24,gameField);
        ballList.add(new Ball(gameField.width/2, gameField.height/2,10, gameField, player));

        createMenu(player, bricks, ballList, 0);
    }

    public void createMenu(Player player, BricksField bricks, List<Ball> balls, int lastScore){
        frame = new JFrame(gameTitle);
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        JButton button = new JButton("SELECT");
        button.setBounds(400,500,200,30);
        button.setVisible(true);
        menu=new Menu(frame,gameField,button,lastScore);
        frame.add(menu);
        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame = new JFrame(gameTitle);
                frame.setSize(1000,800);
                frame.setLocationRelativeTo(null);

                try {
                    bricks.setLevel(menu.getLevel());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                renderer = new Renderer(frame,gameField,player,ballList,bricks,score);
                frame.add(renderer);
                renderer.setSize(frame.getSize());
                frame.setVisible(true);


                frame.addKeyListener(new KeyAdapter(){
                    public void keyPressed(KeyEvent e) {
                        if(!isRunning || isPaused){
                            if(e.getKeyCode() == KeyEvent.VK_ENTER) start();
                        } else {
                            if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveOnXAxis(15);
                            if(e.getKeyCode() == KeyEvent.VK_LEFT) player.moveOnXAxis(-15);
                            if(e.getKeyCode() == KeyEvent.VK_R) try {
                                restartGame();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            if(e.getKeyCode() == KeyEvent.VK_P) pause();
                        }
                    }
                });
            }
        });
    }

    public void restartGame() throws IOException {
        pause();
        isSuperMode = false;
        frame.setVisible(false);
        ballList.removeAll(ballList);
        ballList.add(new Ball(gameField.width/2, gameField.height/2,10, gameField, player));
        bricks = new BricksField(24,gameField);
        createMenu(player, bricks, ballList, lastScore);
        score = 0;
    }

    public int getNotDestroyedBricks(){
        return notDestroyedBricks;
    }

    public void start(){
        this.isPaused = false;
        if(!isRunning) gameThread.start();
    }

    public void pause (){
        isPaused = true;
    }

    public void stop(){
        isRunning = false;
    }

    public Dimension getGameField() {
        return gameField;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getGameTitle(){
        return gameTitle;
    }

    public void checkIfBallCollidesWithWall(Ball ball, int ballIndex) throws IOException {
        if(ball.getBallPossition().x < 0){
            ball.setMovementVectorWidth(-ball.getMovementVector().width);
        } else if(ball.getBallPossition().x + ball.getRadius()*2 >= gameField.width){
            ball.setMovementVectorWidth(-ball.getMovementVector().width);
        }

        if(ball.getBallPossition().y < 0){
            ball.setMovementVectorHeight(-ball.getMovementVector().height);
        } else if(ball.getBallPossition().y + ball.getRadius()*2 > gameField.height){
            if(!isSuperMode){
                ballList.remove(ballIndex);
                renderer.setBallList(ballList);
                if(ballList.size()<1){
                    lastScore = score;
                    restartGame();
                }
            } else {
                ball.setMovementVectorHeight(-ball.getMovementVector().height);
            }
        }
    }

    public void checkIfBallCollidesWithPlayersBrick(Ball ball) {
        if(player.getPlayersBrick() != null){
            if(this.player.collidesWith(new Rectangle(ball.getBallPossition().x,ball.getBallPossition().y, ball.getRadius()*2, ball.getRadius()*2))){
                ball.setMovementVectorHeight(-ball.getMovementVector().height);
                renderer.repaint();
            }
        }
    }

    public void checkIfWallCollidesWithBrick(Ball ball) {
        for(int i =0 ; i<bricks.getBaseBrickList().size(); i++){
            if(bricks.getBaseBrickList().get(i).isNotDestroyed()) {
                if (bricks.getBaseBrickList().get(i).collidesWith(new Rectangle(ball.getBallPossition().x, ball.getBallPossition().y, ball.getRadius() * 2, ball.getRadius() * 2))) {
                    if((ball.getBallPossition().x < bricks.getBaseBrickList().get(i).getCoordinateX() || ball.getBallPossition().x+10 > bricks.getBaseBrickList().get(i).getCoordinateX()+bricks.getBaseBrickList().get(i).getDefaultBrickWidth())){
                        ball.setMovementVectorWidth(-ball.getMovementVector().width);
                    } else {
                        ball.setMovementVectorHeight(-ball.getMovementVector().height);
                    }
                    bricks.destroyBrickByIndex(i);
                    if(!bricks.getBaseBrickList().get(i).isNotDestroyed() && bricks.getBaseBrickList().get(i).getBrickType() == 5 ){
                        this.isSuperMode = true;
                        this.superModeTimer = 3000;
                        this.renderer.setIsSuperMode(true);
                    } else if(!bricks.getBaseBrickList().get(i).isNotDestroyed() && bricks.getBaseBrickList().get(i).getBrickType() == 2){
                        this.ballList.add(new Ball(gameField.width/2, gameField.height/2,10, gameField, player));
                        renderer.setBallList(ballList);
                    }
                    score += 100;
                    this.renderer.updateScore(score);
                }
            }
        }
    }

    public void countNotDestroyedBricks(BricksField bricks) throws IOException {
        int counter = 0;
        for (BaseBrick brick : bricks.getBaseBrickList()){
            if(brick.isNotDestroyed()){
                counter ++;
            }
        }
        notDestroyedBricks = counter;
        if(counter==0){
            lastScore = score;
            restartGame();
        }
    }

    Thread gameThread = new Thread ( new Runnable(){
        public void run(){
            isRunning = true;
            while (isRunning){
                if(!isPaused) {
                    renderer.setBallList(ballList);
                    for(int i =0; i<ballList.size();i++){
                        ballList.get(i).move();
                        checkIfBallCollidesWithPlayersBrick(ballList.get(i));
                        checkIfWallCollidesWithBrick(ballList.get(i));
                        try {
                            checkIfBallCollidesWithWall(ballList.get(i),i);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            countNotDestroyedBricks(bricks);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    renderer.repaint();
                    try {
                        if(!isSuperMode){
                            Thread.sleep(9);
                        } else {
                            Thread.sleep(3);
                            renderer.setSuperModeTimer(superModeTimer);
                            superModeTimer --;
                            if(superModeTimer == 0){
                                isSuperMode = false;
                                renderer.setIsSuperMode(false);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });
}
