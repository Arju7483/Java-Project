package myPakage;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

public class Renderer extends JPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			FlappyBird.flappyBird.repaint(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}