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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.sound.sampled.*;

public class Mainframe {
    private static final boolean calibrationMode = false;

    boolean[] blackInOctave = { false, true, false, true, false, false, true, false, true, false, true, false };
    List<Button> blackKeys = new ArrayList<Button>();
    List<Button> whiteKeys = new ArrayList<Button>();
    List<Button> functionKeys = new ArrayList<Button>();
    int[] blackKeyPositions = { 15, 17, 20, 22, 24, 27, 29, 32, 34, 36, 39, 41, 44, 46, 48, 51, 53, 56, 58, 60, 63, 65,
            68, 70, 72 };
    int[] whiteKeyPositions = { 14, 16, 18, 19, 21, 23, 25, 26, 28, 30, 31, 33, 35, 37, 38, 40, 42, 43, 45, 47, 49, 50,
            52, 54, 55, 57, 59, 61, 62, 64, 66, 67, 69, 71, 73 };
    int[] functionKeyPositions = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

    class Button {
        int xLeft;
        int xRight;
        int yUp;
        int yDown;
        int function;

        Button(int lx, int uy, int rx, int dy, int fun) {
            this.xLeft = lx;
            this.xRight = rx;
            this.yUp = uy;
            this.yDown = dy;
            this.function = fun;
        }

        Button(int lx, int rx, int fun) {
            this(lx, upperKeyBorder, rx, blackInOctave[(fun - 14) % 12] ? lowerBlackBorder : lowerWhiteBorder, fun);
        }

        public boolean check(int x, int y) {
            if (x >= xLeft && x <= xRight && y >= yUp && y <= yDown)
                return true;
            return false;
        }
    }

    int upperKeyBorder = 144; // y upper key border
    int lowerWhiteBorder = 335;// y lower white key border
    int lowerBlackBorder = 265;// y lower black key border
    final Button[] buttons = { // buttons placements
            new Button(320, 83, 339, 104, 0), // Button power
            new Button(357, 75, 374, 91, 1), // Button volume up
            new Button(357, 98, 374, 113, 2), // Button volume down

            new Button(395, 83, 416, 105, 3), // Button instrument 0
            new Button(422, 83, 442, 105, 4), // Button instrument 1
            new Button(449, 83, 468, 105, 5), // Button instrument 2
            new Button(474, 83, 494, 105, 6), // Button instrument 3
            new Button(501, 83, 520, 105, 7), // Button instrument 4

            new Button(568, 83, 588, 105, 8), // Button dummy
            new Button(618, 83, 638, 105, 9), // Button dummy
            new Button(646, 83, 665, 105, 10), // Button dummy
            new Button(673, 83, 692, 105, 11), // Button dummy
            new Button(714, 83, 733, 105, 12), // Button dummy
            new Button(742, 83, 759, 105, 13), // Button dummy

            new Button(61, 90, 14), // Button white
            new Button(80, 96, 15), // Button black
            new Button(91, 124, 16), // Button white
            new Button(119, 136, 17), // Button black
            new Button(125, 155, 18), // Button white
            new Button(156, 186, 19), // Button white
            new Button(174, 191, 20), // Button black
            new Button(187, 219, 21), // Button white
            new Button(211, 227, 22), // Button black
            new Button(220, 252, 23), // Button white
            new Button(249, 265, 24), // Button black
            new Button(253, 283, 25), // Button white

            new Button(284, 315, 26), // Button
            new Button(304, 320, 27), // Button black
            new Button(316, 347, 28), // Button
            new Button(342, 360, 29), // Button black
            new Button(348, 379, 30), // Button
            new Button(380, 410, 31), // Button
            new Button(400, 416, 32), // Button black
            new Button(411, 443, 33), // Button
            new Button(436, 453, 34), // Button black
            new Button(444, 477, 35), // Button
            new Button(473, 491, 36), // Button black
            new Button(478, 509, 37), // Button

            new Button(510, 539, 38), // Button
            new Button(529, 546, 39), // Button black
            new Button(540, 572, 40), // Button
            new Button(569, 586, 41), // Button black
            new Button(573, 605, 42), // Button
            new Button(606, 637, 43), // Button
            new Button(624, 642, 44), // Button black
            new Button(638, 670, 45), // Button
            new Button(662, 678, 46), // Button black
            new Button(671, 702, 47), // Button
            new Button(698, 716, 48), // Button black
            new Button(703, 735, 49), // Button

            new Button(736, 766, 50), // Button
            new Button(755, 773, 51), // Button black
            new Button(767, 800, 52), // Button
            new Button(795, 813, 53), // Button black
            new Button(801, 832, 54), // Button
            new Button(833, 863, 55), // Button
            new Button(851, 870, 56), // Button black
            new Button(864, 898, 57), // Button
            new Button(888, 906, 58), // Button black
            new Button(899, 929, 59), // Button
            new Button(925, 943, 60), // Button black
            new Button(930, 962, 61), // Button

            new Button(963, 994, 62), // Button
            new Button(982, 1000, 63), // Button black
            new Button(995, 1027, 64), // Button
            new Button(1021, 1041, 65), // Button black
            new Button(1028, 1059, 66), // Button
            new Button(1060, 1090, 67), // Button
            new Button(1078, 1096, 68), // Button black
            new Button(1091, 1125, 69), // Button
            new Button(1116, 1134, 70), // Button black
            new Button(1126, 1158, 71), // Button
            new Button(1153, 1172, 72), // Button black
            new Button(1159, 1189, 73), // Button

            new Button(1190, 1222, 74) };// Button

    private CurrentStatus currStat = new CurrentStatus();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane MainAnPane;

    @FXML
    private ImageView TheImage;

    @FXML
    private Label StatusLabel;

    @FXML
    void initialize() {
        assert MainAnPane != null : "fx:id=\"MainAnPane\" was not injected: check your FXML file 'Mainframe.fxml'.";
        assert TheImage != null : "fx:id=\"TheImage\" was not injected: check your FXML file 'Mainframe.fxml'.";
        assert StatusLabel != null : "fx:id=\"StatusLabel\" was not injected: check your FXML file 'Mainframe.fxml'.";

        TheImage.fitWidthProperty().bind(MainAnPane.widthProperty());
        TheImage.fitHeightProperty().bind(MainAnPane.heightProperty());

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
    }

    @FXML
    void mouseClickKeyboard(MouseEvent event) {
        decideAction(currStat.getRelativeCoordinate(event.getX()), currStat.getRelativeCoordinate(event.getY()));
    }

    class CurrentStatus {
        boolean ON = false;
        int volume = 5;
        int instrument = 0;
        String[] instruments = { "PIANO", "E.PIANO", "ORGAN", "HARPE", "STRINGS" };
        List<Clip> soundClips = new ArrayList<Clip>();
        public boolean widthInitialised = true;
        double initialWidth = 1284;

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
            if (this.ON == false)
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

        public int getRelativeCoordinate(double xory) {
            double currWidth = TheImage.getFitWidth();
            return (int) (xory * (initialWidth / currWidth));
        }
    }

    void displayCurrentStatus() {
        StatusLabel.setText(String.format("Power: %s | Volume: %d | Instrument: %s", currStat.ON ? "ON" : "OFF",
                currStat.volume, currStat.instruments[currStat.instrument]));
    }

    int getFunctionKey(int x, int y) {
        for (Button btn : functionKeys)
            if (btn.check(x, y))
                return btn.function;
        return -1;
    }

    int getSoundKey(int x, int y) {
        for (Button btn : blackKeys)
            if (btn.check(x, y))
                return btn.function;
        for (Button btn : whiteKeys)
            if (btn.check(x, y))
                return btn.function;
        return -1;
    }

    void runFunction(int fun) {
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

    void playSound(int sound) {
        if (-1 == sound) {
            System.out.println("No sound key pressed");
            return;
        }
        if (currStat.ON == false) {
            System.out.println("Power is off");
            return;
        }
        if (currStat.volume == 0) {
            System.out.println("Volume is 0");
            return;
        }
        Clip clip = currStat.soundClips.get(sound - 14 + 1);
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(currStat.volume + 1) / Math.log(10) * 20) - 15.0f;
        System.out.println("Setting dB as " + dB);
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

    int calibration = 0;

    void decideAction(int x, int y) {
        if (calibrationMode) {
            System.out.println(String.format("Calibrating button %d, %s x is %d, %s y is %d", calibration / 2,
                    calibration % 2 == 0 ? "left" : "right", x, calibration % 2 == 0 ? "up" : "bottom", y));
            calibration++;
        } else {
            if (y < upperKeyBorder)
                runFunction(getFunctionKey(x, y));
            else
                playSound(getSoundKey(x, y));
            displayCurrentStatus();
        }
    }
}
