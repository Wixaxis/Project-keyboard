package app;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import javafx.scene.input.KeyEvent;

public class SoundPlayer {

    private static final SoundPlayer SOUND_PLAYER = new SoundPlayer();
    private CurrentStatus currStat = null;
    private final Map<Character, Integer> soundsMap = new HashMap<>();

    private SoundPlayer() {
        String keyboardInput = "asdfghjklqwertyuiopASDFGHJKLQWERTYUIOP";
        int currentSound = 14;
        int stringIterator = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                soundsMap.put(keyboardInput.charAt(stringIterator++), currentSound);
                currentSound += 2;
            }
            currentSound--;

            for (int j = 0; j < 4; j++) {
                soundsMap.put(keyboardInput.charAt(stringIterator++), currentSound);
                currentSound += 2;
            }
            currentSound--;
        }
    }

    public static SoundPlayer getSoundPlayer() {
        return SOUND_PLAYER;
    }

    public void playSound(int sound) {
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

        clip.flush();
        clip.setMicrosecondPosition(0);
        try {
            Thread.sleep(70);
        } catch (InterruptedException ignored) {
        }
        clip.start();
        currStat.shadeButton(sound, 0.2);
    }

    private int eventToInt(KeyEvent event) {

        if (event.getText().length() == 0)
            return -1;

        char keyPressed = event.getText().charAt(0);
        int playedSound = -1;
        try {
            playedSound = soundsMap.get(keyPressed);
        } catch (Exception ignore) {
        }
        if (playedSound == -1)
            return -1;
        if (event.isShiftDown())
            playedSound += 32;
        if (event.isAltDown())
            playedSound += 1;
        if (playedSound > 72)
            return -1;
        return playedSound;
    }

    public void playSound(KeyEvent event) {
        System.out.println(eventToInt(event));
        playSound(eventToInt(event));
    }

    public void setCurrentStatus(CurrentStatus currentStatus) {
        currStat = currentStatus;
    }
}