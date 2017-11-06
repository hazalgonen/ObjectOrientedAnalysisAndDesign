package sample;

/**
 * Created by asus on 27/10/2017.
 */
public class Anaconda extends JobHunt {

    public Anaconda(){
        player= createSnake(2);
        description = "Anaconda";
    }

    @Override
    public double getScoreMultiplier() {
        return 2;
    }
}
