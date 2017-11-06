package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Created by asus on 27/10/2017.
 */
public class Food {
    Circle myFood;

    public Food(){
       createFood();
    }
    public void createFood() {
        Circle food= new Circle();
        Random generator= new Random();
        int x = generator.nextInt(1000);
        int y= generator.nextInt(600);
        if((x/40)*40==0)
            x=40;
        if((y/40)*40==0)
            y=40;

        food.setCenterX((((x/40)*40)+20));
        food.setCenterY((((y/40)*40)+20));
        food.setRadius(20);

        food.setFill(Color.GREEN);

        myFood=  food;

    }
}
