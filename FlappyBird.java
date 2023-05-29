
package myPakage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.io.FileWriter;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

	public static FlappyBird flappyBird;

	public final int WIDTH = 1200, HEIGHT = 800;

	public Renderer renderer;

	public Rectangle bird;

	public ArrayList<Rectangle> columns;

	public int ticks, yMotion, score;

	public boolean gameOver, started;

	public Random rand;
	public int speed = 15;
	JFrame jframe = new JFrame();

	public FlappyBird() {

		Timer timer = new Timer(20, this);

		renderer = new Renderer();
		rand = new Random();

		jframe.add(renderer);
		jframe.setTitle("Flappy Bird");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(true);
		jframe.setVisible(true);

		bird = new Rectangle(WIDTH / 2 - 20, HEIGHT / 2 - 20, 30, 30);
		columns = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();
	}

	public void addColumn(boolean start) {
		int space = 300;
		int width = 80;
		int height = 50 + rand.nextInt(400);

		if (start) {
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height)); /// top
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));// bottom
		} else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	public void paintColumn(Graphics g, Rectangle column) throws IOException {

		BufferedImage wall = null;
		wall = ImageIO.read(new File("F:\\pic\\wall33.png"));
		g.drawImage(wall, column.x, column.y, column.width, column.height, null);

	}

	public void jump() {
		if (gameOver) {
			bird = new Rectangle(WIDTH / 2 - 20, HEIGHT / 2 - 20, 30, 30);
			columns.clear();
			yMotion = 0;
			score = 0;
			// speed = 12;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}

		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (yMotion > 0) {
				yMotion = 0;
			}

			yMotion -= 10;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ticks++;

		if (started) {
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);

				column.x -= speed;

			}

			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
			}

			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0) {
					columns.remove(column);

					if (column.y == 0) {
						addColumn(false);
					}
				}
			}

			bird.y += yMotion;

			for (Rectangle column : columns) {
				if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10
						&& bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
					score++;
					AddingSound point = new AddingSound("F:\\pic\\point1.wav");
					point.play();

				}

				if (column.intersects(bird)) {
					gameOver = true;

					if (bird.x <= column.x) {
						bird.x = column.x - bird.width;

					} else {
						if (column.y != 0) {
							bird.y = column.y - bird.height;
						} else if (bird.y < column.height) {
							bird.y = column.height;
						}
					}

				}
			}

			if (bird.y > HEIGHT - 120 || bird.y < 0) {
				gameOver = true;

			}

			if (bird.y + yMotion >= HEIGHT - 120) {
				bird.y = HEIGHT - 120 - bird.height;
				gameOver = true;
			}
		}

		renderer.repaint();
	}

	public void repaint(Graphics g) throws IOException {

		BufferedImage background = null;
		BufferedImage ground = null;
		BufferedImage brd = null;
		try {
			background = ImageIO.read(new File("F:\\pic\\back1.png"));
			ground = ImageIO.read(new File("F:\\pic\\ground.png"));
			brd = ImageIO.read(new File("F:\\pic\\bird9.png"));

		} catch (IOException e) {

			e.printStackTrace();
		}
		g.drawImage(background, 0, 0, WIDTH, HEIGHT - 120, null);
		g.drawImage(ground, 0, HEIGHT - 120, WIDTH, 120, null);
		g.drawImage(brd, bird.x, bird.y, bird.width, bird.height, null);

		for (Rectangle column : columns) {
			paintColumn(g, column);
		}

		g.setColor(Color.black);
		g.setFont(new Font("Arial", 1, 100));

		if (!started) {
			g.setColor(Color.red);
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}

		if (gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", 1, 80));
			g.drawString("Score : " + score, 150, HEIGHT / 2 + 50);

			JButton button = new JButton("Main Menu");
			button.setFont(new Font("Helvetica", Font.BOLD, 20));
			button.setBounds(200, HEIGHT / 2 + 100, 200, 100);
			button.setBackground(Color.black);
			button.setForeground(Color.YELLOW);
			jframe.setContentPane(button);
			button.addActionListener(e -> {
				jframe.dispose();
			});
			File file = new File("F:\\pic\\Score.txt");
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int num = scanner.nextInt();

			if (score > num) {
				FileWriter writer = new FileWriter(file);
				writer.write(String.valueOf(score)); // convert the score to a string and write it to the file
				writer.close();
			}
			scanner.close();
		}

		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	public static void main(String[] args) {
		new HomePage();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		AddingSound sound = new AddingSound("F:\\pic\\wing1.wav");
		jump();
		if (started)
			sound.play();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			AddingSound sound = new AddingSound("F:\\pic\\wing1.wav");
			jump();
			if (started)
				sound.play();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

}
