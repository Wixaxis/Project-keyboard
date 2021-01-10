package app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.sound.sampled.*;

public class Mainframe {
    
    private int calibration = 0;
    private static final boolean CALIBRATION_MODE = false;
    private CurrentStatus currStat = new CurrentStatus();
    private SoundPlayer soundPlayer = SoundPlayer.getSoundPlayer();
    private final boolean[] blackInOctave = { false, true, false, true, false, false, true, false, true, false, true, false };
    private final List<Button> blackKeys = new ArrayList<>();
    private final List<Button> whiteKeys = new ArrayList<>();
    private final List<Button> functionKeys = new ArrayList<>();

    private final int[] blackKeyPositions = {15, 17, 20, 22, 24, 27, 29, 32, 34, 36, 39, 41, 44, 46, 48, 51, 53, 56, 58, 60, 63, 65,
                                            68, 70, 72};

    private final int[] whiteKeyPositions = {14, 16, 18, 19, 21, 23, 25, 26, 28, 30, 31, 33, 35, 37, 38, 40, 42, 43, 45, 47, 49, 50,
                                             52, 54, 55, 57, 59, 61, 62, 64, 66, 67, 69, 71, 73};
                                             
    private final int[] functionKeyPositions = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    

    private static final int UPPER_KEY_BORDER = 144;        // y upper key border
    private static final int LOWER_WHITE_BORDER = 335;      // y lower white key border
    private static final int LOWER_BLACK_BORDER = 265;      // y lower black key border

    private final Button[] buttons = {                      // buttons placements
            new Button(320, 83, 339, 104, 0),               // Button power
            new Button(357, 75, 374, 91, 1),                // Button volume up
            new Button(357, 98, 374, 113, 2),               // Button volume down

            new Button(395, 83, 416, 105, 3),               // Button instrument 0
            new Button(422, 83, 442, 105, 4),               // Button instrument 1
            new Button(449, 83, 468, 105, 5),               // Button instrument 2
            new Button(474, 83, 494, 105, 6),               // Button instrument 3
            new Button(501, 83, 520, 105, 7),               // Button instrument 4

            new Button(568, 83, 588, 105, 8),               // Button dummy
            new Button(618, 83, 638, 105, 9),               // Button dummy
            new Button(646, 83, 665, 105, 10),              // Button dummy
            new Button(673, 83, 692, 105, 11),              // Button dummy
            new Button(714, 83, 733, 105, 12),              // Button dummy
            new Button(742, 83, 759, 105, 13),              // Button dummy

            new Button(61, 90, 14),                         // Button white
            new Button(80, 96, 15),                         // Button black
            new Button(91, 124, 16),                        // Button white
            new Button(119, 136, 17),                       // Button black
            new Button(125, 155, 18),                       // Button white
            new Button(156, 186, 19),                       // Button white
            new Button(174, 191, 20),                       // Button black
            new Button(187, 219, 21),                       // Button white
            new Button(211, 227, 22),                       // Button black
            new Button(220, 252, 23),                       // Button white
            new Button(249, 265, 24),                       // Button black
            new Button(253, 283, 25),                       // Button white

            new Button(284, 315, 26),                       // Button
            new Button(304, 320, 27),                       // Button black
            new Button(316, 347, 28),                       // Button
            new Button(342, 360, 29),                       // Button black
            new Button(348, 379, 30),                       // Button
            new Button(380, 410, 31),                       // Button
            new Button(400, 416, 32),                       // Button black
            new Button(411, 443, 33),                       // Button
            new Button(436, 453, 34),                       // Button black
            new Button(444, 477, 35),                       // Button
            new Button(473, 491, 36),                       // Button black
            new Button(478, 509, 37),                       // Button

            new Button(510, 539, 38),                       // Button
            new Button(529, 546, 39),                       // Button black
            new Button(540, 572, 40),                       // Button
            new Button(569, 586, 41),                       // Button black
            new Button(573, 605, 42),                       // Button
            new Button(606, 637, 43),                       // Button
            new Button(624, 642, 44),                       // Button black
            new Button(638, 670, 45),                       // Button
            new Button(662, 678, 46),                       // Button black
            new Button(671, 702, 47),                       // Button
            new Button(698, 716, 48),                       // Button black
            new Button(703, 735, 49),                       // Button

            new Button(736, 766, 50),                       // Button
            new Button(755, 773, 51),                       // Button black
            new Button(767, 800, 52),                       // Button
            new Button(795, 813, 53),                       // Button black
            new Button(801, 832, 54),                       // Button
            new Button(833, 863, 55),                       // Button
            new Button(851, 870, 56),                       // Button black
            new Button(864, 898, 57),                       // Button
            new Button(888, 906, 58),                       // Button black
            new Button(899, 929, 59),                       // Button
            new Button(925, 943, 60),                       // Button black
            new Button(930, 962, 61),                       // Button

            new Button(963, 994, 62),                       // Button
            new Button(982, 1000, 63),                      // Button black
            new Button(995, 1027, 64),                      // Button
            new Button(1021, 1041, 65),                     // Button black
            new Button(1028, 1059, 66),                     // Button
            new Button(1060, 1090, 67),                     // Button
            new Button(1078, 1096, 68),                     // Button black
            new Button(1091, 1125, 69),                     // Button
            new Button(1116, 1134, 70),                     // Button black
            new Button(1126, 1158, 71),                     // Button
            new Button(1153, 1172, 72),                     // Button black
            new Button(1159, 1189, 73),                     // Button

            new Button(1190, 1222, 74) };                   // Button

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainAnPane;

    @FXML
    private ImageView theImage;

    @FXML
    private Label statusLabel;

    @FXML
    private javafx.scene.control.Button leftButton1, leftButton2, leftButton3, leftButton4, leftButton5, leftButton6, leftButton7, leftButton8, leftButton9, leftButton10;
    
    @FXML
    private javafx.scene.control.Button centerButton1, centerButton2, centerButton3, centerButton4, centerButton5, centerButton6, centerButton7,
    centerButton8, centerButton9, centerButton10, centerButton11, centerButton12, centerButton13, centerButton14, centerButton15;

    @FXML
    private javafx.scene.control.Button rightButton1, rightButton2, rightButton3, rightButton4, rightButton5, rightButton6, rightButton7, rightButton8, rightButton9, rightButton10;
    
    @FXML
    private javafx.scene.control.Button blackButton1, blackButton2, blackButton3, blackButton4, blackButton5, blackButton6, blackButton7,
    blackButton8, blackButton9, blackButton10, blackButton11, blackButton12, blackButton13, blackButton14, blackButton15, blackButton16,
    blackButton17, blackButton18, blackButton19, blackButton20, blackButton21, blackButton22, blackButton23, blackButton24;

    @FXML
    private javafx.scene.control.Button funcButton1, funcButton2, funcButton3, funcButton4, funcButton5, funcButton6, funcButton7, funcButton8;


    @FXML
    void initialize() {
        assert mainAnPane != null : "fx:id=\"mainAnPane\" was not injected: check your FXML file 'Mainframe.fxml'.";
        assert theImage != null : "fx:id=\"theImage\" was not injected: check your FXML file 'Mainframe.fxml'.";
        assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'Mainframe.fxml'.";

        theImage.fitWidthProperty().bind(mainAnPane.widthProperty());
        theImage.fitHeightProperty().bind(mainAnPane.heightProperty());

        for (int i : functionKeyPositions)
            functionKeys.add(buttons[i]);
        for (int i : blackKeyPositions)
            blackKeys.add(buttons[i]);
        for (int i : whiteKeyPositions)
            whiteKeys.add(buttons[i]);
        displayCurrentStatus();
        try {
            currStat.updateSoundsLibrary();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        soundPlayer.setCurrentStatus(currStat);
    }

    @FXML
    void mouseClickKeyboard(MouseEvent event) {
        decideAction(currStat.getRelativeCoordinate(theImage.getFitWidth(), event.getX()), currStat.getRelativeCoordinate(theImage.getFitWidth(), event.getY()));
    }


    private void displayCurrentStatus() {
        statusLabel.setText(String.format("Power: %s | Volume: %d | Instrument: %s", currStat.ON ? "ON" : "OFF",
                currStat.volume, currStat.instruments[currStat.instrument]));
    }

    private int getFunctionKey(int x, int y) {
        for (Button btn : functionKeys)
            if (btn.check(x, y))
                return btn.function;
        return -1;
    }

    private int getSoundKey(int x, int y) {
        for (Button btn : blackKeys)
            if (btn.check(x, y))
                return btn.function;
        for (Button btn : whiteKeys)
            if (btn.check(x, y))
                return btn.function;
        return -1;
    }

    private void runFunction(int fun) {
        if (-1 == fun) {
            System.out.println("No function key pressed");
            return;
        }
        if (0 == fun)
            currStat.powerSwitch();
        if (1 == fun)
            currStat.volumeUp();
        if (2 == fun)
            currStat.volumeDown();
        if (fun > 2 && fun < 8)
            currStat.setInstrument(fun - 3);
    }

    public void playSound(int sound) {
        System.out.println(theImage.getFitWidth() + " " + theImage.getFitHeight());
        if (-1 == sound) {
            System.out.println("No sound key pressed");
            return;
        }
        if (!currStat.ON) {
            System.out.println("Power is off");
            return;
        }
        if (currStat.volume == 0) {
            System.out.println("Volume is 0");
            return;
        }
        
        Clip clip = currStat.soundClips.get(sound - 14 + 1);
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(currStat.volume + 1.0) / Math.log(10) * 20) - 15.0f;

        try {
            gain.setValue(dB);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setMicrosecondPosition(0);
        clip.start();
    }    

    private void decideAction(int x, int y) {
        if (CALIBRATION_MODE) {
            System.out.println(String.format("Calibrating button %d, %s x is %d, %s y is %d", calibration / 2,
                    calibration % 2 == 0 ? "left" : "right", x, calibration % 2 == 0 ? "up" : "bottom", y));
            calibration++;
        } else {
            if (y < UPPER_KEY_BORDER)
                runFunction(getFunctionKey(x, y));
            else
                playSound(getSoundKey(x, y));
            displayCurrentStatus();
        }
    }

    private class Button {
        private int xLeft;
        private int xRight;
        private int yUp;
        private int yDown;
        private int function;

        Button(int lx, int uy, int rx, int dy, int fun) {
            this.xLeft = lx;
            this.xRight = rx;
            this.yUp = uy;
            this.yDown = dy;
            this.function = fun;
        }

        Button(int lx, int rx, int fun) {
            this(lx, UPPER_KEY_BORDER, rx, blackInOctave[(fun - 14) % 12] ? LOWER_BLACK_BORDER : LOWER_WHITE_BORDER, fun);
        }

        public boolean check(int x, int y) {
            return (x >= xLeft && x <= xRight && y >= yUp && y <= yDown);
        }
    }
}
