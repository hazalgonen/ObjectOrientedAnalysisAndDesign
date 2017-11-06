package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

/**
 * Created by asus on 27/10/2017.
 */
public abstract class MyDecorator extends JobHunt{
    Circle myDecorator;

    public  abstract  String getDescription();

    Text textforDec;

    public Text createText(){
        textforDec= new Text(myDecorator.getCenterX()-10, myDecorator.getCenterY(), description);
        return textforDec;
    }

}
