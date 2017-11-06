package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Created by asus on 27/10/2017.
 */
public class ForeignLanguage extends MyDecorator{
    JobHunt jobhunt;

    public ForeignLanguage(JobHunt tempjobhunt){

        this.jobhunt = tempjobhunt;
    }
    @Override
    public String getDescription() {
        return jobhunt.getDescription() + "-" + "ForeignLanguage";
    }

    @Override
    public double getScoreMultiplier() {
        return jobhunt.getScoreMultiplier() *2;
    }

}
