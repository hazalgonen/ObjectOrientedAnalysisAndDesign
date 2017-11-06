package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Main extends Application {

    private Food food;          //Food classi tipinde food
    private JobHunt myPlayer;   //myPlayer
    private JobOffers job;      //job
    JobHunt temp;
    private Stage stage;
    private Pane root1=new Pane();      //stagee yuklenir oyun ekrani icindir
    private Parent root;                //stagee yuklenir baslangic ekrani  icindir

    private Button newGameButton;       //fxml dosyasi yuklenirken kullanilir
    private Button chooseCharButton;    //fxml dosyasi yuklenirken kullanilir
    private Button exitButton;          //fxml dosyasi yuklenirken kullanilir
    private Button button;              //fxml dosyasi yuklenirken kullanilir
    private Label timerLabel;           //fxml dosyasi yuklenirken kullanilir

    public enum Direction {
        UP,DOWN,LEFT,RIGHT
    }           //klavyeden basilan son tusun kontrolunde kullanilir
    private Direction direction=Direction.RIGHT; //oyun basladiginda saga dogru gitmesi icindir

    private int XSIZE=1000;
    private int YSIZE=710;
    private Text multiplierScore;       //carpan icin kullanilir
    private Text stamina;               //stamina icin kullanilir
    private Text score;                 //score icin kullanilir
    private AnimationTimer timer;       //timer ayarlamak icin kullanilir
    private int indexfood=0;            //food uzerinde yazi yazmasi icin kullanilir
    private int staminaTime=50;         //stamina baslangic degeri 50dir
    private Timeline timeline = new Timeline();//timeline ayarlamak icin kullanilir
    private static final Integer LimitedAmountOfTimeForJob = 10;//job 10 saniyede 1 cikar
    private static final Integer LimitedAmountOfTimeForFood = 15; // food 15 saniyede 1 cikar
    private static final Integer STARTTIME = 0;                     //baslangic zamani
    private int jobCreateTime=0;        //jobun son yaratilma zamani
    private int foodCreateTime=0;        //foodun son yaratilma zamani

    private Integer timeSeconds = STARTTIME;
    private int count1=0;       //saniye ayarlamak icin kullanilir
    private int round=0;        //round sayisi icin kullanilir
    private Circle decFood;             //decorator
    private Text textforFood;           //foodun uzerinde yazi yazar
    private Text textforDec;            //decoratorun uzerinde yazi yazar
    private double multiply;            //multiply hesabinda kullanilir
    private double mytime=0.2;          //baslangicta oyun zamanidir
    @Override


    //baslangicta giris ekrani yuklenir
    //
    public void start(Stage primaryStage) throws Exception{

        root = FXMLLoader.load(getClass().getResource("sample.fxml")); //yuklenir
        stage=primaryStage;                                 //gelen stage her fonksiyonda ulasilmasi icin membera atanir
        stage.setScene(new Scene(root,XSIZE,YSIZE));        //scene stagee yuklenir
        stage.setResizable(false);                          //boyutu degistirilmesin
        stage.show();                                       //ekranda gosterilsin

        setSample();        //giris ekranindadi butonlarin atamasi yapilir
        direct();           //timelinein basladigi kisma girilir

        newGameButton.setOnAction(new EventHandler<ActionEvent>() { //newGame butonuna basilmasi
            @Override
            public void handle(ActionEvent event) {
                if(myPlayer==null) {
                    stage.close();
                }
                playGame();     //oyun baslama methodu
            }
        });
        chooseCharButton.setOnAction(new EventHandler<ActionEvent>() {//karakter secme butonu
            @Override
            public void handle(ActionEvent event) {

                if(chooseCharButton.getText().equals("Python")){
                    chooseCharButton.setText("Anaconda");
                    myPlayer = new Anaconda();
                    myPlayer.setScore(0);

                }
                else if(chooseCharButton.getText().equals("Anaconda")){
                    chooseCharButton.setText("Python");
                    myPlayer = new Python();
                    myPlayer.setScore(0);

                }
                else if(chooseCharButton.getText().equals("Choose Character")){
                    chooseCharButton.setText("Python");
                    myPlayer = new Python();
                    myPlayer.setScore(0);

                }
            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() { //exite basilirsa ekran kapansin
            @Override
            public void handle(ActionEvent event) {
               stage.close();
            }
        });
    }

    private void stopGame(){ //ekrani kapatir

        timeline.stop();
        stage.close();
    }

    private void playGame(){

        try {
            root1  =  FXMLLoader.load(getClass().getResource("playGame.fxml"));
            setPlayGame();      //playGame fxmlinde olan butonlari ekler
            job=new JobOffers();    //yeni job
            root1.getChildren().addAll(job.myJob); //ekrana ekler
            jobCreateTime=timeSeconds;      //zamanlari initialize eder
            foodCreateTime=timeSeconds;     //zamanlari initialize eder

            for(int i=0;i<myPlayer.player.size();++i){ //yilani ekrana ekler
                root1.getChildren().add(myPlayer.player.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopGame();   //quite basilirsa oyunu durdurur
            }
        });


        timeline.play();

        stage.setScene(new Scene(root1, XSIZE,YSIZE));
        stage.setResizable(false);
        stage.show();
        eatFood();
        eatJob();
        eatDec();
    }

    private void getKeyCode(){


        stage.getScene().setOnKeyPressed(event -> {

            switch (event.getCode()){
                case RIGHT :
                    if(direction!=Direction.LEFT) {

                        direction=Direction.RIGHT;
                    }
                    break;
                case LEFT :
                    if(direction!=Direction.RIGHT) {

                        direction=Direction.LEFT;
                    }
                    break;
                case UP :
                    if(direction!=Direction.DOWN) {

                        direction=Direction.UP;
                    }
                    break;
                case DOWN :
                    if(direction!=Direction.UP) {
                        direction=Direction.DOWN;
                    }
                    break;
            }
        });
    }
    public void createDecShape(int choice){
        Circle decShape= new Circle();
        Random generator= new Random();
        int x = generator.nextInt(XSIZE);
        int y= generator.nextInt(600);
        if((x/40)*40==0)
            x=40;
        if((y/40)*40==0)
            y=40;

        decShape.setCenterX((((x/40)*40)+20));
        decShape.setCenterY((((y/40)*40)+20));

        decShape.setRadius(20);


        decShape.setFill(Color.RED);

        String desc="nothing";
        if(choice==0){
            desc =  "ForeignLanguage";
        }
        else if(choice==1){
            desc =  "Internships";
        }
        else if(choice==2){

            desc =  "TrainingCertificates";
        }

        textforDec= new Text(x-10,y, desc + "");

        decFood=decShape;
        root1.getChildren().addAll(textforDec,decFood);

    }
    public void direct(){
        KeyFrame frame=new KeyFrame(Duration.seconds(mytime), event -> {
            ++count1;
            if(count1%5==0){
                ++timeSeconds;
                staminaTime--;
                stamina.setText(staminaTime+"");
                count1=0;
            }
            if((timeSeconds-jobCreateTime)% LimitedAmountOfTimeForJob ==0 && count1==0){ //10 saniye gorunecek

                 if(job==null) {
                     job=new JobOffers();
                     root1.getChildren().addAll(job.myJob);
                     jobCreateTime=timeSeconds;
                 }
                 else  {
                     root1.getChildren().remove(job.myJob);
                     job.createJob();
                     root1.getChildren().add(job.myJob);
                 }
            }

            if((timeSeconds-foodCreateTime)% LimitedAmountOfTimeForFood ==0 && count1==0){ //LimitedAmountOfTimeForFood saniye gorunecek

                if(food==null) {
                    food=new Food();
                    indexfood++;
                    textforFood= new Text(food.myFood.getCenterX()-10, food.myFood.getCenterY(), "food");
                    root1.getChildren().addAll(food.myFood,textforFood);
                    foodCreateTime=timeSeconds;
                }
                else  {
                    root1.getChildren().remove(food.myFood);
                    root1.getChildren().remove(textforFood);
                    food.createFood();
                    textforFood= new Text(food.myFood.getCenterX()-10, food.myFood.getCenterY(), "food");
                    root1.getChildren().addAll(food.myFood,textforFood);

                    indexfood++;


                }
            }
            if(staminaTime==0){
                stopGame();
            }

            if(round%5==0 && round!=0){
                mytime= mytime-0.1;
                temp=myPlayer;
                Random generator= new Random();
                int choice = generator.nextInt(3);
                if(choice==0){
                    myPlayer = new ForeignLanguage(myPlayer);
                }
                else if(choice==1){
                    myPlayer = new Internships(myPlayer);
                }
                else if(choice==2){
                    myPlayer = new TrainingCertificates(myPlayer);
                }

                myPlayer.player=temp.player;
                myPlayer.setScore(temp.getScore());

                createDecShape(choice);

                multiply=temp.getScoreMultiplier();

                myPlayer.setScoreMultiplier(temp.getScoreMultiplier());
                round=0;
            }

            goStraight();

            timerLabel.setText(timeSeconds.toString());
            timerLabel.setTextFill(Color.RED);
            getKeyCode();
            eatJob();
            eatFood();
            eatDec();
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

    }
    private void eatFood(){
        int countforFoor=0;
        if(food!=null){
            if(food.myFood.getCenterX()==myPlayer.getHeadX() && food.myFood.getCenterY()==myPlayer.getHeadY()){
                foodCreateTime=timeSeconds;
                textforFood.setText("");
                root1.getChildren().remove(food.myFood);
                staminaTime+=10;
                stamina.setText(staminaTime+"");

                countforFoor++;
            }
            for(int i=myPlayer.player.size()-1; i>1 ;--i){
                if(food.myFood.getCenterX()==myPlayer.player.get(i).getCenterX() && food.myFood.getCenterY()==myPlayer.player.get(i).getCenterY()){
                    foodCreateTime=timeSeconds;
                    textforFood.setText("");
                    root1.getChildren().remove(food.myFood);

                    countforFoor++;

                }
            }
        }
        if(countforFoor>0)
            food=null;
    }

    private void eatJob(){
        if(job!=null){
            if(job.myJob.getCenterX()==myPlayer.getHeadX() && job.myJob.getCenterY()==myPlayer.getHeadY()){
                job.myJob.setFill(Color.PINK);
                myPlayer.player.add(job.myJob);
                job.createJob();
                jobCreateTime=timeSeconds;
                root1.getChildren().add(job.myJob);

                if(temp!=null)
                    myPlayer.setScore(myPlayer.getScore()+ (int)(10*temp.getScoreMultiplier()));
                else
                    myPlayer.setScore(myPlayer.getScore()+ (int)(10*myPlayer.getScoreMultiplier()));
                score.setText(myPlayer.getScore() + "");
                ++round;
            }
            for(int i=myPlayer.player.size()-1; i>1 ;--i){

                if(job.myJob.getCenterX()==myPlayer.player.get(i).getCenterX() && job.myJob.getCenterY()==myPlayer.player.get(i).getCenterY()){
                    job.myJob.setFill(Color.PINK);
                    myPlayer.player.add(job.myJob);
                    job.createJob();
                    jobCreateTime=timeSeconds;
                    root1.getChildren().add(job.myJob);
                    myPlayer.setScore(myPlayer.getScore()+ (int)(10*myPlayer.getScoreMultiplier()));
                    score.setText(myPlayer.getScore() + "");
                    ++round;

                }
            }
        }
    }

    private void eatDec(){
        if(decFood!=null){
            if(decFood.getCenterX()==myPlayer.getHeadX() && decFood.getCenterY()==myPlayer.getHeadY()){
                textforDec.setText("");
                root1.getChildren().remove(decFood);
                myPlayer.setScoreMultiplier(myPlayer.getScoreMultiplier());
                multiplierScore.setText(myPlayer.getScoreMultiplier()+ "X");
                temp=myPlayer;
            }
            for(int i=myPlayer.player.size()-1; i>1 ;--i){
                if(decFood.getCenterX()==myPlayer.player.get(i).getCenterX() && decFood.getCenterY()==myPlayer.player.get(i).getCenterY()){
                    textforDec.setText("");
                    root1.getChildren().remove(decFood);
                    myPlayer.setScoreMultiplier(myPlayer.getScoreMultiplier());
                    multiplierScore.setText(myPlayer.getScoreMultiplier()+ "X");
                    temp=myPlayer;
                }
            }
        }
    }

    private  void goStraight(){
        eatJob();
        eatDec();
        isEndGame();

        for(int i=myPlayer.player.size()-1; i>0 ;--i){
            myPlayer.player.get(i).setCenterX(myPlayer.player.get(i-1).getCenterX());
            myPlayer.player.get(i).setCenterY(myPlayer.player.get(i-1).getCenterY());
        }


        switch (direction){
            case UP:
                myPlayer.player.get(0).setCenterX(myPlayer.getHeadX());
                myPlayer.player.get(0).setCenterY(myPlayer.getHeadY()-40);
                break;

            case DOWN:
                myPlayer.player.get(0).setCenterX(myPlayer.getHeadX());
                myPlayer.player.get(0).setCenterY(myPlayer.getHeadY()+40);
                break;
            case LEFT:
                myPlayer.player.get(0).setCenterX(myPlayer.getHeadX()-40);
                myPlayer.player.get(0).setCenterY(myPlayer.getHeadY());
                break;
            case RIGHT:
                myPlayer.player.get(0).setCenterX(myPlayer.getHeadX()+40);
                myPlayer.player.get(0).setCenterY(myPlayer.getHeadY());
                break;

        }

    }


    private void isEndGame()
    {
        for(int i=1; i<myPlayer.player.size()-1;++i){
            if(myPlayer.player.get(0).getCenterX() == myPlayer.player.get(i).getCenterX()
                    && myPlayer.player.get(0).getCenterY() == myPlayer.player.get(i).getCenterY()){

                stopGame();
            }
        }
        if(myPlayer.player.get(0).getCenterX()>=XSIZE || myPlayer.player.get(0).getCenterY()>600 ||
                myPlayer.player.get(0).getCenterX()<20 || myPlayer.player.get(0).getCenterY()<20)
            stopGame();

        if(staminaTime<=0)
            stopGame();

    }
    private void setPlayGame(){

        button=((Button)root1.lookup("#button"));
        multiplierScore=((Text)root1.lookup("#multiplierScore"));
        timerLabel=((Label)root1.lookup("#timerLabel"));
        stamina=((Text)root1.lookup("#stamina"));
        score=((Text)root1.lookup("#score"));
        FlowPane pane = new FlowPane(XSIZE, 600);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(1), BorderStroke.THICK)));

        pane.setPrefSize(XSIZE, 600);
        root1.getChildren().add(pane);
        multiplierScore.setText(myPlayer.getScoreMultiplier()+ "X");
        score.setText(myPlayer.getScore() + "");
        stamina.setText(staminaTime + "");

    }
    private void setSample(){

        newGameButton=((Button)root.lookup("#newGameButton"));
        chooseCharButton=((Button)root.lookup("#chooseCharButton"));
        exitButton=((Button)root.lookup("#exitButton"));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
