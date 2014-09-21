import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

public class AudioPlayer
{
    public static void PlayDarkTower()
    {
        PlayWav("audio/darktower.wav");
    }

    public static void PlayBattle()
    {
        PlayWav("audio/battle.wav");
    }

    public static void PlayStarvation()
    {
        PlayWav("audio/starving.wav");
    }

    public static void PlayBeep()
    {
        PlayWav("audio/beep.wav");
    }

    public static void PlayDragon()
    {
        PlayWav("audio/dragon.wav");
    }

    public static void PlayDragonKill()
    {
        PlayWav("audio/dragon-kill.wav");
    }

    public static void PlayLost() {
        PlayWav("audio/lost.wav");
    }

    public static void PlayPlague() { PlayWav("audio/plague.wav"); }

    public static void PlayWav(String fileName)
    {
        File dt = new File(fileName);
        try
        {
            playClip(dt);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void playClip(File clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
    {

        class AudioListener implements LineListener
        {
            private boolean done = false;
            @Override
            public synchronized void update(LineEvent event)
            {
                Type eventType = event.getType();
                if (eventType == Type.STOP || eventType == Type.CLOSE)
                {
                    done = true;
                    notifyAll();
                }
            }
            public synchronized void waitUntilDone() throws InterruptedException
            {
                while (!done) { wait(); }
            }
        }

        AudioListener listener = new AudioListener();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(listener);
            clip.open(audioInputStream);
            try
            {
                clip.start();
                listener.waitUntilDone();
            }
            finally
            {
                clip.close();
            }
        }
        finally
        {
            audioInputStream.close();
        }
    }

}



