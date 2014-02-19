import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {

	BufferedImage image;
	static int displayNum = -1;

	public Display(int width, int height) {
		displayNum++;
		int displayX = 30 + (displayNum * (width + 65));
		int displayY = 30;
		while (displayX + width > 1600) { //TODO: change 1600 to auto
			displayX -= 1600;
			displayY += height + 20;
		}
		setBounds(displayX, displayY, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(false);
		setVisible(true);
	}

	public void updateImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(image, 0, 0, this);
	}

}
