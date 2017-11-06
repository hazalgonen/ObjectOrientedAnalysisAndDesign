package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 27/10/2017.
 */
public abstract class JobHunt {
    private int score;
    protected List<Circle> player = new ArrayList<Circle>(); //yilanin kendisi
    protected double scoreMultiplier; //JobHunti extend edenler icin baslangic score carpanini verir
    public String description= "Unknown Snake";
    public abstract double getScoreMultiplier();
    public  String  getDescription()
    {
        return description;
    }

    public void setScoreMultiplier( double scoreMultiplier1)
    {
        scoreMultiplier =scoreMultiplier1;
    }
    public void setScore(int score1)
    {
         score=score1;
    }
    public int getScore()
    {
        return score;
    }

    public double getHeadX(){
        return player.get(0).getCenterX();
    }
    public double getHeadY(){
        return player.get(0).getCenterY();
    }
    public List<Circle> createSnake(int howManyCircle){
        int x=220;
        int y=260;
        Circle newPiece =new Circle(x,y,20);
        newPiece.setFill(Color.PINK);
        player.add(0,newPiece);

        for(int i=1; i<howManyCircle;++i){
            Circle newPiece1 =new Circle(x,y,20);
            newPiece1.setFill(Color.PINK);
            newPiece1.setCenterX(player.get(i-1).getCenterX()-40);
            newPiece1.setCenterY(player.get(i-1).getCenterY());
            player.add(newPiece1);
        }
        return player;
    }

}
