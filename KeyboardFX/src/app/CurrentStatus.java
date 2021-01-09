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

public class CurrentStatus {
    protected boolean ON = false;
    protected int volume = 5;
    protected int instrument = 0;
    protected final String[] instruments = {"PIANO", "E.PIANO", "ORGAN", "HARPE", "STRINGS"};
    protected List<Clip> soundClips = new ArrayList<>();
    private double initialWidth = 1284;

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
}