package app;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Mainframe {
    boolean[] blackInOctave = { false, true, false, true, false, false, true, false, true, false, true, false };
    List<Button> blackKeys = new ArrayList<Button>();
    List<Button> whiteKeys = new ArrayList<Button>();
    int[] blackKeyPositions = { 15, 17, 20, 22, 24, 27, 29, 32, 34, 36, 39, 41, 44, 46, 48, 51, 53, 56, 58, 60, 63, 65,
            68, 70, 72 };

    class CurrentStatus {
        boolean ON = false;
        int volume = 5;
        int instrument = 0;
        String[] instruments = { "PIANO", "E.PIANO", "ORGAN", "HARPE", "STRINGS" };

        public void powerSwitch() {
            this.ON = !this.ON;
        }

        public void volumeUp() {
            if (this.volume < 10)
                this.volume++;
        }

        public void volumeDown() {
            if (this.volume > 0)
                this.volume--;
        }

        public void setInstrument(int option) {
            if (option < 0)
                option = 0;
            if (option > 4)
                option = 4;
            this.instrument = option;
        }
    }

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

    int upperKeyBorder = 166; // y upper key border
    int lowerWhiteBorder = 360;// y lower white key border
    int lowerBlackBorder = 290;// y lower black key border
    Button[] buttons = { new Button(325, 105, 346, 125, 0), new Button(365, 97, 380, 111, 1),
            new Button(366, 119, 379, 134, 2), new Button(403, 105, 422, 124, 3), new Button(431, 105, 449, 125, 4),
            new Button(457, 105, 476, 125, 5), new Button(483, 105, 502, 125, 6), new Button(510, 105, 528, 124, 7),
            new Button(568, 105, 588, 126, 8), new Button(618, 105, 638, 125, 9), new Button(646, 105, 665, 125, 10),
            new Button(673, 104, 692, 125, 11), new Button(714, 105, 733, 124, 12), new Button(742, 104, 759, 125, 13),
            new Button(63, 91, 14), new Button(82, 98, 15), new Button(96, 124, 16), new Button(120, 138, 17),
            new Button(128, 156, 18), new Button(159, 189, 19), new Button(178, 194, 20), new Button(190, 222, 21),
            new Button(215, 232, 22), new Button(225, 254, 23), new Button(253, 269, 24), new Button(261, 287, 25),
            new Button(290, 320, 26), new Button(309, 326, 27), new Button(321, 353, 28), new Button(349, 366, 29),
            new Button(355, 386, 30), new Button(387, 419, 31), new Button(406, 424, 32), new Button(418, 450, 33),
            new Button(444, 461, 34), new Button(452, 483, 35), new Button(481, 499, 36), new Button(486, 517, 37),
            new Button(519, 550, 38), new Button(538, 555, 39), new Button(549, 581, 40), new Button(578, 596, 41),
            new Button(586, 615, 42), new Button(617, 648, 43), new Button(635, 653, 44), new Button(647, 681, 45),
            new Button(673, 691, 46), new Button(683, 714, 47), new Button(711, 728, 48), new Button(714, 747, 49),
            new Button(749, 780, 50), new Button(768, 784, 51), new Button(780, 814, 52), new Button(809, 826, 53),
            new Button(813, 847, 54), new Button(847, 880, 55), new Button(866, 884, 56), new Button(877, 913, 57),
            new Button(903, 922, 58), new Button(914, 945, 59), new Button(942, 959, 60), new Button(943, 980, 61),
            new Button(979, 1013, 62), new Button(999, 1018, 63), new Button(1012, 1046, 64),
            new Button(1038, 1058, 65), new Button(1045, 1078, 66), new Button(1077, 1113, 67),
            new Button(1097, 1116, 68), new Button(1111, 1145, 69), new Button(1134, 1154, 70),
            new Button(1146, 1179, 71), new Button(1172, 1192, 72), new Button(1178, 1211, 73),
            new Button(1209, 1244, 74) };

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

    }

    @FXML
    void mouseClickKeyboard(MouseEvent event) {
        // System.out.println(String.format("x is %d, y is %d", (int) event.getX(),
        // (int) event.getY()));
        decideAction((int) event.getX(), (int) event.getY());
    }

    void displayCurrentStatus() {
        StatusLabel.setText(String.format("Power: %s | Volume: %d | Instrument: %s", currStat.ON ? "ON" : "OFF",
                currStat.volume, currStat.instruments[currStat.instrument]));
    }

    // int calibration = 0;

    void decideAction(int x, int y) {
        System.out.println(String.format("x is %d, y is %d", x, y));

        // System.out.println(String.format("Calibrating button %d, %s x is %d, %s y is
        // %d", calibration / 2,
        // calibration % 2 == 0 ? "left" : "right", x, calibration % 2 == 0 ? "left" :
        // "right", y));
        // calibration++;
        for (Button btn : buttons) {
            if (btn.check(x, y))
                System.out.println("Button " + btn.function + " clicked");
        }
        displayCurrentStatus();
    }

}
