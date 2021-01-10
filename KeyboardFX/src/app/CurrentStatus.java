package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CurrentStatus {
    protected AnchorPane anPain;
    protected ImageView theImage;
    protected boolean ON = false;
    protected int volume = 5;
    protected int instrument = 0;
    protected final String[] instruments = { "PIANO", "E.PIANO", "ORGAN", "HARPE", "STRINGS" };
    protected List<Clip> soundClips = new ArrayList<>();
    private double initialWidth = 1284;
    private static final String[] colours = {"linear-gradient(to right, #c31432, #240b36);", "linear-gradient(to right, #34e89e, #0f3443);",
                                            "linear-gradient(to right, #6190E8, #A7BFE8);", "linear-gradient(to right, #44A08D, #093637);",
                                            "linear-gradient(to right, #43C6AC, #F8FFAE);", "linear-gradient(to right, #FFAFBD, #ffc3a0);",
                                            "linear-gradient(to right, #8CBC00, #636FA4);", "linear-gradient(to right, #c0c0aa, #1cefff);"};
    private static int currentColour = 1;

    public void setAnchorAndImage(AnchorPane anPain, ImageView image) {
        this.anPain = anPain;
        this.theImage = image;
    }

    public final Mainframe.ButtonData[] buttons = { // buttons placements
            new Mainframe.ButtonData(320, 83, 339, 104, 0), // Mainframe.ButtonData power
            new Mainframe.ButtonData(357, 75, 374, 91, 1), // Mainframe.ButtonData volume up
            new Mainframe.ButtonData(357, 98, 374, 113, 2), // Mainframe.ButtonData volume down

            new Mainframe.ButtonData(395, 83, 416, 105, 3), // Mainframe.ButtonData instrument 0
            new Mainframe.ButtonData(422, 83, 442, 105, 4), // Mainframe.ButtonData instrument 1
            new Mainframe.ButtonData(449, 83, 468, 105, 5), // Mainframe.ButtonData instrument 2
            new Mainframe.ButtonData(474, 83, 494, 105, 6), // Mainframe.ButtonData instrument 3
            new Mainframe.ButtonData(501, 83, 520, 105, 7), // Mainframe.ButtonData instrument 4

            new Mainframe.ButtonData(557, 83, 578, 105, 8), // Mainframe.ButtonData dummy
            new Mainframe.ButtonData(607, 83, 628, 105, 9), // Mainframe.ButtonData dummy
            new Mainframe.ButtonData(634, 83, 654, 105, 10), // Mainframe.ButtonData dummy
            new Mainframe.ButtonData(661, 83, 680, 105, 11), // Mainframe.ButtonData dummy
            new Mainframe.ButtonData(701, 83, 721, 105, 12), // Mainframe.ButtonData dummy
            new Mainframe.ButtonData(727, 83, 747, 105, 13), // Mainframe.ButtonData dummy

            new Mainframe.ButtonData(61, 90, 14), // Mainframe.ButtonData white
            new Mainframe.ButtonData(80, 96, 15), // Mainframe.ButtonData black
            new Mainframe.ButtonData(91, 124, 16), // Mainframe.ButtonData white
            new Mainframe.ButtonData(119, 136, 17), // Mainframe.ButtonData black
            new Mainframe.ButtonData(125, 155, 18), // Mainframe.ButtonData white
            new Mainframe.ButtonData(156, 186, 19), // Mainframe.ButtonData white
            new Mainframe.ButtonData(174, 191, 20), // Mainframe.ButtonData black
            new Mainframe.ButtonData(187, 219, 21), // Mainframe.ButtonData white
            new Mainframe.ButtonData(211, 227, 22), // Mainframe.ButtonData black
            new Mainframe.ButtonData(220, 252, 23), // Mainframe.ButtonData white
            new Mainframe.ButtonData(249, 265, 24), // Mainframe.ButtonData black
            new Mainframe.ButtonData(253, 283, 25), // Mainframe.ButtonData white

            new Mainframe.ButtonData(284, 315, 26), // Mainframe.ButtonData
            new Mainframe.ButtonData(304, 320, 27), // Mainframe.ButtonData black
            new Mainframe.ButtonData(316, 347, 28), // Mainframe.ButtonData
            new Mainframe.ButtonData(342, 360, 29), // Mainframe.ButtonData black
            new Mainframe.ButtonData(348, 379, 30), // Mainframe.ButtonData
            new Mainframe.ButtonData(380, 410, 31), // Mainframe.ButtonData
            new Mainframe.ButtonData(400, 416, 32), // Mainframe.ButtonData black
            new Mainframe.ButtonData(411, 443, 33), // Mainframe.ButtonData
            new Mainframe.ButtonData(436, 453, 34), // Mainframe.ButtonData black
            new Mainframe.ButtonData(444, 477, 35), // Mainframe.ButtonData
            new Mainframe.ButtonData(473, 491, 36), // Mainframe.ButtonData black
            new Mainframe.ButtonData(478, 509, 37), // Mainframe.ButtonData

            new Mainframe.ButtonData(510, 539, 38), // Mainframe.ButtonData
            new Mainframe.ButtonData(529, 546, 39), // Mainframe.ButtonData black
            new Mainframe.ButtonData(540, 572, 40), // Mainframe.ButtonData
            new Mainframe.ButtonData(569, 586, 41), // Mainframe.ButtonData black
            new Mainframe.ButtonData(573, 605, 42), // Mainframe.ButtonData
            new Mainframe.ButtonData(606, 637, 43), // Mainframe.ButtonData
            new Mainframe.ButtonData(624, 642, 44), // Mainframe.ButtonData black
            new Mainframe.ButtonData(638, 670, 45), // Mainframe.ButtonData
            new Mainframe.ButtonData(662, 678, 46), // Mainframe.ButtonData black
            new Mainframe.ButtonData(671, 702, 47), // Mainframe.ButtonData
            new Mainframe.ButtonData(698, 716, 48), // Mainframe.ButtonData black
            new Mainframe.ButtonData(703, 735, 49), // Mainframe.ButtonData

            new Mainframe.ButtonData(736, 766, 50), // Mainframe.ButtonData
            new Mainframe.ButtonData(755, 773, 51), // Mainframe.ButtonData black
            new Mainframe.ButtonData(767, 800, 52), // Mainframe.ButtonData
            new Mainframe.ButtonData(795, 813, 53), // Mainframe.ButtonData black
            new Mainframe.ButtonData(801, 832, 54), // Mainframe.ButtonData
            new Mainframe.ButtonData(833, 863, 55), // Mainframe.ButtonData
            new Mainframe.ButtonData(851, 870, 56), // Mainframe.ButtonData black
            new Mainframe.ButtonData(864, 898, 57), // Mainframe.ButtonData
            new Mainframe.ButtonData(888, 906, 58), // Mainframe.ButtonData black
            new Mainframe.ButtonData(899, 929, 59), // Mainframe.ButtonData
            new Mainframe.ButtonData(925, 943, 60), // Mainframe.ButtonData black
            new Mainframe.ButtonData(930, 962, 61), // Mainframe.ButtonData

            new Mainframe.ButtonData(963, 994, 62), // Mainframe.ButtonData
            new Mainframe.ButtonData(982, 1000, 63), // Mainframe.ButtonData black
            new Mainframe.ButtonData(995, 1027, 64), // Mainframe.ButtonData
            new Mainframe.ButtonData(1021, 1041, 65), // Mainframe.ButtonData black
            new Mainframe.ButtonData(1028, 1059, 66), // Mainframe.ButtonData
            new Mainframe.ButtonData(1060, 1090, 67), // Mainframe.ButtonData
            new Mainframe.ButtonData(1078, 1096, 68), // Mainframe.ButtonData black
            new Mainframe.ButtonData(1091, 1125, 69), // Mainframe.ButtonData
            new Mainframe.ButtonData(1116, 1134, 70), // Mainframe.ButtonData black
            new Mainframe.ButtonData(1126, 1158, 71), // Mainframe.ButtonData
            new Mainframe.ButtonData(1153, 1172, 72), // Mainframe.ButtonData black
            new Mainframe.ButtonData(1159, 1189, 73), // Mainframe.ButtonData

            new Mainframe.ButtonData(1190, 1222, 74) }; // Mainframe.ButtonData

    public void powerSwitch() {
        this.ON = !this.ON;
        if (this.ON) {
            System.out.println("Turning ON");
            this.volume = 5;
            this.instrument = 0;
        } else {
            System.out.println("Turning OFF");
            this.volume = 0;
        }
    }

    public void volumeUp() {
        if (this.volume < 10 && this.ON)
            this.volume++;
    }

    public void volumeDown() {
        if (this.volume > 0 && this.ON)
            this.volume--;
    }

    public void setInstrument(int option) {
        if (!this.ON)
            return;
        if (option < 0)
            option = 0;
        if (option > 4)
            option = 4;
        this.instrument = option;

        try {
            updateSoundsLibrary();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void updateSoundsLibrary() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        soundClips.clear();
        for (int i = 1; i < 62; i++) {
            File file = new File(String.format("src/app/pianoSounds%d/sound%d.wav", this.instrument, i));
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundClips.add(clip);
        }
    }

    public int getRelativeCoordinate(double width, double xory) {
        return (int) (xory * (initialWidth / width));
    }

    public int getButtonXorYInNewSpace(double width, double xory) {
        return (int) (xory * (width / initialWidth));
    }

    public void shadeButton(int buttonNo, double seconds) {
        Button btn = new Button();
        SimpleRelativeShape shp = new SimpleRelativeShape(buttons[buttonNo]);
        double x = shp.leftX;
        double y = shp.upY;
        AnchorPane.setLeftAnchor(btn, x);
        AnchorPane.setTopAnchor(btn, y);
        double width = shp.rightX - x;
        double height = shp.downY - y;
        btn.setMaxHeight(height);
        btn.setPrefHeight(height);
        btn.setMinHeight(height);
        btn.setMaxWidth(width);
        btn.setPrefWidth(width);
        btn.setMinWidth(width);
        btn.setStyle("-fx-background-color:blue; -fx-opacity:30%;");
        anPain.getChildren().add(btn);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long) (seconds * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new ButtonShadeOff(btn));
            }
        });
        t.setDaemon(true);
        t.start();

    }

    public void changeBackgroundColour() {
        anPain.setStyle("-fx-background-color: " + colours[currentColour]);
        currentColour++;
        if (currentColour == colours.length) currentColour = 0;
    }

    private class ButtonShadeOff implements Runnable {
        Button btn;

        ButtonShadeOff(Button btn) {
            this.btn = btn;
        }

        @Override
        public void run() {
            anPain.getChildren().remove(btn);
        }
    }

    public class SimpleRelativeShape {
        double leftX;
        double rightX;
        double upY;
        double downY;

        SimpleRelativeShape(Mainframe.ButtonData btn) {
            leftX = getButtonXorYInNewSpace(theImage.getFitWidth(), btn.xLeft);
            rightX = getButtonXorYInNewSpace(theImage.getFitWidth(), btn.xRight);
            upY = getButtonXorYInNewSpace(theImage.getFitWidth(), btn.yUp);
            downY = getButtonXorYInNewSpace(theImage.getFitWidth(), btn.yDown);
        }
    }
}