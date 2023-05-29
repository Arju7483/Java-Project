package myPakage;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingSound {
	JFrame frame;
	JPanel panel;
	JButton button1, button2;

	Clip clip;
	AudioInputStream sound;

	public AddingSound(String name) {

		try {
			File file = new File(name);
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (Exception e) {
		}
	}

	public void play() {
		clip.start();
	}

	public void stop() throws IOException {
		sound.close();
		clip.close();
		clip.stop();
	}

}
