import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Number {

	Frame frame = new Frame("pinBall Game");

	// table size
	private final int TABLE_WIDTH = 300;
	private final int TABLE_HEIGHT = 400;

	// racket size
	private final int RACKET_WIDTH = 60;
	private final int RACKET_HEIGHT = 20;

	// ball size
	private final int BALL_SIZE = 16;

	// original coordinate of ball
	private int ballX = 120;
	private int ballY = 20;

	// speed of ball in coordinate
	private int speedX = 10;
	private int speedY = 5;

	// original coordinate of racket
	private int racketX = 120;
	private int racketY = 340;

	// sign of whether game continue
	private boolean isOver = false;

	// declare a timer
	private Timer timer;

	public static void main(String[] args) {
		new Number().init();

	}

	// declare a class inherited from class canvas
	private class MyCanvas extends Canvas {
		public void paint(Graphics g) {
			// Game over
			if (isOver) {
				System.out.println(isOver);
				g.setColor(Color.BLUE);
				g.setFont(new Font("Times New Roman", Font.BOLD, 30));
				g.drawString("Game Over", 50, 200);
			} else {
				// Game continue
				System.out.println(2);
				// Draw ball
				g.setColor(Color.RED);// set color
				g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);// fill color of ball

				// Draw racket
				g.setColor(Color.PINK);
				g.fillRect(racketX, racketY, RACKET_WIDTH, RACKET_HEIGHT);

			}
		}
	}

	// create canvas area
	MyCanvas drawArea = new MyCanvas();

	public void init() {
		// pinBall Game logic control

		// racket coordinate change
		KeyListener listener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				// capture the pressing key
				int keyCode = e.getKeyCode();

				if (keyCode == KeyEvent.VK_LEFT) {
					// <- move left
					if (racketX > 0)
						racketX -= 10;
				}

				if (keyCode == KeyEvent.VK_RIGHT) {
					// -> move right
					if (racketX < (TABLE_WIDTH - RACKET_WIDTH))
						racketX += 10;
				}
			}

		};
		System.out.println("init 2");
		// frame and drawArea listener
		frame.addKeyListener(listener);
		drawArea.addKeyListener(listener);

		// ball coordinate control
		ActionListener task = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// renew speed according to the border of the drawArea
				if (ballX <= 0 || ballX >= (TABLE_WIDTH - BALL_SIZE))
					speedX = -speedX;

				if (ballY <= 0
						|| (ballY > (racketY - BALL_SIZE) && ballX > racketX && ballX < (racketX + RACKET_WIDTH)))
					speedY = -speedY;

				if (ballY > (racketY - BALL_SIZE) && (ballX < racketX || ballX > (racketX + RACKET_WIDTH))) {
					timer.stop();
					isOver = true;
					drawArea.repaint();
				}

				// renew coordinate of ball and repaint ball
				ballX += speedX;
				ballY += speedY;
				drawArea.repaint();
			}
		};

		timer = new Timer(100, task);
		timer.start();

		drawArea.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		System.out.println("init 1");
		frame.add(drawArea);

		frame.pack();
		frame.setVisible(true);
	}
}
