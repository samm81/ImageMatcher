import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {

	BufferedImage image;
	static int displayNum = -1;

	public Display(int width, int height) {
		displayNum++;
		int displayX = 20 + (displayNum * (width + 30));
		int displayY = 50;
		while (displayX > 1600) { //TODO: change 1600 to auto
			displayX -= 1600;
			displayY += height + 30;
		}
		setBounds(displayX, displayY, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void updateImage(BufferedImage image) {
		this.image = image;
	}

	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}

}
