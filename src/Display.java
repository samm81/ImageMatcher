import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {

	static int displayNum = -1;
	
	DisplayCanvas displayCanvas;

	public Display(int width, int height) {
		displayNum++;
		int displayX = 30 + (displayNum * (width + 65));
		int displayY = 5;
		
		int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		
		while (displayX + width > screenWidth) {
			displayX -= screenWidth;
			displayY += height + 45;
		}
		setLocation(displayX, displayY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(false);
		
		displayCanvas = new DisplayCanvas(30);
		displayCanvas.setSize(width, height);
		add(displayCanvas);
		
		pack();
		
		setVisible(true);
		
		displayCanvas.start();
	}

	public void updateImage(BufferedImage image) {
		displayCanvas.updateImage(image);
	}
	
	private class DisplayCanvas extends DoubleBufferedCanvas{
		BufferedImage image;
		
		public DisplayCanvas(int fps) { super(fps); }

		@Override
		void draw(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}

		@Override
		protected void updateVars() { }
		
		protected void updateImage(BufferedImage image) {
			this.image = image;
		}
	}

}
