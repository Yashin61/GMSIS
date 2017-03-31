// Author: yhk30--y.hosseinkhorrami@se15.qmul.ac.uk--150479358--Yashin Hossein Khorrami

package common;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class SoundEffect
{
    public static synchronized void playOpening()
    {
	try
	{
            Clip clip=AudioSystem.getClip();
	    AudioInputStream inputStream=AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("HelloSound.wav"));
	    clip.open(inputStream);
	    clip.start();
	}
	catch(LineUnavailableException | UnsupportedAudioFileException | IOException e)
	{
            System.err.println(e.getMessage());
	}
    }
}