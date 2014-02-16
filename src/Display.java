import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {

	BufferedImage image;
	static int displayNum = -1;

	public Display(int width, int height) {
		displayNum++;
		setBounds(20 + (displayNum * (width + 30)), 100, width, height);
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
