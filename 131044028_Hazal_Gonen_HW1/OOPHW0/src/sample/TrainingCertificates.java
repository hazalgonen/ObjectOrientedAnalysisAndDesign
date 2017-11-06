package sample;

/**
 * Created by asus on 27/10/2017.
 */
public class TrainingCertificates extends MyDecorator{
    JobHunt jobhunt;
    public TrainingCertificates(JobHunt tempjobhunt){
        jobhunt = tempjobhunt;
    }

    @Override
    public double getScoreMultiplier() {
        return jobhunt.getScoreMultiplier()*3;
    }

    @Override
    public String getDescription() {
        return jobhunt.getDescription() + "-" + "TrainingCertificates";
    }
}
