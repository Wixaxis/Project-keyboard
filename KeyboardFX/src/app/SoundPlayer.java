package app;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {

    private static final SoundPlayer SOUND_PLAYER = new SoundPlayer();
    private CurrentStatus currStat = null;

    private SoundPlayer(){}

    
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

        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setMicrosecondPosition(0);
        clip.start();
    }
    
    public void setCurrentStatus(CurrentStatus currentStatus) {
        currStat = currentStatus;
    }
}