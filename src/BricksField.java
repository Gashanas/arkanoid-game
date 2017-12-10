import bricks.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BricksField {
    private Dimension gameField;
    private String level = "";

    private List<BaseBrick> baseBrickList = new ArrayList<BaseBrick>();

    public BricksField(int amount, Dimension gameField) throws IOException {
        this.gameField = gameField;
    }

    public void setLevel(String level) throws IOException {
        this.level = level;
        setBricksLocations();
    }

    public List<BaseBrick> getBaseBrickList(){
        return baseBrickList;
    }

    public void destroyBrickByIndex(int index){
        baseBrickList.get(index).hitBrick();
    }

    public void setBricksLocations() throws IOException {
        int currentLocationX = 30;
        int currentLocationY = 30;
        BufferedReader br;
        if(this.level != ""){
            br = new BufferedReader(new FileReader(this.level));
        } else {
            br = new BufferedReader(new FileReader("gameField4.txt"));
        }
        try {
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null){
                if(line != null){
                    for (char c : line.toCharArray()) {
                        String type = "" + c;
                        if (!type.equals(" ")) {
                            if (type.equals("1")) {
                                baseBrickList.add(new BaseBrick(currentLocationX, currentLocationY));
                            } else if (type.equals("2")) {
                                baseBrickList.add(new FragileBrick(currentLocationX, currentLocationY));
                            } else if (type.equals("3")) {
                                baseBrickList.add(new MediumBrick(currentLocationX, currentLocationY));
                            } else if (type.equals("4")) {
                                baseBrickList.add(new SolidBrick(currentLocationX, currentLocationY));
                            } else if (type.equals("5")) {
                                baseBrickList.add(new SuperBrick(currentLocationX, currentLocationY));
                            }
                            currentLocationX += baseBrickList.get(0).getDefaultBrickWidth() + 5;
                            if (currentLocationX + baseBrickList.get(0).getDefaultBrickWidth()-30 >= gameField.width) {
                                currentLocationX = 30;
                                currentLocationY += baseBrickList.get(0).getDefaultBrickHeight() + 5;
                            }
                        }
                    }
                };
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } finally {
            br.close();
        }

    }
}
