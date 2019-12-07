package pt.technic.apps.minesfinder;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class GameSound extends Thread{
    private static Clip clip;

    public void GameSound(String filename) {

        File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" + filename);
        AudioInputStream ais;
        try {
            clip = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(url);
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void StopSound() {
        clip.stop();
        clip.close();
    }
}

