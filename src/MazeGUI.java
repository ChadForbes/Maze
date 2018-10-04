import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class MazeGUI extends Application {
    int imgSize = 32;
    ArrayList<Image> player = new ArrayList<>();
    int[][] mazelayout =
                   {{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
                    {0,0,1,1,1,1,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,0},
                    {0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
                    {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    Maze maze = new Maze(mazelayout);
    GridPane mazeP = new GridPane();
    Image ground = new Image("file:ground.png");
    Image step = new Image("file:stepground.png");
    Image wall = new Image("file:wall.png");
    Image win = new Image("file:winner.png");
    StackPane stackPane = new StackPane();


    public static void main (String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox hb =  new HBox();
        loadImages();
        updatemap();
        ambient();

        mazeP.setAlignment(Pos.CENTER);
        ImageView w = new ImageView(win);
        w.setFitHeight(.1);
        w.setFitWidth(.1);

        // Scaling the victory image
        ScaleTransition scTran
                = new ScaleTransition(Duration.millis(2000), w);
        scTran.setToX(7000f);
        scTran.setToY(7000f);
        scTran.setCycleCount(1);


        // handling the events when clicked
        Button btStep = new Button("Take Step");
        btStep.setOnMouseClicked(e ->
        {
            if(!maze.CheckExit()) {
                maze.takeStep();
                updatemap();
            }
            if (maze.isFinished()){
                scTran.play();
                pauseAmbient();
                win();
            }


        });


        Button btFinish = new Button("Show Path");
        btFinish.setOnMouseClicked(e ->
        {
            maze.findExit();
            updatemap();
            pauseAmbient();
            win();
            scTran.play();
        });

        //Closes the program
        Button btQuit = new Button("Quit");
        btQuit.setOnMouseClicked(e ->
        {
            System.exit(1);
        });


        hb.getChildren().addAll(btStep,btFinish,btQuit);
        stackPane.getChildren().add(w);
        hb.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(0,mazeP);
        VBox vb = new VBox();

        // binding the width and height
        mazeP.prefWidthProperty().bind(vb.widthProperty());
        mazeP.prefHeightProperty().bind(vb.heightProperty());

        vb.getChildren().addAll(stackPane,hb);
        vb.setSpacing(0);
        vb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vb,1200,900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze");
        primaryStage.show();

    }

    String musicFile = "Doom2.mp3";
    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public void ambient(){
        mediaPlayer.play();
    }

    public void pauseAmbient(){
        mediaPlayer.pause();
    }

    public void win(){
        String musicFile = "mushroom.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }


    public void loadImages(){
        player.add(new Image("file:playerUp.gif"));
        player.add(new Image("file:playerLeft.gif"));
        player.add(new Image("file:playerDown.gif"));
        player.add(new Image("file:playerRight.gif"));
    }

    public int getFacing(){
        if (maze.getDirection().equals("North")) {
            return 0;
        } else if (maze.getDirection().equals("East")) {
            return 3;
        } else if (maze.getDirection().equals("South")) {
            return 2;
        } else {
            return 1;
        }
    }

    public void updatemap() {
        for (int i = 0; i < mazelayout.length; i++) {
            for (int j = 0; j < mazelayout[i].length; j++) {
                if (maze.getCell(i, j) == 0) {
                    ImageView wallV = new ImageView(wall);
                    wallV.setFitHeight(imgSize);
                    wallV.setFitWidth(imgSize);
                    mazeP.add(wallV, j, i);

                }else
                if (maze.getCell(i, j) == 1) {
                    ImageView groundV = new ImageView(ground);
                    groundV.setFitHeight(imgSize);
                    groundV.setFitWidth(imgSize);
                    mazeP.add(groundV, j, i);

                }else
                if (maze.getCell(i, j) == 3) {
                    ImageView iv = new ImageView(step);
                    iv.setFitHeight(imgSize);
                    iv.setFitWidth(imgSize);
                    mazeP.add(iv, j, i);

                }else
                if (maze.getCell(i, j) == 4) {
                    ImageView iv = new ImageView(player.get(getFacing()));
                    iv.setFitHeight(imgSize);
                    iv.setFitWidth(imgSize);
                    mazeP.add(iv, j, i);
                }
            }
        }
    }

}