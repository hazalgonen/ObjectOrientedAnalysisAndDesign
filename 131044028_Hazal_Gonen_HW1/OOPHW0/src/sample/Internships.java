package sample;

/**
 * Created by asus on 27/10/2017.
 */
public class Internships extends MyDecorator{
    JobHunt jobhunt;
    public Internships(JobHunt tempjobhunt){
        jobhunt = tempjobhunt;
    }

    @Override
    public double getScoreMultiplier() {
        return jobhunt.getScoreMultiplier()*1.5;
    }

    @Override
    public String getDescription() {
        return jobhunt.getDescription() + "-" + "Internships";
    }

}

