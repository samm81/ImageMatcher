import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {

	static int displayNum = -1;

	int fps = 10;
	
	DisplayCanvas displayCanvas;

	public Display(int width, int height) {
		displayNum++;
		int displayX = 30 + (displayNum * (width + 65));
		int displayY = 5;

		int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth();

		while (displayX + width > screenWidth) {
			displayX -= screenWidth;
			displayY += height + 45;
		}
		setLocation(displayX, displayY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(false);

		displayCanvas = new DisplayCanvas(fps);
		displayCanvas.setSize(width, height);
		add(displayCanvas);

		pack();

		setVisible(true);

		displayCanvas.start();
	}

	public void updateImage(BufferedImage image) {
		displayCanvas.updateImage(image);
	}

	private class DisplayCanvas extends DoubleBufferedCanvas {
		BufferedImage image;

		public DisplayCanvas(int fps) {
			super(fps);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						ImageIO.write(image, "png", new File("image.png"));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					System.out.println("SAVED");
				}
			});
		}

		@Override
		void draw(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}

		@Override
		protected void updateVars() {
		}

		protected void updateImage(BufferedImage image) {
			this.image = image;
		}
	}

}
