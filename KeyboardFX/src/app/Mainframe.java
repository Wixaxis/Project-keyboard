package app;

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

    private int calibration = 0;
    private static final boolean CALIBRATION_MODE = false;
    private CurrentStatus currStat = new CurrentStatus();
    private SoundPlayer soundPlayer = SoundPlayer.getSoundPlayer();
    private static final boolean[] blackInOctave = { false, true, false, true, false, false, true, false, true, false,
            true, false };
    private final List<ButtonData> blackKeys = new ArrayList<>();
    private final List<ButtonData> whiteKeys = new ArrayList<>();
    private final List<ButtonData> functionKeys = new ArrayList<>();

    private final int[] blackKeyPositions = { 15, 17, 20, 22, 24, 27, 29, 32, 34, 36, 39, 41, 44, 46, 48, 51, 53, 56,
            58, 60, 63, 65, 68, 70, 72 };

    private final int[] whiteKeyPositions = { 14, 16, 18, 19, 21, 23, 25, 26, 28, 30, 31, 33, 35, 37, 38, 40, 42, 43,
            45, 47, 49, 50, 52, 54, 55, 57, 59, 61, 62, 64, 66, 67, 69, 71, 73 };

    private final int[] functionKeyPositions = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

    private static final int UPPER_KEY_BORDER = 144; // y upper key border
    private static final int LOWER_WHITE_BORDER = 335; // y lower white key border
    private static final int LOWER_BLACK_BORDER = 265; // y lower black key border

    private final ButtonData[] buttons = currStat.buttons;

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
            currStat.loadCurrentBackingTrack();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        currStat.setAnchorAndImage(mainAnPane, theImage);
        soundPlayer.setCurrentStatus(currStat);
    }

    @FXML
    void mouseClickKeyboard(MouseEvent event) {
        decideAction(currStat.getRelativeCoordinate(theImage.getFitWidth(), event.getX()),
                currStat.getRelativeCoordinate(theImage.getFitWidth(), event.getY()));
    }

    private void displayCurrentStatus() {
        System.out.println("Setting status");
        statusLabel.setText(String.format("Power: %s | Volume: %d | Instrument: %s | Backing Track: %s [%s]",
                currStat.ON ? "ON" : "OFF", currStat.volume, currStat.instruments[currStat.instrument],
                currStat.getCurrBackingName(), currStat.isBackingRunning ? "PLAYING" : "PAUSED"));
    }

    private int getFunctionKey(int x, int y) {
        for (ButtonData btn : functionKeys)
            if (btn.check(x, y))
                return btn.function;
        return -1;
    }

    private int getSoundKey(int x, int y) {
        for (ButtonData btn : blackKeys)
            if (btn.check(x, y))
                return btn.function;
        for (ButtonData btn : whiteKeys)
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
        if (currStat.ON) {
            if (1 == fun)
                currStat.volumeUp();
            if (2 == fun)
                currStat.volumeDown();
            if (fun > 2 && fun < 8) {
                currStat.setInstrument(fun - 3);
            }
            if (8 == fun) {
                theImage.setImage(currStat.shiftImage());
            }
            if (9 == fun) {
                currStat.previousBackingTrack();
            }
            if (10 == fun) {
                currStat.nextBackingTrack();
            }
            if (11 == fun) {
                currStat.playPauseBackingTrack();
            }
            if (13 == fun) {
                currStat.resetBacking();
            }
        }
        if (12 == fun) {
            currStat.changeBackgroundColour();
        }

        currStat.shadeButton(fun, 0.3);
    }

    private void decideAction(int x, int y) {
        if (CALIBRATION_MODE) {
            System.out.println(String.format("Calibrating button %d, %s x is %d, %s y is %d", calibration / 2,
                    calibration % 2 == 0 ? "left" : "right", x, calibration % 2 == 0 ? "up" : "bottom", y));
            calibration++;
        } else {
            if (y < UPPER_KEY_BORDER)
                runFunction(getFunctionKey(x, y));
            else {
                soundPlayer.playSound(getSoundKey(x, y));
            }
            displayCurrentStatus();
        }
    }

    public static class ButtonData {
        public int xLeft;
        public int xRight;
        public int yUp;
        public int yDown;
        public int function;

        ButtonData(int lx, int uy, int rx, int dy, int fun) {
            this.xLeft = lx;
            this.xRight = rx;
            this.yUp = uy;
            this.yDown = dy;
            this.function = fun;
        }

        ButtonData(int lx, int rx, int fun) {
            this(lx, UPPER_KEY_BORDER, rx, blackInOctave[(fun - 14) % 12] ? LOWER_BLACK_BORDER : LOWER_WHITE_BORDER,
                    fun);
        }

        public boolean check(int x, int y) {
            return (x >= xLeft && x <= xRight && y >= yUp && y <= yDown);
        }
    }

}
