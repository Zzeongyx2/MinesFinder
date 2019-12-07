package pt.technic.apps.minesfinder;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameSound {

    private Clip clip;

    public void GameSound(String filename) {
        File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" + filename);
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)) {
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            Logger.getLogger(GameSound.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void StopSound() {
        clip.stop();
        clip.close();
    }
}

