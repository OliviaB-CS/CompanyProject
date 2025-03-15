//Angelina Chen
//Olivia Budiarto
//Alisa Liao
//Seher Subedar
//Hana Shah
//Period 1

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Animation extends JPanel {

    private BufferedImage[] up = new BufferedImage[4];  //array to store claw machine up images
    private BufferedImage[] down = new BufferedImage[4];  //array to store claw machine down images
    private int currentImageIndex = 0;  //track the current image
    private int delay = 1000;  //1 second delay for switching images
    private Timer timer;
    private boolean animationRunning = true;
    public static boolean closeClaw;

    private BufferedImage prizeImage; //image for the prize
    private boolean showPrize = false; //flag to control prize display

    private JFrame animationFrame; //reference to the JFrame containing this panel
    private blindBox blindBox; //reference to the BlindBox class to communicate back

    public Animation(JFrame frame, blindBox blindBox, int prizeIndex, String prizeImagePath) {
        this.animationFrame = frame;
        this.blindBox = blindBox;
        Images();

        //timer to update the image
        //cycles through the claw machine when the claw is up
        timer = new Timer(delay, e -> {
            if (currentImageIndex < 3) {
                currentImageIndex++;
            } else {
                currentImageIndex = 0;
            }
            repaint();
        });
        timer.start();

        //mouse click listener to stop the animation
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //stop the timer when the user clicks on the panel
                timer.stop();
                animationRunning = false; //wwitch image to claw down
                closeClaw = true;
                showPrize = true; //show the prize after animation ends
                repaint(); //repaint to show the prize

                //close the animation window after a short delay
                PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
                pause.setOnFinished(event -> {
                    animationFrame.dispose(); //close the animation window
                    blindBox.showPrize(prizeImage, prizeIndex, prizeImagePath); //pass the prize image back to BlindBox
                });
                pause.play();
            }
        });
    }

    //load the 4 images from files
    private void Images() {
        try {
            up[0] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawup1.png"));
            up[1] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawup2.png"));
            up[2] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawup3.png"));
            up[3] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawup2.png"));
            down[0] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawdown1.png"));
            down[1] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawdown2.png"));
            down[2] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawdown3.png"));
            down[3] = ImageIO.read(new File("C:/Company/src/Art/ClawMachine/clawdown2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPrizeImage(String prizePath) { //verifies image
        try {
            prizeImage = ImageIO.read(new File(prizePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (animationRunning) {
            //if animation is running, draw current image
            g.drawImage(up[currentImageIndex], 0, 0, getWidth(), getHeight(), this);
        } else if (showPrize && prizeImage != null) {
            //if the prize should be shown, draw the prize image
            g.drawImage(down[currentImageIndex], 0, 0, getWidth(), getHeight(), this);
        } else {
            //do not display down animation otherwise
            g.drawImage(up[currentImageIndex], 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void startAnimation(blindBox blindBox, String prizePath, int prizeIndex) {
        JFrame frame = new JFrame("Animation Claw Machine Demo");
        Animation panel = new Animation(frame, blindBox, prizeIndex, prizePath); //trigger animation
        panel.checkPrizeImage(prizePath); //verify image
        frame.add(panel);
        frame.setSize(500, 500);  // Set the size of the window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes tab if event fulfilled
        frame.setVisible(true);
    }
}