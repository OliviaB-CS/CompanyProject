//Angelina Chen
//Olivia Budiarto
//Alisa Liao
//Seher Subedar
//Hana Shah
//Period 1

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.image.BufferedImage;
import java.io.File;

public class blindBox extends Application {

    //prizes and their probabilities
    private static final String[] PRIZES = {"Principal Perez", "Snoopy", "Mr. Lau", "Mr. Jan", "Senior", "Freshman Boys"};
    private static final double[] PROBABILITIES = {0.17, 0.24, 0.35, 0.40, 0.55, 0.65};

    //image paths
    private static final String SPLASH_IMAGE = "C:/Company/src/Art/openingscene.png";
    private static final String BUTTON_IMAGE = "C:/Company/src/Art/button.png";
    private static final String[] PRIZE_IMAGES = {
            "C:/Company/src/Art/perez.png",
            "C:/Company/src/Art/snoopy.png",
            "C:/Company/src/Art/lau.png",
            "C:/Company/src/Art/jan.png",
            "C:/Company/src/Art/senior.png",
            "C:/Company/src/Art/freshmen.png"
    };
    private static final String[] PRIZE_POWERS = {
            "C:/Company/src/Art/perezpower.png",
            "C:/Company/src/Art/snoopyscary.png",
            "C:/Company/src/Art/laupower.png",
            "C:/Company/src/Art/janfire.png",
            "C:/Company/src/Art/seniorpower.png",
            "C:/Company/src/Art/freshmen.png"
    };

    //prize backgrounds
    private static final String[] PRIZE_BG = {
            "#F8CFCF",
            "#FFB4B4",
            "#B08E8E",
            "#D1F1D2",
            "#C0DABE",
            "#A0D4A1"

    };
    private static final String[] POWER_BG = {
            "#FF6DA5",
            "#383838",
            "#92D67F",
            "#C27C39",
            "#C0DABE",
            "#88C689"
    };

    //main scene
    private BorderPane root;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showSplashScreen();
    }

    private void showSplashScreen() {
        //load splash image
        Image splashImage = loadImage(SPLASH_IMAGE, "Splash screen not found!");
        ImageView splashView = new ImageView(splashImage);
        splashView.setFitWidth(800);
        splashView.setPreserveRatio(true);

        BorderPane splashPane = new BorderPane(splashView);
        Scene splashScene = new Scene(splashPane, 800, 600);
        splashPane.setStyle("-fx-background-color: #2F493A;"); //match bg w/ image

        primaryStage.setScene(splashScene);
        primaryStage.setTitle("Blind Box Game - Splash Screen");
        primaryStage.show();

        //transition to main screen after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> showMainGameScreen());
        delay.play();
    }

    private void showMainGameScreen() {
        //background
        root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: #2F493A;");

        //button to open box
        Button openBoxButton = createButton(BUTTON_IMAGE, 200);
        openBoxButton.setOnAction(event -> boxOpening());

        root.setCenter(openBoxButton); //position button
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blind Box Game - Main Screen"); //tab title
        primaryStage.show();
    }

    private void boxOpening() {
        root.setCenter(null); //remove button
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5)); //1.5 second delay
        pause.setOnFinished(e -> {
            //collect prize data
            String prize = determinePrize();
            int prizeIndex = getPrizeIndex(prize);
            String prizeImagePath = PRIZE_IMAGES[prizeIndex];

            //start the animation and pass the prize image path
            Animation.startAnimation(this, prizeImagePath, prizeIndex);
        });
        pause.play();
    }

    //display the prize image in the BlindBox window
    public void showPrize(BufferedImage prizeImage, int prizeIndex, String prizeImagePath) {
        //load prize image
        Image fxImage = SwingFXUtils.toFXImage(prizeImage, null);
        ImageView prizeImageView = new ImageView(fxImage);
        prizeImageView.setFitWidth(700);
        prizeImageView.setPreserveRatio(true);

        BorderPane prizePane = new BorderPane(prizeImageView);
        Scene prizeScene = new Scene(prizePane, 950, 590); //image sizing
        prizePane.setStyle("-fx-background-color: " + PRIZE_BG[prizeIndex] + ";"); //match bg w/ prize
        primaryStage.setScene(prizeScene);
        primaryStage.setTitle("Blind Box Game - Prize Reveal"); //tab title

        //create buttons
        Button powerButton = createButton("C:/Company/src/Art/powerup.png", 111);
        Button exitButton = createButton("C:/Company/src/Art/exit.png", 111);
        exitButton.setOnAction(event -> System.exit(0)); //exit button action

        //set power button action based on prize
        setPowerButtonAction(powerButton, prizeIndex, prizePane);

        //layout for buttons
        prizePane.setLeft(powerButton);
        prizePane.setRight(exitButton);
        BorderPane.setAlignment(powerButton, Pos.BOTTOM_LEFT);
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);

        System.out.println("You won: " + PRIZES[prizeIndex] + "!");
    }

    private Button createButton(String imagePath, double width) {
        Button button = new Button();
        Image buttonImage = loadImage(imagePath, "Button image not found!");
        ImageView buttonView = new ImageView(buttonImage);
        buttonView.setFitWidth(width);
        buttonView.setPreserveRatio(true);
        button.setGraphic(buttonView); //set button image
        button.setStyle("-fx-background-color: transparent;");
        return button;
    }

    private void setPowerButtonAction(Button button, int index, BorderPane pane) {
        button.setOnAction(event -> {
            pane.setCenter(null); //remove og prize image
            // Load the power-up image
            Image powerImage = loadImage(blindBox.PRIZE_POWERS[index], "Can't power up!");
            ImageView powerView = new ImageView(powerImage); //image -> node = can be given to pane
            powerView.setFitWidth(710);
            powerView.setPreserveRatio(true);
            pane.setCenter(powerView); //set power image as main image
            // Set the background color
            String bgColor = blindBox.POWER_BG[index];
            pane.setStyle("-fx-background-color: " + bgColor + ";");
        });
    }

    private String determinePrize() {
        double random = Math.random();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < PROBABILITIES.length; i++) { //find first probability greater than random #
            cumulativeProbability += PROBABILITIES[i];
            if (random <= cumulativeProbability)
                return PRIZES[i];
        }
        return "No Prize";
    }

    private int getPrizeIndex(String prize) {
        for (int i = 0; i < PRIZES.length; i++) {
            if (PRIZES[i].equals(prize))
                return i; //find index of prize; prize index same across all arrays
        }
        System.out.println("Prize not recognized: " + prize);
        return 0;
    }

    private Image loadImage(String filePath, String errorMessage) {
        try {
            File file = new File(filePath); //try to find file based on path
            if (!file.exists()) throw new IllegalArgumentException(errorMessage);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            System.out.println(errorMessage); //if file does not exist
            return new Image("C:/Company/src/Art/closingscene.png");
        }
    }
}
