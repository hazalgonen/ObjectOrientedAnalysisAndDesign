package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Random;

/**
 * Created by asus on 27/10/2017.
 */
public class JobOffers {
    Circle myJob;

    public JobOffers(){
        createJob();
    }
    public void createJob() {
        Circle temp=new Circle();
        Random generator= new Random();
        int x = generator.nextInt(960);
        int y= generator.nextInt(600);
        if((x/40)*40==0)
            x=20;
        if((y/40)*40==0)
            y=20;

        temp.setCenterX(((x/40)*40)+20);
        temp.setCenterY(((y/40)*40)+20);
        temp.setRadius(20);

        temp.setFill(Color.LIGHTGRAY);
        myJob=temp;

    }
}
