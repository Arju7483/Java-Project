package myPakage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Scanner;

@SuppressWarnings("serial")
public class HomePage extends JPanel implements ActionListener, MouseListener, KeyListener {

	private JFrame frame;
	private JButton playButton;
	private JButton scoreButton;
	private JButton exitButton;
	private Image backgroundImage;

	public HomePage() {
		frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(800, 800);

		// Load the background image
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		backgroundImage = toolkit.getImage("F:\\pic\\cover9.png");

		// Add the buttons
		playButton = new JButton("Play Game");
		playButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		playButton.setBounds(300, 200, 200, 50);
		playButton.addActionListener(this);
		frame.add(playButton);

		scoreButton = new JButton("Highest Score");
		scoreButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		scoreButton.setBounds(300, 300, 200, 50);
		scoreButton.addActionListener(this);
		frame.add(scoreButton);

		exitButton = new JButton("Exit Game");
		exitButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		exitButton.setBounds(300, 400, 200, 50);
		exitButton.addActionListener(this);
		frame.add(exitButton);

		// Add this panel to the frame
		this.setBounds(0, 0, 800, 800);
		frame.add(this);

		// Show the frame
		frame.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, 800, 700, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			FlappyBird.flappyBird = new FlappyBird();
		} else if (e.getSource() == scoreButton) {
			File file = new File("F:\\pic\\Score.txt");
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int num = scanner.nextInt();
			scanner.close();
			JFrame f = new JFrame("Highest Score");
			f.setLayout(null);
			f.setBounds(0,0,800,800);
			f.setBackground(Color.black);
			f.setVisible(true);
			
			//System.out.println(num);
			// show high scores
			JLabel highScoresLabel = new JLabel();
			highScoresLabel.setFont(new Font("Helvetica", Font.PLAIN, 50));
			
			highScoresLabel.setBounds(30, 50, 800, 400);
			highScoresLabel.setBackground(Color.yellow);
			f.add(highScoresLabel);
			highScoresLabel.setText("Highest Scores : " + num);

		} else if (e.getSource() == exitButton) {
			System.exit(0);
		}
	}

	// Implement other event handlers as needed

	public static void main(String s[]) {
		new HomePage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
