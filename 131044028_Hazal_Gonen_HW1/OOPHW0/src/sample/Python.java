package sample;

/**
 * Created by asus on 27/10/2017.
 */
public class Python extends JobHunt {

    public Python(){
        player= createSnake(1);
        description = "Python";

    }

    @Override
    public double getScoreMultiplier() {
        return 1;
    }
}
