

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class Menu extends JPanel {

    private Dimension gameField;
    private int score;
    private Rectangle[] levelBoxes = new Rectangle[4];
    private int counter = 180;
    private String level = "";
    private Rectangle border = new Rectangle();

    public Menu(Frame container, Dimension gameField, JButton button, int score){
        this.score = score;
        JPanel[] panels = new JPanel[4];
        for(int i = 0 ;i<panels.length;i++){
            panels[i] = new JPanel();
            panels[i].setSize(150,150);
            panels[i].setBackground(Color.BLUE);
            panels[i].setLocation(counter,300);
            counter += 160;
            JLabel l1 = new JLabel(""+(i+1));
            l1.setFont(new Font("TimesRoman", Font.PLAIN, 90));
            panels[i].add(l1);
            container.add(panels[i]);
            int index = i;
            panels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addBorder(panels, index);
                    setLevel(panels,index);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        this.gameField = gameField;
        container.add(button);
    }

    private void addBorder(JPanel[] panels, int index){
        for(int i =0; i<panels.length;i++){
            if(i == index) {
                panels[i].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
            } else {
                panels[i].setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }
        }
    }

    public String getLevel() {
        return this.level;
    }

    private void setLevel(JPanel[] panels, int index){
        if(index == 0 ){
            this.level = "gameField1.txt";
        } else if(index == 1 ){
            this.level = "gameField2.txt";
        } else if(index == 2 ){
            this.level = "gameField3.txt";
        } else if(index == 3 ){
            this.level = "gameField4.txt";
        }
    }

    public void setSize(Dimension size){
        super.setSize(size);
        gameField = new Dimension(size.width-200,size.height-290);
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void paint(Graphics g){
        super.paint(g);
        g.translate((getWidth()-gameField.width)/2, (getHeight()-gameField.height)/2);

        g.setColor(Color.cyan);
        g.fillRect(0,0,gameField.width,gameField.height);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        Graphics2D g2d = (Graphics2D) g.create();
        g.setColor(Color.BLACK);
        Graphics2D g2d2 = (Graphics2D) g.create();
        g2d2.drawString("Choose game level: ", 300, 150);

        if(score > 0){
            g2d2.drawString("LAST SCORE: "+ score, 300, 30);
        }

    }

}
